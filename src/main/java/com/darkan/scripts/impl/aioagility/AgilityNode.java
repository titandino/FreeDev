package com.darkan.scripts.impl.aioagility;

import com.darkan.api.util.Area;
import com.darkan.cache.def.objects.ObjectDef;

public class AgilityNode {	
	private int objectId;
	private Area area;

	public AgilityNode(int objectId, Area area) {
		this.objectId = objectId;
		this.area = area;
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
