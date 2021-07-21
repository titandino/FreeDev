package com.darkan.api.pathing.action.node.impl;

import java.util.List;

import com.darkan.api.pathing.FixedTileStrategy;
import com.darkan.api.pathing.Pathing;
import com.darkan.api.pathing.action.node.TraversalNode;
import com.darkan.api.world.WorldTile;

public class PathNode extends TraversalNode {
	
	private List<WorldTile> path;
	
	public PathNode(WorldTile start, WorldTile end) {
		path = Pathing.findRoute(start.getX(), start.getY(), start.getPlane(), 1, new FixedTileStrategy(end.getX(), end.getY()), false);
	}

	public List<WorldTile> getPath() {
		return path;
	}
	
	@Override
	public boolean canStart() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean process() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean reached() {
		return false;
	}

	@Override
	public TraversalNode copy() {
		// TODO Auto-generated method stub
		return null;
	}

}
