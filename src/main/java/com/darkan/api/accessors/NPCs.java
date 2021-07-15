package com.darkan.api.accessors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.darkan.api.entity.NPC;
import com.darkan.api.util.Utils;
import com.darkan.api.world.WorldTile;

import kraken.plugin.api.Filter;
import kraken.plugin.api.Npcs;
import kraken.plugin.api.Players;

public class NPCs {
	
	private static boolean UPDATING = false;
	private static List<NPC> NPCS = new CopyOnWriteArrayList<>();
	
	public static void update() {
		if (UPDATING)
			return;
		UPDATING = true;
		new Thread(() -> {
			List<NPC> list = new ArrayList<>();
			Npcs.closest(npc -> {
				if (npc == null)
					return false;
				NPC tNpc = new NPC(npc);
				list.add(tNpc);
				return false;
			});
			NPCS.clear();
			NPCS.addAll(list);
			UPDATING = false;
		}).start();
	}
	
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
	
    public static boolean interactClosestReachable(String option) {
         NPC closest = getClosestReachable(n -> n.hasOption(option));
         if (closest == null)
             return false;
         closest.interact(option);
         return true;
     }
	
    public static boolean interactClosestReachable(String option, Filter<NPC> filter) {
         NPC closest = getClosestReachable(n -> filter.accept(n) && n.hasOption(option));
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
     
     public static boolean interactClosest(String option) {
         NPC closest = getClosest(n -> n.hasOption(option));
         if (closest == null)
             return false;
         closest.interact(option);
         return true;
     }
	
    public static boolean interactClosest(String option, Filter<NPC> filter) {
         NPC closest = getClosest(n -> filter.accept(n) && n.hasOption(option));
         if (closest == null)
             return false;
         closest.interact(option);
         return true;
     }
   
     public static boolean interactClosest(int option, Filter<NPC> filter) {
    	 NPC closest = getClosest(filter);
         if (closest == null)
             return false;
        closest.interact(option);
        return true;
    }
	
 	public static List<NPC> getNearby(Filter<NPC> filter) {
		List<NPC> list = new ArrayList<>();
		for (NPC obj : NPCS) {
			if (filter == null || filter.accept(obj))
				list.add(obj);
		}
		return list;
	}
	
	public static List<NPC> getNearby() {
		return getNearby(null);
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
	
	public static List<NPC> getOrderedClosest(Filter<NPC> filter) {
		Map<Integer, List<NPC>> distanceMap = new HashMap<>();
		List<NPC> closest = new ArrayList<>();
		List<NPC> npcs = getNearby(filter);
		WorldTile pTile = new WorldTile(Players.self().getGlobalPosition());
		for (NPC npc : npcs) {
			if (npc != null) {
				int distance = Utils.getRouteDistanceTo(pTile, npc.getPosition());
				if (distance != -1) {
					List<NPC> nAtDist = distanceMap.get(distance);
					if (nAtDist == null)
						nAtDist = new ArrayList<>();
					nAtDist.add(npc);
					distanceMap.put(distance, nAtDist);
				}
			}
		}
		if (distanceMap.isEmpty())
			return closest;
		List<Integer> sortedKeys = new ArrayList<Integer>(distanceMap.keySet());
		Collections.sort(sortedKeys);
		for (int key : sortedKeys)
			closest.addAll(distanceMap.get(key));
		return closest;
	}
	
	public static List<NPC> getOrderedClosest() {
		return getOrderedClosest(null);
	}
}
