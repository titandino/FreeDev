package com.darkan.kraken.entity;

import com.darkan.kraken.world.WorldTile;

public abstract class Entity {

	protected WorldTile position;
	
	public Entity(kraken.plugin.api.Entity memEntity) {
		if (memEntity != null)
			this.position = new WorldTile(memEntity.getGlobalPosition());
	}
	
	public Entity(WorldTile position) {
		this.position = position;
	}

	public abstract int getSize();
	
	
	public WorldTile getPosition() {
		return position;
	}
	
	
}