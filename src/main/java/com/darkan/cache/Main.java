package com.darkan.cache;

import com.darkan.cache.def.enums.EnumDef;
import com.darkan.cache.def.enums.EnumDefParser;

public class Main {

	public static void main(String[] args) {
		Cache cache = new Cache();
		EnumDef def = EnumDefParser.get(cache, 108);
		System.out.println(def);
	}
	
}
