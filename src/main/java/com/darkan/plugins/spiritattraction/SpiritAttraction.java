package com.darkan.plugins.spiritattraction;

import kraken.plugin.api.Actions;
import kraken.plugin.api.ImGui;
import kraken.plugin.api.Npc;
import kraken.plugin.api.Npcs;
import kraken.plugin.api.Player;
import kraken.plugin.api.Time;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.darkan.plugins.PluginSkeleton;

public class SpiritAttraction extends PluginSkeleton {

	private static Set<String> SPIRIT_NAMES = new HashSet<>(Arrays.asList(new String[] { 
			"Seren spirit", "Fire spirit", "Divine blessing", "Divine fire spirit", 
			"Forge phoenix", "Manifested knowledge", "Divine forge phoenix", "Divine carpet dust" 
			}));
	
	private int spirits = 0;
	
	public SpiritAttraction() {
		super("Spirit Attraction", 2000);
	}

	@Override
	public void loop(Player self) {
		setState("Waiting for spirit...");
		Npc serenSpirit = Npcs.closest(npc -> SPIRIT_NAMES.contains(npc.getName()));
		if (serenSpirit != null) {
			setState("Interacting with spirit...");
			serenSpirit.interact(Actions.MENU_EXECUTE_NPC1);
			spirits++;
			sleep(2500);
		}
	}

	@Override
	public void paint(long runtime) {
		ImGui.label("Spirits p/h: " + Time.perHour(runtime, spirits));
	}
}