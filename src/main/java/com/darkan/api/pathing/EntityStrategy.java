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

import com.darkan.api.entity.Entity;

public class EntityStrategy extends RouteStrategy {

	private int x;
	private int y;
	private int size;
	private int accessBlockFlag;

	public EntityStrategy(Entity entity) {
		this(entity, 0);
	}

	public EntityStrategy(Entity entity, int accessBlockFlag) {
		this.x = entity.getPosition().getX();
		this.y = entity.getPosition().getY();
		this.size = entity.getSize();
		this.accessBlockFlag = accessBlockFlag;
	}

	@Override
	public boolean canExit(int currentX, int currentY, int sizeXY, int[][] clip, int clipBaseX, int clipBaseY) {
		return RouteStrategy.checkFilledRectangularInteract(clip, currentX - clipBaseX, currentY - clipBaseY, sizeXY, sizeXY, x - clipBaseX, y - clipBaseY, size, size, accessBlockFlag);
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
		return size;
	}

	@Override
	public int getApproxDestinationSizeY() {
		return size;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof EntityStrategy))
			return false;
		EntityStrategy strategy = (EntityStrategy) other;
		return x == strategy.x && y == strategy.y && size == strategy.size && accessBlockFlag == strategy.accessBlockFlag;
	}

}
