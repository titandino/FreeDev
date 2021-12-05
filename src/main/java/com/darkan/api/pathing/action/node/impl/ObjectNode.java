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
package com.darkan.api.pathing.action.node.impl;

import java.util.function.Supplier;

import com.darkan.api.accessors.WorldObjects;
import com.darkan.api.entity.MyPlayer;
import com.darkan.api.pathing.action.node.TraversalNode;
import com.darkan.api.profile.PlayerProfiles;
import com.darkan.api.util.Area;
import com.darkan.api.util.Utils;
import com.darkan.api.world.WorldObject;

public class ObjectNode extends TraversalNode {
	
	private Object objIdentifier;
	private String action;
	private Supplier<Boolean> customReached;
	private Area destination;
	
	private transient long nextClick = 0;
	
	private ObjectNode() {
		
	}
	
	public ObjectNode(WorldObject object, String action, Area destination, Supplier<Boolean> customReached) {
		this.objIdentifier = object;
		this.action = action;
		this.destination = destination;
		this.customReached = customReached;
	}
	
	public ObjectNode(String objectName, String action, Area destination, Supplier<Boolean> customReached) {
		this.objIdentifier = objectName;
		this.action = action;
		this.destination = destination;
		this.customReached = customReached;
	}
	
	public ObjectNode(int objectId, String action, Area destination, Supplier<Boolean> customReached) {
		this.objIdentifier = objectId;
		this.action = action;
		this.destination = destination;
		this.customReached = customReached;
	}
	
	public ObjectNode(WorldObject object, String action, Area destination) {
		this(object, action, destination, null);
	}
	
	public ObjectNode(String objectName, String action, Area destination) {
		this(objectName, action, destination, null);
	}
	
	public ObjectNode(int objectId, String action, Area destination) {
		this(objectId, action, destination, null);
	}
	
	public ObjectNode(WorldObject object, String action, Supplier<Boolean> customReached) {
		this(object, action, null, customReached);
	}
	
	public ObjectNode(String objectName, String action, Supplier<Boolean> customReached) {
		this(objectName, action, null, customReached);
	}
	
	public ObjectNode(int objectId, String action, Supplier<Boolean> customReached) {
		this(objectId, action, null, customReached);
	}
	
	public ObjectNode(WorldObject object, Area destination) {
		this(object, null, destination, null);
	}
	
	public ObjectNode(String objectName, Area destination) {
		this(objectName, null, destination, null);
	}
	
	public ObjectNode(int objectId, Area destination) {
		this(objectId, null, destination, null);
	}
	
	public ObjectNode(WorldObject object, Supplier<Boolean> customReached) {
		this(object, null, null, customReached);
	}
	
	public ObjectNode(String objectName, Supplier<Boolean> customReached) {
		this(objectName, null, null, customReached);
	}
	
	public ObjectNode(int objectId, Supplier<Boolean> customReached) {
		this(objectId, null, null, customReached);
	}

	@Override
	public boolean canStart() {
		WorldObject target = getTarget();
		return target != null && Utils.withinDistance(MyPlayer.getPosition(), target) && Utils.getRouteDistanceTo(MyPlayer.getPosition(), target) != -1;
	}

	private WorldObject getTarget() {
		WorldObject target = null;
		if (objIdentifier instanceof WorldObject)
			target = ((WorldObject) objIdentifier);
		else if (objIdentifier instanceof String)
			target = WorldObjects.getClosest(obj -> obj.getName().contains((String) objIdentifier) && (action == null || obj.hasOption(action)));
		else if (objIdentifier instanceof Integer)
			target = WorldObjects.getClosest(obj -> obj.getId() == ((int) objIdentifier) && (action == null || obj.hasOption(action)));
		return target;
	}

	@Override
	public boolean process() {
		if (System.currentTimeMillis() < nextClick)
			return true;
		WorldObject target = getTarget();
		if (target != null && ((action != null && target.interact(action)) || target.interact(0))) {
			nextClick = System.currentTimeMillis() + Utils.gaussian(PlayerProfiles.get().walkPathClickTime, PlayerProfiles.get().walkPathClickTime / 2);
			return true;
		}
		return false;
	}

	@Override
	public boolean reached() {
		return (customReached != null && customReached.get()) || destination.inside(MyPlayer.getPosition());
	}

	@Override
	public TraversalNode copy() {
		ObjectNode copy = new ObjectNode();
		copy.objIdentifier = this.objIdentifier;
		copy.action = this.action;
		copy.customReached = this.customReached;
		copy.destination = this.destination;
		return copy;
	}
	

}
