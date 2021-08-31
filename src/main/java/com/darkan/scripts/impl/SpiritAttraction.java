package com.darkan.scripts.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.darkan.api.accessors.NPCs;
import com.darkan.api.entity.NPC;
import com.darkan.scripts.LoopScript;
import com.darkan.scripts.Script;

import kraken.plugin.api.ImGui;
import kraken.plugin.api.Time;

@Script(value = "Spirit Attraction", utility = true)
public class SpiritAttraction extends LoopScript {

	private static Set<String> SPIRIT_NAMES = new HashSet<>(Arrays.asList(new String[] { 
			"Seren spirit", "Fire spirit", "Divine blessing", "Divine fire spirit", 
			"Forge phoenix", "Manifested knowledge", "Divine forge phoenix", "Divine carpet dust" 
			}));
	
	private int spirits = 0;
	
	public SpiritAttraction() {
		super("Spirit Attraction", 2000);
	}
	
	@Override
	public boolean onStart() {
		return true;
	}

	@Override
	public void loop() {
		setState("Waiting for spirit...");
		NPC serenSpirit = NPCs.getClosestReachable(npc -> SPIRIT_NAMES.contains(npc.getName()));
		if (serenSpirit != null) {
			setState("Interacting with spirit...");
			serenSpirit.interact(0);
			spirits++;
			sleep(2500);
		}
	}
	
	@Override
	public void paintOverlay(long runtime) {
		
	}

	@Override
	public void paintImGui(long runtime) {
		ImGui.label("Spirits p/h: " + Time.perHour(runtime, spirits));
	}

	@Override
	public void onStop() {
		
	}
}
