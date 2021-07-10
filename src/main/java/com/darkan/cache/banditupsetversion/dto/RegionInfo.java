package com.darkan.cache.banditupsetversion.dto;

import java.util.Map;

public class RegionInfo {
	public int id;
	public Map<Short, Integer> masks;
	private transient int[][][] maskArr;
	
	public int[][][] getMasks() {
		if (maskArr == null) {
			maskArr = new int[4][64][64];
			for (int plane = 0;plane < 4;plane++) {
				for (int x = 0;x < 64;x++) {
					for (int y = 0;y < 64;y++) {
						short key = (short) ((plane << 12) + (y << 6) + x);
						maskArr[plane][x][y] = masks.get(key) == null ? 0 : masks.get(key);
					}
				}
			}
		}
		return maskArr;
	}
}
