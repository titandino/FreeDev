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
package com.darkan.api.pathing.action.node.impl;

import java.util.ArrayList;
import java.util.List;

import com.darkan.api.entity.MyPlayer;
import com.darkan.api.pathing.FixedTileStrategy;
import com.darkan.api.pathing.LocalPathing;
import com.darkan.api.pathing.action.node.TraversalNode;
import com.darkan.api.profile.PlayerProfiles;
import com.darkan.api.util.Utils;
import com.darkan.api.world.WorldTile;

import kraken.plugin.api.Actions;

public class PathNode extends TraversalNode {
	
	private WorldTile start;
	private WorldTile end;
	private List<WorldTile> path;
	
	private transient long nextClick = 0;
	
	private PathNode() {
		
	}
	
	public PathNode(WorldTile start, WorldTile end) {
		this.start = start;
		this.end = end;
		path = LocalPathing.findLocalRoute(start, 1, new FixedTileStrategy(end), false);
	}

	public List<WorldTile> getPath() {
		return path;
	}
	
	@Override
	public boolean canStart() {
		return Utils.getRouteDistanceTo(MyPlayer.getPosition(), start) != -1;
	}

	@Override
	public boolean process() {
		if (System.currentTimeMillis() > nextClick) {
			WorldTile clickTile = getNextClickPoint();
			Actions.menu(Actions.MENU_EXECUTE_WALK, Utils.random(100) >= PlayerProfiles.get().minimapWalkPerc ? 1 : 0, clickTile.getX(), clickTile.getY(), Utils.random(0, Integer.MAX_VALUE));
			nextClick = System.currentTimeMillis() + Utils.gaussian(PlayerProfiles.get().walkPathClickTime, PlayerProfiles.get().walkPathClickTime / 2);
		}
		return true;
	}
	
	public WorldTile getNextClickPoint() {
		WorldTile myPos = MyPlayer.getPosition();
		WorldTile closest = new WorldTile(0, 0, 0);
		for (WorldTile tile : path) {
			if (Utils.getDistanceTo(myPos, tile) < Utils.getDistanceTo(myPos, closest))
				closest = tile;
		}
		List<WorldTile> futureTiles = new ArrayList<>();
		boolean start = false;
		for (WorldTile tile : path) {
			if (closest.matches(tile))
				start = true;
			if (start)
				futureTiles.add(tile);
		}
		int numFuture = Utils.random(PlayerProfiles.get().futurePathStepMin, PlayerProfiles.get().futurePathStepMax);
		if (numFuture >= futureTiles.size())
			numFuture = Utils.random(futureTiles.size() / 2, futureTiles.size());
		WorldTile target = futureTiles.get(numFuture);
		int tries = 20;
		WorldTile finalTarget = null;
		while (finalTarget == null) {
			if (tries-- < 0)
				finalTarget = target;
			WorldTile att = new WorldTile(target, PlayerProfiles.get().walkPathDeviation);
			int routeDist = Utils.getRouteDistanceTo(target, att);
			if (routeDist != -1 && routeDist <= PlayerProfiles.get().walkPathDeviation+2)
				finalTarget = att;
		}
		return finalTarget;
	}
	
	@Override
	public boolean reached() {
		return next != null ? next.canStart() : Utils.getDistanceTo(MyPlayer.getPosition(), end) <= 2;
	}

	@Override
	public TraversalNode copy() {
		PathNode copy = new PathNode();
		copy.start = this.start;
		copy.end = this.end;
		copy.path = this.path;
		return copy;
	}

}
