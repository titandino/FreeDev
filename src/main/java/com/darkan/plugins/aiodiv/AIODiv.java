package com.darkan.plugins.aiodiv;

import kraken.plugin.AbstractPlugin;
import kraken.plugin.api.*;

import static kraken.plugin.api.Actions.*;
import static kraken.plugin.api.Client.*;
import static kraken.plugin.api.Player.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.darkan.kraken.Util;

public class AIODiv extends AbstractPlugin {
	
	//Can change depending on player but ideally would want to change this to be
	//attached to each player instance itself.
	private static final int GAUS_VARIANCE = 4000;
	
	private static final int[] ENERGIES = { 29313, 29314, 29315, 29316, 29317, 29318, 29319, 29320, 31312, 29321, 29322, 29323, 29324, 37941 };
	private static final Set<Integer> RIFT_IDS = new HashSet<>(Arrays.asList(new Integer[] { 87307, 87308, 87309, 87310 }));
	
	private DivConfig config = DivConfig.PALE;
	private int startXp;
	private long started;
	private int startEnergy;
	
	public boolean onLoaded(PluginContext context) {
		context.setName("Trent AIO Divination");
		
		//Initialize stat keeping variables
		startXp = Client.getStatById(Client.DIVINATION).getXp();
		started = System.currentTimeMillis();
		startEnergy = Util.invCount(ENERGIES);
		
		//Detect tier based on where player is at
		for (DivConfig c : DivConfig.values()) {
			Npc n = Npcs.closest(npc -> c.getNormalNpcs().contains(npc.getId()));
			if (n != null) {
				config = c;
				break;
			}
		}
	
		return true;
	}

	public int onLoop() {
		int loopDelay = 1500;
		
		Player self = Players.self();
		if (Inventory.isFull()) {
			SceneObject rift = SceneObjects.closest(obj -> RIFT_IDS.contains(obj.getId()));
			if (rift != null && !self.isMoving() && !self.isAnimationPlaying()) {
				rift.interact(1);
				loopDelay += 2500;
			}
		} else {
			Npc wisp = Npcs.closest(npc -> config.getEnrichedNpcs().contains(npc.getId()));
			if (wisp == null)
				wisp = Npcs.closest(npc -> config.getNormalNpcs().contains(npc.getId()));
			
			if (wisp != null && !self.isMoving() && !self.isAnimationPlaying())
				wisp.interact(1);
		}
		return Util.gaussian(loopDelay, GAUS_VARIANCE);
	}

	public void onPaint() {
		long runtime = System.currentTimeMillis() - started;
		ImGui.label("Trent AIO Divination");
		
		ImGui.label("Energy p/h: " + Time.perHour(runtime, Util.invCount(ENERGIES) - startEnergy));
		ImGui.label("XP p/h: " + Time.perHour(runtime, Client.getStatById(Client.DIVINATION).getXp() - startXp));
	}
}
