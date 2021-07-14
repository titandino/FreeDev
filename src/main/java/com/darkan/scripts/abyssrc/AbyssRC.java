package com.darkan.scripts.abyssrc;

import com.darkan.scripts.Script;
import com.darkan.scripts.ScriptSkeleton;

import kraken.plugin.api.Player;

@Script("Abyss RC")
public class AbyssRC extends ScriptSkeleton {
	
	public AbyssRC() {
		super("Abyss RC", 600);
	}
	
	@Override
	public boolean onStart(Player self) {
		return true;
	}
	
	@Override
	public void loop(Player self) {
		
	}

	@Override
	public void paintOverlay(long runtime) {
		
	}

	@Override
	public void paintImGui(long runtime) {
		printGenericXpGain(runtime);
	}

	@Override
	public void onStop() {
		
	}
}
