package com.darkan.api.world;

import com.darkan.api.util.Util;
import com.darkan.cache.banditupsetversion.Cache;
import com.darkan.cache.banditupsetversion.dto.ObjectInfo;

import kraken.plugin.api.Actions;

public class WorldObject extends WorldTile implements Interactable {
	private static final int[] MENU_OPS = { 
			Actions.MENU_EXECUTE_OBJECT1, Actions.MENU_EXECUTE_OBJECT2, Actions.MENU_EXECUTE_OBJECT3, 
			Actions.MENU_EXECUTE_OBJECT4, Actions.MENU_EXECUTE_OBJECT5, Actions.MENU_EXECUTE_OBJECT6 
		};
	
	protected int id;
	protected ObjectType type;
	protected int rotation;
	
	public WorldObject(int id, WorldTile tile) {
		super(tile);
		this.id = id;
		this.type = ObjectType.SCENERY_INTERACT;
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
		if (action < 0 || action >= MENU_OPS.length)
			return;
		Actions.menu(MENU_OPS[action], getId(), getX(), getY(), Util.random(0, Integer.MAX_VALUE));
	}
	
	public void interact(String action) {
		int op = getDef().getOption(action);
		if (op != -1)
			interact(op);
	}

	public ObjectInfo getDef() {
		return Cache.getObject(id);
	}

	public String getName() {
		return getDef().name;
	}

	@Override
	public boolean hasOption(String string) {
		return getDef().hasOption(string);
	}
}
