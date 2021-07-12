package com.darkan.scripts.beachevent;

import java.util.HashMap;
import java.util.Map;

import com.darkan.scripts.Script;
import com.darkan.scripts.ScriptSkeleton;

import kraken.plugin.api.ImGui;
import kraken.plugin.api.Player;

@Script("AIO Beach Event")
public class AIOBeachEvent extends ScriptSkeleton {
	
	private static Map<String, BeachActivity> ACTIVITIES = new HashMap<>();
	
	//v5733 = temp gauge?
	
	static {
		ACTIVITIES.put("Sand Castles", new SandCastles());
	}
	
	private BeachActivity activity = ACTIVITIES.get("Sand Castles");
			
	public AIOBeachEvent() {
		super("AIO Beach Event", 1000);
	}
	
	@Override
	public boolean onStart(Player self) {
		return true;
	}
	
	@Override
	public void loop(Player self) {
		if (activity != null)
			activity.loop(this, self);
	}

	@Override
	public void paintOverlay(long runtime) {
		
	}

	@Override
	public void paintImGui(long runtime) {
		ImGui.label("Current activity: " + activity.getClass().getSimpleName());
		printGenericXpGain(runtime);
	}

	@Override
	public void onStop() {
		
	}
}
