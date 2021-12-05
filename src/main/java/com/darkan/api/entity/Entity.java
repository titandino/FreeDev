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
package com.darkan.api.entity;

import com.darkan.api.pathing.Direction;
import com.darkan.api.world.WorldTile;

public abstract class Entity {

	protected WorldTile position;
	protected int serverIndex;
	protected boolean moving;
	protected int animationId;
	protected boolean animationPlaying;
	protected int interactingIndex;
	protected Direction dir;

	public Entity(kraken.plugin.api.Spirit spirit) {
		if (spirit != null) {
			this.position = new WorldTile(spirit.getGlobalPosition());
			this.serverIndex = spirit.getServerIndex();
			this.moving = spirit.isMoving();
			this.animationId = spirit.getAnimationId();
			this.animationPlaying = spirit.isAnimationPlaying();
			this.interactingIndex = spirit.getInteractingIndex();
			this.dir = Direction.forDelta((int) spirit.getDirectionOffset().getX(), (int) spirit.getDirectionOffset().getY());
		}
	}
	
	public Entity(WorldTile position) {
		this.position = position;
	}

	public abstract int getSize();
	
	
	public WorldTile getPosition() {
		return position;
	}
	
	public int getServerIndex() {
		return serverIndex;
	}

	public boolean isMoving() {
		return moving;
	}

	public int getAnimationId() {
		return animationId;
	}

	public boolean isAnimationPlaying() {
		return animationPlaying;
	}

	public int getInteractingIndex() {
		return interactingIndex;
	}

	public Direction getDir() {
		return dir;
	}

}
