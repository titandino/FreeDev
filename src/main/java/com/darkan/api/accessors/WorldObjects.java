package com.darkan.api.accessors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.darkan.api.util.Utils;
import com.darkan.api.world.WorldObject;
import com.darkan.api.world.WorldTile;

import kraken.plugin.api.Filter;
import kraken.plugin.api.Players;
import kraken.plugin.api.SceneObjects;

public class WorldObjects {
	
	public static WorldObject getClosest(Filter<WorldObject> filter) {
		Map<Integer, WorldObject> distanceMap = new TreeMap<Integer, WorldObject>();
		List<WorldObject> objects = getNearby(filter);
		WorldTile pTile = new WorldTile(Players.self().getGlobalPosition());
		for (WorldObject object : objects) {
			if (object != null) {
				int distance = Utils.getDistanceTo(pTile, object);
				if (distance != -1)
					distanceMap.put(distance, object);
			}
		}
		if (distanceMap.isEmpty())
			return null;
		List<Integer> sortedKeys = new ArrayList<Integer>(distanceMap.keySet());
		Collections.sort(sortedKeys);
		return distanceMap.get(sortedKeys.get(0));
	}

	public static WorldObject getClosestReachable(Filter<WorldObject> filter) {
		Map<Integer, WorldObject> distanceMap = new TreeMap<Integer, WorldObject>();
		List<WorldObject> objects = getNearby(filter);
		WorldTile pTile = new WorldTile(Players.self().getGlobalPosition());
		for (WorldObject object : objects) {
			if (object != null) {
				int distance = Utils.getRouteDistanceTo(pTile, object);
				if (distance != -1)
					distanceMap.put(distance, object);
			}
		}
		if (distanceMap.isEmpty())
			return null;
		List<Integer> sortedKeys = new ArrayList<Integer>(distanceMap.keySet());
		Collections.sort(sortedKeys);
		return distanceMap.get(sortedKeys.get(0));
	}

	public static List<WorldObject> getNearby(Filter<WorldObject> filter) {
		List<WorldObject> list = new ArrayList<>();
		SceneObjects.closest(obj -> {
			WorldObject wo = new WorldObject(obj.getId(), new WorldTile(obj.getGlobalPosition()));
			if (wo.getPlane() == Players.self().getGlobalPosition().getZ() && filter.accept(wo))
				list.add(wo);
			return false;
		});
		return list;
	}
	
	public static List<WorldObject> getNearby() {
		List<WorldObject> list = new ArrayList<>();
		SceneObjects.closest(obj -> {
			if (obj.getGlobalPosition().getZ() == Players.self().getGlobalPosition().getZ())
				list.add(new WorldObject(obj.getId(), new WorldTile(obj.getGlobalPosition().getX(), obj.getGlobalPosition().getY(), obj.getGlobalPosition().getZ())));
			return false;
		});
		return list;
	}
	
	public static List<WorldObject> getNearbyReachable() {
		List<WorldObject> reachable = new ArrayList<>();
		List<WorldObject> objects = getNearby();
		WorldTile pTile = new WorldTile(Players.self().getGlobalPosition());
		for (WorldObject object : objects) {
			if (object != null) {
				int distance = Utils.getRouteDistanceTo(pTile, object);
				if (distance != -1)
					reachable.add(object);
			}
		}
		return reachable;
	}
    
    public static boolean interactClosestReachable(int option, Filter<WorldObject> filter) {
        WorldObject obj = getClosestReachable(filter);
        if (obj == null)
            return false;
        obj.interact(option);
        return true;
    }
    
    public static boolean interactClosestReachable(String option) {
        WorldObject obj = getClosestReachable(o -> o.hasOption(option));
        if (obj == null)
            return false;
        obj.interact(option);
        return true;
    }

    public static boolean interactClosestReachable(String option, Filter<WorldObject> filter) {
        WorldObject obj = getClosestReachable(filter);
        if (obj == null)
            return false;
        obj.interact(option);
        return true;
    }
    
    public static boolean interactClosest(int option, Filter<WorldObject> filter) {
        WorldObject obj = getClosest(filter);
        if (obj == null)
            return false;
        obj.interact(option);
        return true;
    }
    
    public static boolean interactClosest(String option) {
        WorldObject obj = getClosest(o -> o.hasOption(option));
        if (obj == null)
            return false;
        obj.interact(option);
        return true;
    }

    public static boolean interactClosest(String option, Filter<WorldObject> filter) {
        WorldObject obj = getClosest(filter);
        if (obj == null)
            return false;
        obj.interact(option);
        return true;
    }

}
