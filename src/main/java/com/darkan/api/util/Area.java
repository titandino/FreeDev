package com.darkan.api.util;

import com.darkan.api.world.WorldTile;

import kraken.plugin.api.Vector3i;

import java.util.ArrayList;
import java.util.List;

public class Area {

	private int minX;
	private int maxX;
	private int minY;
	private int maxY;
	
	public Area(WorldTile t1, WorldTile t2) {
		if (t1 != null && t2 != null) {
			minX = Math.min(t1.getX(), t2.getX());
			maxX = Math.max(t1.getX(), t2.getX());
			minY = Math.min(t1.getY(), t2.getY());
			maxY = Math.max(t1.getY(), t2.getY());
		}
	}
	
	public Area(int cornerX1, int cornerY1, int cornerX2, int cornerY2) {
		minX = Math.min(cornerX1, cornerX2);
		maxX = Math.max(cornerX1, cornerX2);
		minY = Math.min(cornerY1, cornerY2);
		maxY = Math.max(cornerY1, cornerY2);
	}
	
	public boolean inside(WorldTile tile) {
		return tile.getX() >= minX && tile.getX() <= maxX && tile.getY() >= minY && tile.getY() <= maxY;
	}
	
	public boolean inside(Vector3i tile) {
		return tile.getX() >= minX && tile.getX() <= maxX && tile.getY() >= minY && tile.getY() <= maxY;
	}

	public WorldTile[] toTiles() {
		List<WorldTile> tiles = new ArrayList<>();
		for(int x = minX; x <= maxX; x++) {
			for(int y = minY; y <= maxY; y++) {
				tiles.add(new WorldTile(x, y));
			}
		}
		return tiles.toArray(WorldTile[]::new);
	}

	public String toCopyString() {
		return "new Area(new WorldTile("+getMinX()+", "+getMinY()+"), new WorldTile("+getMaxX()+", "+getMaxY()+"))";
	}

	public int getMinX() {
		return minX;
	}

	public int getMaxX() {
		return maxX;
	}

	public int getMinY() {
		return minY;
	}

	public int getMaxY() {
		return maxY;
	}

	public String toString() {
		return "["+minX+", "+minY+"] x ["+maxX+", "+maxY+"]";
	}
}
