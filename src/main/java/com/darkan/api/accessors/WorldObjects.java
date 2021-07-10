package com.darkan.api.accessors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.darkan.api.pathing.ObjectStrategy;
import com.darkan.api.pathing.Pathing;
import com.darkan.api.world.WorldObject;
import com.darkan.api.world.WorldTile;

import kraken.plugin.api.Filter;
import kraken.plugin.api.Player;
import kraken.plugin.api.Players;
import kraken.plugin.api.SceneObjects;

public class WorldObjects {

	public static WorldObject getClosestReachable(Filter<WorldObject> filter) {
		Map<Integer, WorldObject> distanceMap = new TreeMap<Integer, WorldObject>();
		List<WorldObject> objects = getNearby(filter);
		for (WorldObject object : objects) {
			if (object != null) {
				int distance = getDistanceTo(object);
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
	
	private static int getDistanceTo(WorldObject object) {
		Player player = Players.self();
		WorldTile pTile = new WorldTile(player.getGlobalPosition());
		return Pathing.getStepsTo(pTile.getX(), pTile.getY(), pTile.getPlane(), 1, new ObjectStrategy(object), false);
	}

	public static List<WorldObject> getNearby(Filter<WorldObject> filter) {
		List<WorldObject> list = new ArrayList<>();
		SceneObjects.closest(obj -> {
			WorldObject wo = new WorldObject(obj.getId(), new WorldTile(obj.getGlobalPosition()));
			if (filter.accept(wo))
				list.add(wo);
			return false;
		});
		return list;
	}
	
	public static List<WorldObject> getNearby() {
		List<WorldObject> list = new ArrayList<>();
		SceneObjects.closest(obj -> {
			list.add(new WorldObject(obj.getId(), new WorldTile(obj.getGlobalPosition().getX(), obj.getGlobalPosition().getY(), obj.getGlobalPosition().getZ())));
			return false;
		});
		return list;
	}
	
	public static List<WorldObject> getNearbyReachable() {
		List<WorldObject> reachable = new ArrayList<>();
		List<WorldObject> objects = getNearby();
		for (WorldObject object : objects) {
			if (object != null) {
				int distance = getDistanceTo(object);
				if (distance != -1)
					reachable.add(object);
			}
		}
		return reachable;
	}
}