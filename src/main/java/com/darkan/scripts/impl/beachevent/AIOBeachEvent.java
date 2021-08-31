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
