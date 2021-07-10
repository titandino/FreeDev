package com.darkan.api.accessors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.darkan.api.entity.NPC;
import com.darkan.api.pathing.EntityStrategy;
import com.darkan.api.pathing.Pathing;
import com.darkan.api.world.WorldTile;

import kraken.plugin.api.Debug;
import kraken.plugin.api.Filter;
import kraken.plugin.api.Npcs;
import kraken.plugin.api.Player;
import kraken.plugin.api.Players;

public class NPCs {

	public static NPC getClosestReachable(Filter<NPC> filter) {
		Map<Integer, NPC> distanceMap = new TreeMap<Integer, NPC>();
		List<NPC> npcs = getNearby(filter);
		Debug.log(npcs.toString());
		for (NPC npc : npcs) {
			if (npc != null) {
				int distance = getDistanceTo(npc);
				if (distance != -1)
					distanceMap.put(distance, npc);
			}
		}
		if (distanceMap.isEmpty())
			return null;
		List<Integer> sortedKeys = new ArrayList<Integer>(distanceMap.keySet());
		Debug.log(sortedKeys.toString());
		Collections.sort(sortedKeys);
		return distanceMap.get(sortedKeys.get(0));
	}
	
	private static int getDistanceTo(NPC object) {
		Player player = Players.self();
		WorldTile pTile = new WorldTile(player.getGlobalPosition());
		return Pathing.getStepsTo(pTile.getX(), pTile.getY(), pTile.getPlane(), 1, new EntityStrategy(object), false);
	}

	public static List<NPC> getNearby(Filter<NPC> filter) {
		List<NPC> list = new ArrayList<>();
		Npcs.closest(n -> {
			NPC npc = new NPC(n);
			if (filter.accept(npc))
				list.add(npc);
			return false;
		});
		return list;
	}
	
	public static List<NPC> getNearby() {
		List<NPC> list = new ArrayList<>();
		Npcs.closest(n -> {
			list.add(new NPC(n));
			return false;
		});
		return list;
	}
	
	public static List<NPC> getNearbyReachable() {
		List<NPC> reachable = new ArrayList<>();
		List<NPC> objects = getNearby();
		for (NPC npc : objects) {
			if (npc != null) {
				int distance = getDistanceTo(npc);
				if (distance != -1)
					reachable.add(npc);
			}
		}
		return reachable;
	}
}
