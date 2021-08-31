package com.darkan.api.accessors;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import com.darkan.api.item.GroundItem;
import com.darkan.api.util.Area;
import com.darkan.api.util.Utils;
import com.darkan.api.world.WorldTile;

import kraken.plugin.api.Filter;
import kraken.plugin.api.Players;

public class GroundItems {
	
	private static List<GroundItem> GROUND_ITEMS = new CopyOnWriteArrayList<>();
	
	public static void update() {
		try {
			List<GroundItem> list = new ArrayList<>();
			kraken.plugin.api.GroundItems.closest(item -> {
				if (item == null)
					return false;
				GroundItem tItem = new GroundItem(item);
				list.add(tItem);
				return false;
			});
			GROUND_ITEMS.clear();
			GROUND_ITEMS.addAll(list);
		} catch(Exception e) {
			
		}
	}
	
	public static GroundItem getClosest(Filter<GroundItem> filter) {
		Map<Integer, GroundItem> distanceMap = new TreeMap<Integer, GroundItem>();
		List<GroundItem> npcs = getNearby(filter);
		WorldTile pTile = new WorldTile(Players.self().getGlobalPosition());
		for (GroundItem item : npcs) {
			if (item != null) {
				int distance = Utils.getDistanceTo(pTile, item.getPosition());
				if (distance != -1)
					distanceMap.put(distance, item);
			}
		}
		if (distanceMap.isEmpty())
			return null;
		List<Integer> sortedKeys = new ArrayList<Integer>(distanceMap.keySet());
		Collections.sort(sortedKeys);
		return distanceMap.get(sortedKeys.get(0));
	}

	public static List<GroundItem> getAllWithin(Area area) {
		return getAllWithin(area, (item) -> true);
	}

	public static List<GroundItem> getAllWithin(Area area, Filter<GroundItem> filter) {
		return GROUND_ITEMS.stream()
				.filter(i -> area.inside(i.getPosition()) && filter.accept(i))
				.collect(Collectors.toList());
	}

	public static GroundItem getClosestWithin(Area area) {
		return getClosestWithin(area, (item) -> true);
	}

	public static GroundItem getClosestWithin(Area area, Filter<GroundItem> filter) {
		return GROUND_ITEMS.stream()
				.filter(i -> area.inside(i.getPosition()) && filter.accept(i))
				.min(Comparator.comparingInt(o -> o.getPosition().getDistance(Players.self().getGlobalPosition())))
				.orElse(null);
	}

	public static GroundItem getClosestReachable(Filter<GroundItem> filter) {
		Map<Integer, GroundItem> distanceMap = new TreeMap<Integer, GroundItem>();
		List<GroundItem> npcs = getNearby(filter);
		WorldTile pTile = new WorldTile(Players.self().getGlobalPosition());
		for (GroundItem item : npcs) {
			if (item != null) {
				int distance = Utils.getRouteDistanceTo(pTile, item);
				if (distance != -1)
					distanceMap.put(distance, item);
			}
		}
		if (distanceMap.isEmpty())
			return null;
		List<Integer> sortedKeys = new ArrayList<Integer>(distanceMap.keySet());
		Collections.sort(sortedKeys);
		return distanceMap.get(sortedKeys.get(0));
	}
	
    public static boolean interactClosestReachable(String option) {
         GroundItem closest = getClosestReachable(n -> n.hasOption(option));
         if (closest == null)
             return false;
         closest.interact(option);
         return true;
     }
	
    public static boolean interactClosestReachable(String option, Filter<GroundItem> filter) {
         GroundItem closest = getClosestReachable(n -> filter.accept(n) && n.hasOption(option));
         if (closest == null)
             return false;
         closest.interact(option);
         return true;
     }
   
     public static boolean interactClosestReachable(int option, Filter<GroundItem> filter) {
    	 GroundItem closest = getClosestReachable(filter);
         if (closest == null)
             return false;
        closest.interact(option);
        return true;
    }
     
     public static boolean interactClosest(String option) {
         GroundItem closest = getClosest(n -> n.hasOption(option));
         if (closest == null)
             return false;
         closest.interact(option);
         return true;
     }
	
    public static boolean interactClosest(String option, Filter<GroundItem> filter) {
         GroundItem closest = getClosest(n -> filter.accept(n) && n.hasOption(option));
         if (closest == null)
             return false;
         closest.interact(option);
         return true;
     }
   
     public static boolean interactClosest(int option, Filter<GroundItem> filter) {
    	 GroundItem closest = getClosest(filter);
         if (closest == null)
             return false;
        closest.interact(option);
        return true;
    }
	
 	public static List<GroundItem> getNearby(Filter<GroundItem> filter) {
		List<GroundItem> list = new ArrayList<>();
		for (GroundItem obj : GROUND_ITEMS) {
			if (filter == null || filter.accept(obj))
				list.add(obj);
		}
		return list;
	}
	
	public static List<GroundItem> getNearby() {
		return getNearby(null);
	}
	
	public static List<GroundItem> getNearbyReachable() {
		List<GroundItem> reachable = new ArrayList<>();
		List<GroundItem> objects = getNearby();
		WorldTile pTile = new WorldTile(Players.self().getGlobalPosition());
		for (GroundItem item : objects) {
			if (item != null) {
				int distance = Utils.getRouteDistanceTo(pTile, item);
				if (distance != -1)
					reachable.add(item);
			}
		}
		return reachable;
	}
	
	public static List<GroundItem> getOrderedClosest(Filter<GroundItem> filter) {
		Map<Integer, List<GroundItem>> distanceMap = new HashMap<>();
		List<GroundItem> closest = new ArrayList<>();
		List<GroundItem> npcs = getNearby(filter);
		WorldTile pTile = new WorldTile(Players.self().getGlobalPosition());
		for (GroundItem item : npcs) {
			if (item != null) {
				int distance = Utils.getRouteDistanceTo(pTile, item.getPosition());
				if (distance != -1) {
					List<GroundItem> nAtDist = distanceMap.get(distance);
					if (nAtDist == null)
						nAtDist = new ArrayList<>();
					nAtDist.add(item);
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
	
	public static List<GroundItem> getOrderedClosest() {
		return getOrderedClosest(null);
	}
}
