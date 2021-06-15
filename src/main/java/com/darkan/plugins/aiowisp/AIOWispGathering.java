package com.darkan.plugins.aiowisp;

import kraken.plugin.api.Actions;
import kraken.plugin.api.Client;
import kraken.plugin.api.ImGui;
import kraken.plugin.api.Inventory;
import kraken.plugin.api.Npc;
import kraken.plugin.api.Npcs;
import kraken.plugin.api.Player;
import kraken.plugin.api.SceneObject;
import kraken.plugin.api.SceneObjects;
import kraken.plugin.api.Time;

import java.time.LocalTime;

import com.darkan.kraken.world.WorldObject;
import com.darkan.kraken.world.WorldTile;
import com.darkan.plugins.PluginSkeleton;

public class AIOWispGathering extends PluginSkeleton {

	/**
	 * TODO
	 * Chronicle absorption prayer support
	 * Better prioritization of enriched
	 */
	
	private static final int[] ENERGIES = { 29313, 29314, 29315, 29316, 29317, 29318, 29319, 29320, 31312, 29321, 29322, 29323, 29324, 37941 };
	private static final WorldObject ELDER_RIFT = new WorldObject(66522, new WorldTile(4273, 6318, 0));
	private static final WorldObject ELDER_RIFT_CACHE = new WorldObject(93494, new WorldTile(4273, 6318, 0));
	
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
			startEnergy = Inventory.count(ENERGIES);
			start();
			return false;
		}
		return true;
	}
	
	@Override
	public void loop(Player self) {
		if (Inventory.isFull()) {
			setState("Inventory full. Finding closest rift...");
			if (config == WispConfig.ELDER) {
				setState("Inventory full. Clicking closest rift...");
				ELDER_RIFT.interact(Actions.MENU_EXECUTE_OBJECT1);
				if (LocalTime.now().getMinute() >= 0 && LocalTime.now().getMinute() <= 10)
					ELDER_RIFT_CACHE.interact(Actions.MENU_EXECUTE_OBJECT1);
				sleep(2500);
			} else {
				SceneObject rift = SceneObjects.closest(obj -> obj != null && (obj.getId() == 87306 || obj.getId() == 93489 || obj.getId() == 66522)); //TODO API doesn't pick up 66522 for some reason..
				if (rift != null && !self.isAnimationPlaying()) {
					setState("Inventory full. Clicking closest rift...");
					rift.interact(Actions.MENU_EXECUTE_OBJECT1);
					sleep(2500);
				}
			}
		} else {
			setState("Finding closest " + config.name().toLowerCase() + " wisp...");
			Npc wisp = Npcs.closest(npc -> config.getEnrichedNpcs().contains(npc.getId()) || npc.getName().contains("Enriched"));
			if (wisp == null)
				wisp = Npcs.closest(npc -> config.getNormalNpcs().contains(npc.getId()));
			
			if (wisp != null && !self.isAnimationPlaying()) {
				setState("Clicking closest " + config.name().toLowerCase() + " wisp...");
				wisp.interact(Actions.MENU_EXECUTE_NPC1);
				sleep(2500);
			}
		}
	}

	@Override
	public void paint(long runtime) {
		ImGui.label("Energy p/h: " + Time.perHour(runtime, Inventory.count(ENERGIES) - startEnergy));
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
}
