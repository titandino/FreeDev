package com.darkan.plugins.aiodiv;

import kraken.plugin.AbstractPlugin;
import kraken.plugin.api.*;

import java.time.LocalTime;

import com.darkan.kraken.Util;
import com.darkan.kraken.world.WorldObject;
import com.darkan.kraken.world.WorldTile;

public class AIODiv extends AbstractPlugin {
	
	/**
	 * TODO
	 * Chronicle absorbtion prayer support
	 * Better prioritization of enriched
	 */
	
	private static final int GAUS_VARIANCE = 4000;
	private static final int[] ENERGIES = { 29313, 29314, 29315, 29316, 29317, 29318, 29319, 29320, 31312, 29321, 29322, 29323, 29324, 37941 };
	private static final WorldObject ELDER_RIFT = new WorldObject(66522, new WorldTile(4273, 6318, 0));
	private static final WorldObject ELDER_RIFT_CACHE = new WorldObject(93292, new WorldTile(4273, 6318, 0));
	
	private DivConfig config;
	private String state = "Initializing...";
	private int startXp;
	private long started;
	private int startEnergy;
	
	public boolean onLoaded(PluginContext context) {
		context.setName("Trent AIO Divination");
		return true;
	}

	public int onLoop() {
		Player self = Players.self();
		if(self == null) {
			state = "Finding local player...";
			return 3600;
		}
		if (config == null) {
			state = "Detecting location and starting...";
			detectLocation();
			startXp = Client.getStatById(Client.DIVINATION).getXp();
			started = System.currentTimeMillis();
			startEnergy = Util.invCount(ENERGIES);
			return 3600;
		}
		
		int loopDelay = 1500;
		
		Npc serenSpirit = Npcs.closest(npc -> npc.getName().equalsIgnoreCase("seren spirit"));
		if (serenSpirit != null) {
			serenSpirit.interact(Actions.MENU_EXECUTE_NPC1);
			return Util.gaussian(loopDelay + 2500, GAUS_VARIANCE);
		}
		
		if (Inventory.isFull()) {
			state = "Inventory full. Finding closest rift...";
			if (config == DivConfig.ELDER) {
				state = "Inventory full. Clicking closest rift...";
				ELDER_RIFT.interact(Actions.MENU_EXECUTE_OBJECT1);
				if (LocalTime.now().getMinute() >= 0 && LocalTime.now().getMinute() <= 10)
					ELDER_RIFT_CACHE.interact(Actions.MENU_EXECUTE_OBJECT1);
				loopDelay += 2500;
			} else {
				SceneObject rift = SceneObjects.closest(obj -> obj != null && (obj.getId() == 87306 || obj.getId() == 93489 || obj.getId() == 66522)); //TODO API doesn't pick up 66522 for some reason..
				if (rift != null && !self.isAnimationPlaying()) {
					state = "Inventory full. Clicking closest rift...";
					rift.interact(Actions.MENU_EXECUTE_OBJECT1);
					loopDelay += 2500;
				}
			}
		} else {
			state = "Finding closest " + config.name().toLowerCase() + " wisp...";
			Npc wisp = Npcs.closest(npc -> config.getEnrichedNpcs().contains(npc.getId()) || npc.getName().contains("Enriched"));
			if (wisp == null)
				wisp = Npcs.closest(npc -> config.getNormalNpcs().contains(npc.getId()));
			
			if (wisp != null && !self.isAnimationPlaying()) {
				state = "Clicking closest " + config.name().toLowerCase() + " wisp...";
				wisp.interact(Actions.MENU_EXECUTE_NPC1);
				loopDelay += 2500;
			}
		}
		return Util.gaussian(loopDelay, GAUS_VARIANCE);
	}

	public void onPaint() {
		long runtime = System.currentTimeMillis() - started;
		ImGui.label("Trent AIO Divination - " + Time.formatTime(runtime));
		ImGui.label(state);
		
		ImGui.label("Energy p/h: " + Time.perHour(runtime, Util.invCount(ENERGIES) - startEnergy));
		ImGui.label("XP p/h: " + Time.perHour(runtime, Client.getStatById(Client.DIVINATION).getXp() - startXp));
	}
	
	public void detectLocation() {
		for (DivConfig c : DivConfig.values()) {
			Npc n = Npcs.closest(npc -> c.getNormalNpcs().contains(npc.getId()));
			if (n != null) {
				config = c;
				break;
			}
		}
	}
}
