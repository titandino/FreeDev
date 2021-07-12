package com.darkan.scripts.aiowisp;

import java.awt.Color;

import com.darkan.api.accessors.NPCs;
import com.darkan.api.accessors.WorldObjects;
import com.darkan.api.entity.NPC;
import com.darkan.api.inter.Interfaces;
import com.darkan.api.util.Paint;
import com.darkan.api.world.WorldObject;
import com.darkan.scripts.Script;
import com.darkan.scripts.ScriptSkeleton;

import kraken.plugin.api.Client;
import kraken.plugin.api.ImGui;
import kraken.plugin.api.Npc;
import kraken.plugin.api.Npcs;
import kraken.plugin.api.Player;
import kraken.plugin.api.Time;
import kraken.plugin.api.Vector2i;

@Script("AIO Wisp Gatherer")
public class AIOWispGathering extends ScriptSkeleton {

	/**
	 * TODO
	 * Chronicle absorption prayer support
	 * Better prioritization of enriched
	 */
		
	private WispConfig config;
	private int startXp;
	private int startEnergy;
	
	public AIOWispGathering() {
		super("AIO Wisp Gathering", 1000);
	}
	
	@Override
	public boolean onStart(Player self) {
		if (config == null) {
			setState("Detecting location and starting...");
			detectLocation();
			startXp = Client.getStatById(Client.DIVINATION).getXp();
			startEnergy = Interfaces.getInventory().countReg(" energy");
			start();
			return false;
		}
		return true;
	}
	
	@Override
	public void loop(Player self) {
		if (Interfaces.getInventory().isFull()) {
			setState("Inventory full. Finding closest rift...");
			WorldObject rift = WorldObjects.getClosest(obj -> obj.hasOption("Convert memories"));
			if (rift != null && !self.isAnimationPlaying()) {
				setState("Inventory full. Clicking closest rift...");
				rift.interact("Convert memories");
				sleepWhile(3500, 51526, () -> Interfaces.getInventory().containsAnyReg(" memory"));
			}
		} else {
			setState("Finding closest " + config.name().toLowerCase() + " wisp...");
			NPC wisp = NPCs.getClosestReachable(npc -> config.getEnrichedNpcs().contains(npc.getId()) || npc.getName().contains("Enriched"));
			if (wisp == null)
				wisp = NPCs.getClosestReachable(npc -> config.getNormalNpcs().contains(npc.getId()));
			if (wisp != null && !self.isAnimationPlaying()) {
				setState("Clicking closest " + config.name().toLowerCase() + " wisp...");
				wisp.interact("Harvest");
				sleepWhile(3100, 73513, () -> self.isAnimationPlaying() && !Interfaces.getInventory().isFull());
			}
		}
	}

	@Override
	public void paintOverlay(long runtime) {
		Paint.rect(0, 0, 300, 150, Color.GREEN);
		Paint.rect(5, 5, 290, 140, Color.DARK_GRAY);
		ImGui.freeText("Energy p/h: " + Time.perHour(runtime, Interfaces.getInventory().countReg(" energy") - startEnergy), new Vector2i(15, 20), Color.GREEN.getRGB());
	}

	@Override
	public void paintImGui(long runtime) {
		ImGui.label("Energy p/h: " + Time.perHour(runtime, Interfaces.getInventory().countReg(" energy") - startEnergy));
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
