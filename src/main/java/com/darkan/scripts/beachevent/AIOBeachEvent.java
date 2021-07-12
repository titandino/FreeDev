package com.darkan.scripts.beachevent;

import java.util.HashMap;
import java.util.Map;

import com.darkan.api.accessors.NPCs;
import com.darkan.api.entity.MyPlayer;
import com.darkan.api.inter.Interfaces;
import com.darkan.scripts.Script;
import com.darkan.scripts.ScriptSkeleton;

import kraken.plugin.api.ImGui;
import kraken.plugin.api.Player;

@Script("AIO Beach Event")
public class AIOBeachEvent extends ScriptSkeleton {
	
	private static Map<String, BeachActivity> ACTIVITIES = new HashMap<>();
	
	static {
		ACTIVITIES.put("Construction", new SandCastles());
		ACTIVITIES.put("Dungeoneering", new DungeoneeringHole());
		ACTIVITIES.put("Cooking", new Barbeques());
		ACTIVITIES.put("Farming", new PalmTrees());
		ACTIVITIES.put("Fishing", new RockPools());
	}
	
	private BeachActivity activity = null;
	private boolean killClawdia = true;
			
	public AIOBeachEvent() {
		super("AIO Beach Event", 1000);
	}
	
	@Override
	public boolean onStart(Player self) {
		return true;
	}
	
	@Override
	public void loop(Player self) {
		if (killClawdia && MyPlayer.getHealthPerc() > 20.0 && NPCs.interactClosest("Attack", n -> n.getName().contains("Clawdia"))) {
			setState("Attacking Clawdia...");
			sleepWhile(3500, Long.MAX_VALUE, () -> getTimeSinceLastAnimation() < 4000 && MyPlayer.getHealthPerc() > 20.0 && NPCs.getClosest(n -> n.getName().contains("Clawdia") && n.hasOption("Attack")) != null);
			return;
		}
		
		if (activity != null)
			activity.loop(this, self);
	}
	
	public int getTemp() {
		//varbit 28441 220 -> 0 when eating ice cream?
		try {
			String tempDesc = Interfaces.getComponent(1486, 23).getText();
			return Integer.valueOf(tempDesc.substring(tempDesc.indexOf('(')+1, tempDesc.indexOf('%')));
		} catch(Exception e) {
			return 0;
		}
	}

	@Override
	public void paintOverlay(long runtime) {
		
	}

	@Override
	public void paintImGui(long runtime) {
		killClawdia = ImGui.checkbox("Kill Clawdia", killClawdia);
		
		ImGui.label("Temperature: " + getTemp());
		ImGui.label("Choose skill to train:");
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
}
