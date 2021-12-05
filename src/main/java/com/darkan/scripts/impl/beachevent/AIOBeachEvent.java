// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
//
//  Copyright © 2021 Trenton Kress
//  This file is part of project: FreeDev
//
package com.darkan.scripts.impl.beachevent;

import java.util.HashMap;
import java.util.Map;

import com.darkan.api.accessors.NPCs;
import com.darkan.api.entity.MyPlayer;
import com.darkan.api.inter.Interfaces;
import com.darkan.api.inter.chat.Message;
import com.darkan.api.listeners.MessageListener;
import com.darkan.scripts.LoopScript;
import com.darkan.scripts.Script;

import kraken.plugin.api.ImGui;

@Script("AIO Beach Event")
public class AIOBeachEvent extends LoopScript implements MessageListener {
	
	private static Map<String, BeachActivity> ACTIVITIES = new HashMap<>();
	
	static {
		ACTIVITIES.put("Construction", new SandCastles());
		ACTIVITIES.put("Dungeoneering", new DungeoneeringHole());
		ACTIVITIES.put("Cooking", new Barbeques());
		ACTIVITIES.put("Farming", new PalmTrees());
		ACTIVITIES.put("Fishing", new RockPools());
	    ACTIVITIES.put("Hunter", new HookADuck());
	}
	
	private BeachActivity activity = null;
	private boolean killClawdia = true;
	private long eatAnIceCream = -1;
			
	public AIOBeachEvent() {
		super("AIO Beach Event", 1000);
	}
	
	@Override
	public boolean onStart() {
		return true;
	}
	
	public boolean shouldEatIceCream() {
		return getTemp() == 220 && (System.currentTimeMillis() - eatAnIceCream) < 30000;
	}
	
	@Override
	public void loop() {
		if (shouldEatIceCream() && !getHighlight().contains("Happy Hour")) {
			if (Interfaces.getInventory().clickItem("Ice cream", "Eat"))
				sleep(5000);
			return;
		}
		if (killClawdia && NPCs.interactClosest("Attack", n -> n.getName().contains("Clawdia"))) {
			setState("Attacking Clawdia...");
			sleepWhile(3500, Long.MAX_VALUE, () -> getTimeSinceLastAnimation() < 4000 && NPCs.getClosest(n -> n.getName().contains("Clawdia") && n.hasOption("Attack")) != null);
			return;
		}
		
		if (activity != null)
			activity.loop(this);
	}
	
	public String getHighlight() {
		try {
			return Interfaces.getComponent(1642, 5).getText().replace("Spotlight Plot:<br>", "");
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	public int getTemp() {
		return MyPlayer.getVars().getVarBit(28441);
	}

	@Override
	public void paintOverlay(long runtime) {
		
	}

	@Override
	public void paintImGui(long runtime) {
		killClawdia = ImGui.checkbox("Kill Clawdia", killClawdia);
		
		ImGui.label("Choose a skill to train. Current spotlight: " + getHighlight());
		for (String name : ACTIVITIES.keySet()) {
			boolean currActive = activity == ACTIVITIES.get(name);
			boolean active = ImGui.checkbox(name, currActive);
			if (active != currActive)
				activity = active ? ACTIVITIES.get(name) : null;
		}
		
		printGenericXpGain(runtime);
	}

	@Override
	public void onStop() {
		
	}

	@Override
	public void onMessageReceived(Message message) {
		if (message.isGame() && message.getText().contains("Eat an ice cream"))
			eatAnIceCream = System.currentTimeMillis();
	}
}
