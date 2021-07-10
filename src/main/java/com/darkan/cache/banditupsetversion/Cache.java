package com.darkan.cache.banditupsetversion;

import com.darkan.cache.banditupsetversion.dto.CacheFile;
import com.darkan.cache.banditupsetversion.dto.ItemInfo;
import com.darkan.cache.banditupsetversion.dto.NPCInfo;
import com.darkan.cache.banditupsetversion.dto.ObjectInfo;
import com.darkan.cache.banditupsetversion.dto.RegionInfo;

/**
 * Remote API for pulling information from Trent's cache library.
 * When I am allowed back into the community, I will open source
 * the actual source of this data so other people can keep it updated
 * instead of me.
 * @author trent
 */
public class Cache {
	
	private static CacheFile cache;

	public static NPCInfo getNPC(int id) {
		return cache.NPCS.get(id);
	}
	
	public static ItemInfo getItem(int id) {
		return cache.ITEMS.get(id);
	}
	
	public static ObjectInfo getObject(int id) {
		return cache.OBJECTS.get(id);
	}
	
	public static RegionInfo getRegion(int id) {
		return cache.REGIONS.get(id);
	}

	public static void loadCache() {
		
	}
}
