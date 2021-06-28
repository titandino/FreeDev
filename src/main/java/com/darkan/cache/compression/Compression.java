package com.darkan.cache.compression;

import java.util.HashMap;
import java.util.Map;

public enum Compression {
	NONE,
	BZIP2,
	GZIP,
	LZMA;
	
	private static Map<Integer, Compression> MAP = new HashMap<>();
	
	static {
		for (Compression c : Compression.values())
			MAP.put(c.ordinal(), c);
	}
	
	public static Compression forId(int type) {
		return MAP.get(type);
	}
}
