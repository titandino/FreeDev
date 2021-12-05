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
package com.darkan.scripts.impl.aioagility;

import com.darkan.api.util.Area;
import com.darkan.api.world.WorldObject;
import com.darkan.cache.def.objects.ObjectDef;

public class AgilityNode {
	private WorldObject object;
	private int objectId;
	private Area area;

	public AgilityNode(int objectId, Area area) {
		this.objectId = objectId;
		this.area = area;
	}
	
	public AgilityNode(WorldObject object, Area area) {
		this.object = object;
		this.area = area;
	}
	
	public WorldObject getObject() {
		return object;
	}
	
	public String getName() {
		return ObjectDef.get(objectId).name;
	}
	
	public Area getArea() {
		return area;
	}

	public int getObjectId() {
		return objectId;
	}
}
