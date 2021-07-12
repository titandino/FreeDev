package com.darkan.api.accessors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.darkan.api.entity.NPC;
import com.darkan.api.util.Utils;
import com.darkan.api.world.WorldTile;

import kraken.plugin.api.Filter;
import kraken.plugin.api.Npcs;
import kraken.plugin.api.Players;

public class NPCs {
	
	public static NPC getClosest(Filter<NPC> filter) {
		Map<Integer, NPC> distanceMap = new TreeMap<Integer, NPC>();
		List<NPC> npcs = getNearby(filter);
		WorldTile pTile = new WorldTile(Players.self().getGlobalPosition());
		for (NPC npc : npcs) {
			if (npc != null) {
				int distance = Utils.getDistanceTo(pTile, npc.getPosition());
				if (distance != -1)
					distanceMap.put(distance, npc);
			}
		}
		if (distanceMap.isEmpty())
			return null;
		List<Integer> sortedKeys = new ArrayList<Integer>(distanceMap.keySet());
		Collections.sort(sortedKeys);
		return distanceMap.get(sortedKeys.get(0));
	}

	public static NPC getClosestReachable(Filter<NPC> filter) {
		Map<Integer, NPC> distanceMap = new TreeMap<Integer, NPC>();
		List<NPC> npcs = getNearby(filter);
		WorldTile pTile = new WorldTile(Players.self().getGlobalPosition());
		for (NPC npc : npcs) {
			if (npc != null) {
				int distance = Utils.getRouteDistanceTo(pTile, npc);
				if (distance != -1)
					distanceMap.put(distance, npc);
			}
		}
		if (distanceMap.isEmpty())
			return null;
		List<Integer> sortedKeys = new ArrayList<Integer>(distanceMap.keySet());
		Collections.sort(sortedKeys);
		return distanceMap.get(sortedKeys.get(0));
	}
	
    public static boolean interactClosestReachable(String option, Filter<NPC> filter) {
         NPC closest = getClosestReachable(filter);
         if (closest == null)
             return false;
         closest.interact(option);
         return true;
     }
   
     public static boolean interactClosestReachable(int option, Filter<NPC> filter) {
    	 NPC closest = getClosestReachable(filter);
         if (closest == null)
             return false;
        closest.interact(option);
        return true;
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
		WorldTile pTile = new WorldTile(Players.self().getGlobalPosition());
		for (NPC npc : objects) {
			if (npc != null) {
				int distance = Utils.getRouteDistanceTo(pTile, npc);
				if (distance != -1)
					reachable.add(npc);
			}
		}
		return reachable;
	}
}
