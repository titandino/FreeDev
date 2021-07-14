package com.darkan.scripts.impl.aiorunespan;

public enum Platform {
	P_EARTH(70478, Rune.EARTH), 
	P_EARTH2(70479, Rune.EARTH), 
	P_EARTH3(70485, Rune.EARTH), 
	P_EARTH4(70495, Rune.EARTH), 
	P_ICE(70480, Rune.AIR, Rune.WATER), 
	P_ICE2(70496, Rune.AIR, Rune.WATER), 
	P_VINE(70490, Rune.WATER, Rune.EARTH, Rune.NATURE), 
	P_VINE2(70500, Rune.WATER, Rune.EARTH, Rune.NATURE), 
	P_SMALL_MISSILE(70481, Rune.AIR, Rune.MIND), 
	P_SMALL_MISSILE2(70482, Rune.AIR, Rune.MIND), 
	P_SMALL_MISSILE3(70487, Rune.AIR, Rune.MIND), 
	P_SMALL_MISSILE4(70497, Rune.AIR, Rune.MIND), 
	P_GREATER_MISSILE(70504, Rune.AIR, Rune.BLOOD, Rune.DEATH), 
	P_GREATER_MISSILE2(70505, Rune.AIR, Rune.BLOOD, Rune.DEATH), 
	P_MISSILE(70489, Rune.AIR, Rune.CHAOS), 
	P_MISSILE2(70499, Rune.AIR, Rune.CHAOS), 
	P_MIST1(70491, Rune.BODY, Rune.WATER, Rune.NATURE), 
	P_MIST2(70492, Rune.BODY, Rune.WATER, Rune.NATURE), 
	P_MIST3(70501, Rune.BODY, Rune.WATER, Rune.NATURE), 
	P_COSMIC(70493, Rune.COSMIC, Rune.ASTRAL, Rune.LAW), 
	P_COSMIC2(70502, Rune.COSMIC, Rune.ASTRAL, Rune.LAW), 
	P_CONJURATION(70488, Rune.MIND, Rune.BODY, Rune.ESSENCE), 
	P_FLESH(70506, Rune.BODY, Rune.DEATH, Rune.BLOOD), 
	P_SKELETAL(70503, Rune.DEATH), 
	P_FLOAT(70476, Rune.AIR), 
	P_FLOAT2(70477, Rune.AIR), 
	P_FLOAT3(70483, Rune.AIR), 
	P_FLOAT4(70494, Rune.AIR);

	private int objectId;
	private int[] runes;

	private Platform(int objectId, int... runes) {
		this.objectId = objectId;
		this.runes = runes;
	}

	public int[] getRunes() {
		return runes;
	}

	public int getObjectId() {
		return objectId;
	}
}
