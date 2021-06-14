package com.darkan.kraken.world;

import kraken.plugin.api.Actions;

public class WorldObject extends WorldTile {
	protected int id;
	protected ObjectType type;
	protected int rotation;
	
	public WorldObject(int id, WorldTile tile) {
		super(tile);
		this.id = id;
	}

	public WorldObject(int id, ObjectType type, int rotation, WorldTile tile) {
		super(tile);
		this.id = id;
		this.type = type;
		this.rotation = rotation;
	}

	public WorldObject(int id, ObjectType type, int rotation, int x, int y, int plane) {
		super(x, y, plane);
		this.id = id;
		this.type = type;
		this.rotation = rotation;
	}

	public WorldObject(WorldObject object) {
		super(object);
		this.id = object.id;
		this.type = object.type;
		this.rotation = object.rotation;
	}
	
	public int getId() {
		return id;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	
	public ObjectType getType() {
		return type;
	}
	
	public void setType(ObjectType i) {
		this.type = i;
	}

	public int getRotation() {
		return rotation;
	}
	
	public int getSlot() {
		return type.slot;
	}
	
	public void interact(int action) {
		Actions.menu(action, getId(), getX(), getY(), 1);
	}
}
