package com.darkan.api.pathing.action.node.impl;

import java.util.List;

import com.darkan.api.entity.MyPlayer;
import com.darkan.api.pathing.FixedTileStrategy;
import com.darkan.api.pathing.Pathing;
import com.darkan.api.pathing.action.node.TraversalNode;
import com.darkan.api.util.Utils;
import com.darkan.api.world.WorldTile;

public class PathNode extends TraversalNode {
	
	private WorldTile start;
	private WorldTile end;
	private List<WorldTile> path;
	
	public PathNode(WorldTile start, WorldTile end) {
		this.start = start;
		this.end = end;
		path = Pathing.findRoute(start, 1, new FixedTileStrategy(end.getX(), end.getY()), false);
	}

	public List<WorldTile> getPath() {
		return path;
	}
	
	@Override
	public boolean canStart() {
		return Utils.getRouteDistanceTo(new WorldTile(MyPlayer.get().getGlobalPosition()), start) != -1;
	}

	@Override
	public boolean process() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean reached() {
		return next != null ? next.canStart() : Utils.getDistanceTo(new WorldTile(MyPlayer.get().getGlobalPosition()), end) <= 2;
	}

	@Override
	public TraversalNode copy() {
		return null; //TODO
	}

}
