package com.darkan.cache;

import com.darkan.cache.def.enums.EnumDef;

public enum ResourceType {
	ENUM("enum", EnumDef.class);
	
	private String identifier;
	private Class<?> clazz;
	
	private ResourceType(String identifier, Class<?> clazz) {
		this.identifier = identifier;
		this.clazz = clazz;
	}

	public static int getArchive(int id, int size) {
		return id >> size;
	}

	public static int getFile(int id, int size) {
		return id & (1 << size) - 1;
	}
}
