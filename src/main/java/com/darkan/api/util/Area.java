package com.darkan.api.util;

import com.darkan.api.world.WorldTile;

import kraken.plugin.api.Vector3i;

public class Area {

	private int minX;
	private int maxX;
	private int minY;
	private int maxY;
	
	public Area(WorldTile t1, WorldTile t2) {
		minX = Math.min(t1.getX(), t2.getX());
		maxX = Math.max(t1.getX(), t2.getX());
		minY = Math.min(t1.getY(), t2.getY());
		maxY = Math.max(t1.getY(), t2.getY());
	}
	
	public boolean inside(WorldTile tile) {
		return tile.getX() >= minX && tile.getX() <= maxX && tile.getY() >= minY && tile.getY() <= maxY;
	}
	
	public boolean inside(Vector3i tile) {
		return tile.getX() >= minX && tile.getX() <= maxX && tile.getY() >= minY && tile.getY() <= maxY;
	}
}
