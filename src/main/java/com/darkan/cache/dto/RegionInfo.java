package com.darkan.cache.dto;

import java.util.List;
import java.util.Map;

public class RegionInfo {
	public int id;
	public Map<Integer, List<Short>> masks;
	private transient int[][][] maskArr;
	
	public int[][][] getMasks() {
		if (masks == null)
			return null;
		if (maskArr == null) {
			maskArr = new int[4][64][64];
			for (int mask : masks.keySet()) {
				List<Short> tileHashes = masks.get(mask);
				for (short tile : tileHashes)
					maskArr[tile >> 12][tile & 0x3F][tile >> 6 & 0x3F] = mask;
			}
		}
		return maskArr;
	}
}
