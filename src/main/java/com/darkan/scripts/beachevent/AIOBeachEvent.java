package com.darkan.scripts.beachevent;

import java.util.HashMap;
import java.util.Map;

import com.darkan.api.accessors.NPCs;
import com.darkan.api.entity.MyPlayer;
import com.darkan.scripts.Script;
import com.darkan.scripts.ScriptSkeleton;

import kraken.plugin.api.ImGui;
import kraken.plugin.api.Player;

@Script("AIO Beach Event")
public class AIOBeachEvent extends ScriptSkeleton {
	
	private static Map<String, BeachActivity> ACTIVITIES = new HashMap<>();
	
	static {
		ACTIVITIES.put("Sand Castles", new SandCastles());
	}
	
	private BeachActivity activity = ACTIVITIES.get("Sand Castles");
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
			sleepWhile(3500, 50000, () -> MyPlayer.getHealthPerc() > 20.0 && NPCs.getClosest(n -> n.getName().contains("Clawdia") && n.hasOption("Attack")) != null);
			return;
		}
		
		if (activity != null)
			activity.loop(this, self);
	}

	@Override
	public void paintOverlay(long runtime) {
		
	}

	@Override
	public void paintImGui(long runtime) {
		killClawdia = ImGui.checkbox("Kill Clawdia", killClawdia);
		
		ImGui.label("Current activity: " + activity.getClass().getSimpleName());
		printGenericXpGain(runtime);
	}

	@Override
	public void onStop() {
		
	}
}
