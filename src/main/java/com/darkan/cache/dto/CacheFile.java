package com.darkan.cache.dto;

import java.util.HashMap;
import java.util.Map;

public class CacheFile {
	public Map<Integer, NPCInfo> NPCS = new HashMap<>();
	public Map<Integer, ItemInfo> ITEMS = new HashMap<>();
	public Map<Integer, ObjectInfo> OBJECTS = new HashMap<>();
	public Map<Integer, RegionInfo> REGIONS = new HashMap<>();
}
