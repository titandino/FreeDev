package com.darkan.cache;

import com.darkan.cache.def.structs.StructDef;

public class Main {

	public static void main(String[] args) {
		StructDef def = StructDef.get(103);
		System.out.println(def);
	}

}
