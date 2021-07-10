package com.darkan.scripts.aiowisp;

import com.darkan.api.accessors.NPCs;
import com.darkan.api.accessors.WorldObjects;
import com.darkan.api.entity.NPC;
import com.darkan.api.inter.Interfaces;
import com.darkan.api.world.WorldObject;
import com.darkan.scripts.Script;
import com.darkan.scripts.ScriptSkeleton;

import kraken.plugin.api.Client;
import kraken.plugin.api.ImGui;
import kraken.plugin.api.Npc;
import kraken.plugin.api.Npcs;
import kraken.plugin.api.Player;
import kraken.plugin.api.Time;

@Script("AIO Wisp Gatherer")
public class AIOWispGathering extends ScriptSkeleton {

	/**
	 * TODO
	 * Chronicle absorption prayer support
	 * Better prioritization of enriched
	 */
	
	private static final int[] ENERGIES = { 29313, 29314, 29315, 29316, 29317, 29318, 29319, 29320, 31312, 29321, 29322, 29323, 29324, 37941 };
	
	private WispConfig config;
	private int startXp;
	private int startEnergy;
	
	public AIOWispGathering() {
		super("AIO Wisp Gathering", 1500);
	}
	
	@Override
	public boolean onStart(Player self) {
		if (config == null) {
			setState("Detecting location and starting...");
			detectLocation();
			startXp = Client.getStatById(Client.DIVINATION).getXp();
			startEnergy = Interfaces.getInventory().count(ENERGIES);
			start();
			return false;
		}
		return true;
	}
	
	@Override
	public void loop(Player self) {
		if (Interfaces.getInventory().isFull()) {
			setState("Inventory full. Finding closest rift...");
			WorldObject rift = WorldObjects.getClosestReachable(obj -> obj.hasOption("Convert memories"));
			if (rift != null && !self.isAnimationPlaying()) {
				setState("Inventory full. Clicking closest rift...");
				rift.interact("Convert memories");
				sleep(2500);
			}
		} else {
			setState("Finding closest " + config.name().toLowerCase() + " wisp...");
			NPC wisp = NPCs.getClosestReachable(npc -> config.getEnrichedNpcs().contains(npc.getId()) || npc.getName().contains("Enriched"));
			if (wisp == null)
				wisp = NPCs.getClosestReachable(npc -> config.getNormalNpcs().contains(npc.getId()));
			if (wisp != null && !self.isAnimationPlaying()) {
				setState("Clicking closest " + config.name().toLowerCase() + " wisp...");
				wisp.interact("Harvest");
				sleep(2500);
			}
		}
	}
	
	@Override
	public void paintOverlay(long runtime) {
		
	}

	@Override
	public void paintImGui(long runtime) {
		ImGui.label("Energy p/h: " + Time.perHour(runtime, Interfaces.getInventory().count(ENERGIES) - startEnergy));
		ImGui.label("XP p/h: " + Time.perHour(runtime, Client.getStatById(Client.DIVINATION).getXp() - startXp));
	}
	
	public void detectLocation() {
		for (WispConfig c : WispConfig.values()) {
			Npc n = Npcs.closest(npc -> c.getNormalNpcs().contains(npc.getId()));
			if (n != null) {
				config = c;
				break;
			}
		}
	}

	@Override
	public void onStop() {
		
	}
}
