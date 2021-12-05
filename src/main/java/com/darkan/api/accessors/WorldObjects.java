// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
//
//  Copyright © 2021 Trenton Kress
//  This file is part of project: FreeDev
//
package com.darkan.api.accessors;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import com.darkan.api.entity.MyPlayer;
import com.darkan.api.util.Area;
import com.darkan.api.util.Utils;
import com.darkan.api.world.SpotAnim;
import com.darkan.api.world.WorldObject;
import com.darkan.api.world.WorldTile;

import kraken.plugin.api.Filter;
import kraken.plugin.api.Players;
import kraken.plugin.api.SceneObjects;
import kraken.plugin.api.Vector3i;

public class WorldObjects {
	
	private static List<WorldObject> OBJECTS = new CopyOnWriteArrayList<>();
	
	public static void update() {
		try {
			List<WorldObject> list = new ArrayList<>();
			Vector3i pos = Players.self().getGlobalPosition();
			SceneObjects.forEach(obj -> {
				if (obj == null || obj.hidden())
					return;
				WorldObject wo = new WorldObject(obj.getId(), new WorldTile(obj.getGlobalPosition()));
				if (pos.getZ() > 0 || wo.getPlane() == pos.getZ())
					list.add(wo);
			});
			OBJECTS.clear();
			OBJECTS.addAll(list);
		} catch(Exception e) {
			
		}
	}
	
	public static WorldObject getClosestTo(WorldTile tile, Filter<WorldObject> filter) {
		Map<Integer, WorldObject> distanceMap = new TreeMap<Integer, WorldObject>();
		List<WorldObject> objects = getNearby(filter);
		for (WorldObject object : objects) {
			if (object != null) {
				int distance = Utils.getDistanceTo(tile, object);
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

	public static List<WorldObject> getAllWithin(Area area) {
		return getAllWithin(area, (item) -> true);
	}

	public static List<WorldObject> getAllWithin(Area area, Filter<WorldObject> filter) {
		return OBJECTS.stream()
				.filter(i -> area.inside(i) && filter.accept(i))
				.collect(Collectors.toList());
	}

	public static WorldObject getClosestWithin(Area area) {
		return getClosestWithin(area, (item) -> true);
	}

	public static WorldObject getClosestWithin(Area area, Filter<WorldObject> filter) {
		return OBJECTS.stream()
				.filter(i -> area.inside(i) && filter.accept(i))
				.min(Comparator.comparingInt(o -> o.getDistance(Players.self().getGlobalPosition())))
				.orElse(null);
	}
	
	public static WorldObject getClosest(Filter<WorldObject> filter) {
		return getClosestTo(MyPlayer.getPosition(), filter);
	}
	
	public static WorldObject getClosestToReachable(WorldTile tile, Filter<WorldObject> filter) {
		Map<Integer, WorldObject> distanceMap = new TreeMap<Integer, WorldObject>();
		List<WorldObject> objects = getNearby(filter);
		for (WorldObject object : objects) {
			if (object != null) {
				int distance = Utils.getRouteDistanceTo(tile, object);
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
		return getClosestToReachable(MyPlayer.getPosition(), filter);
	}

	public static List<WorldObject> getNearby(Filter<WorldObject> filter) {
		List<WorldObject> list = new ArrayList<>();
		for (WorldObject obj : OBJECTS) {
			if (filter == null || filter.accept(obj))
				list.add(obj);
		}
		return list;
	}
	
	public static List<WorldObject> getNearby() {
		return getNearby(null);
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
        WorldObject obj = getClosest(o -> o.hasOption(option) && filter.accept(o));
        if (obj == null)
            return false;
        obj.interact(option);
        return true;
    }
    
	public static List<WorldObject> getOrderedClosest(Filter<WorldObject> filter) {
		Map<Integer, List<WorldObject>> distanceMap = new HashMap<>();
		List<WorldObject> closest = new ArrayList<>();
		List<WorldObject> objects = getNearby(filter);
		WorldTile pTile = new WorldTile(Players.self().getGlobalPosition());
		for (WorldObject object : objects) {
			if (object != null) {
				int distance = Utils.getRouteDistanceTo(pTile, object);
				if (distance != -1) {
					List<WorldObject> nAtDist = distanceMap.get(distance);
					if (nAtDist == null)
						nAtDist = new ArrayList<>();
					nAtDist.add(object);
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
	
	public static List<WorldObject> getOrderedClosest() {
		return getOrderedClosest(null);
	}

}
