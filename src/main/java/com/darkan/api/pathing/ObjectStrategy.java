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
package com.darkan.api.pathing;

import com.darkan.api.world.ObjectType;
import com.darkan.api.world.WorldObject;

public class ObjectStrategy extends RouteStrategy {

	private int x;
	private int y;
	private int routeType;
	private ObjectType type;
	private int rotation;
	private int sizeX;
	private int sizeY;
	private int accessBlockFlag;

	public ObjectStrategy(WorldObject object) {
		this.x = object.getX();
		this.y = object.getY();
		this.routeType = getType(object);
		this.type = object.getType();
		this.rotation = object.getRotation();
		this.sizeX = rotation == 0 || rotation == 2 ? object.getDef().sizeX : object.getDef().sizeY;
		this.sizeY = rotation == 0 || rotation == 2 ? object.getDef().sizeY : object.getDef().sizeX;
		this.accessBlockFlag = object.getDef().accessBlockFlag;
		if (rotation != 0)
			accessBlockFlag = ((accessBlockFlag << rotation) & 0xF) + (accessBlockFlag >> (4 - rotation));
	}

	@Override
	public boolean canExit(int currentX, int currentY, int sizeXY, int[][] clip, int clipBaseX, int clipBaseY) {
		switch (routeType) {
		case 0:
			return RouteStrategy.checkWallInteract(clip, currentX - clipBaseX, currentY - clipBaseY, sizeXY, x - clipBaseX, y - clipBaseY, type, rotation);
		case 1:
			return RouteStrategy.checkWallDecorationInteract(clip, currentX - clipBaseX, currentY - clipBaseY, sizeXY, x - clipBaseX, y - clipBaseY, type, rotation);
		case 2:
			return RouteStrategy.checkFilledRectangularInteract(clip, currentX - clipBaseX, currentY - clipBaseY, sizeXY, sizeXY, x - clipBaseX, y - clipBaseY, sizeX, sizeY, accessBlockFlag);
		case 3:
			return currentX == x && currentY == y;
		}
		return false;
	}

	@Override
	public int getApproxDestinationX() {
		return x;
	}

	@Override
	public int getApproxDestinationY() {
		return y;
	}

	@Override
	public int getApproxDestinationSizeX() {
		return sizeX;
	}

	@Override
	public int getApproxDestinationSizeY() {
		return sizeY;
	}

	private int getType(WorldObject object) {
		switch(object.getType()) {
		case WALL_STRAIGHT:
		case WALL_DIAGONAL_CORNER:
		case WALL_WHOLE_CORNER:
		case WALL_STRAIGHT_CORNER:
		case WALL_INTERACT:
			return 0;
		case STRAIGHT_INSIDE_WALL_DEC:
		case STRAIGHT_OUSIDE_WALL_DEC:
		case DIAGONAL_OUTSIDE_WALL_DEC:
		case DIAGONAL_INSIDE_WALL_DEC:
		case DIAGONAL_INWALL_DEC:
			return 1;
		case SCENERY_INTERACT:
		case GROUND_INTERACT:
		case GROUND_DECORATION:
			return 2;
		default:
			return 3;
		}
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof ObjectStrategy))
			return false;
		ObjectStrategy strategy = (ObjectStrategy) other;
		return x == strategy.x && y == strategy.y && routeType == strategy.routeType && type == strategy.type && rotation == strategy.rotation && sizeX == strategy.sizeX && sizeY == strategy.sizeY && accessBlockFlag == strategy.accessBlockFlag;
	}

}
