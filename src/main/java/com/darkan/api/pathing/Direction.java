package com.darkan.api.pathing;

public enum Direction {
	NORTH(0, 0, 1),
	NORTHEAST(1, 1, 1),
	EAST(2, 1, 0),
	SOUTHEAST(3, 1, -1),
	SOUTH(4, 0, -1),
	SOUTHWEST(5, -1, -1),
	WEST(6, -1, 0),
	NORTHWEST(7, -1, 1);
	
	private int id;
	private int dx;
	private int dy;
	
	private Direction(int id, int dx, int dy) {
		this.id = id;
		this.dx = dx;
		this.dy = dy;
	}
	
	public int getId() {
		return id;
	}
	
	public boolean isDiagonal() {
		return dx != 0 && dy != 0;
	}
	
	public int getDx() {
		return dx;
	}
	
	public int getDy() {
		return dy;
	}

	public static Direction getById(int id) {
		switch (id) {
		case 0:
			return NORTH;
		case 1:
			return NORTHEAST;
		case 2:
			return EAST;
		case 3:
			return SOUTHEAST;
		case 4:
			return SOUTH;
		case 5:
			return SOUTHWEST;
		case 6:
			return WEST;
		case 7:
			return NORTHWEST;
		default:
			return SOUTH;
		}
	}
	
	public static Direction forDelta(int dx, int dy) {
		if (dy >= 1 && dx >= 1) {
			return NORTHEAST;
		} else if (dy <= -1 && dx >= 1) {
			return SOUTHEAST;
		} else if (dy <= -1 && dx <= -1) {
			return SOUTHWEST;
		} else if (dy >= 1 && dx <= -1) {
			return NORTHWEST;
		} else if (dy >= 1) {
			return NORTH;
		} else if (dx >= 1) {
			return EAST;
		} else if (dy <= -1) {
			return SOUTH;
		} else if (dx <= -1) {
			return WEST;
		} else {
			return null;
		}
	}
}
