package com.darkan.scripts.impl.aiorunespan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum NodeInfo {
	CYCLONE(false, false, 70455, 1, 19.0, Rune.AIR),
	MIND_STORM(false, false, 70456, 1, 20.0, Rune.MIND),
	WATER_POOL(false, false, 70457, 5, 25.3, Rune.WATER),
	ROCK_FRAGMENT(false, false, 70458, 9, 28.6, Rune.EARTH),
	FIRE_BALL(false, false, 70459, 14, 34.8, Rune.FIRE),
	VINE(false, false, 70460, 17, 32.3, Rune.WATER, Rune.EARTH),
	FLESHLY_GROWTH(false, false, 70461, 20, 46.2, Rune.BODY),
	FIRE_STORM(false, false, 70462, 27, 32.25, Rune.AIR, Rune.FIRE),
	CHAOTIC_CLOUD(true, false, 70463, 35, 61.6, Rune.CHAOS),
	NEBULA(true, false, 70464, 40, 74.7, Rune.COSMIC, Rune.ASTRAL),
	SHIFTER(true, false, 70465, 44, 86.8, Rune.NATURE),
	JUMPER(true, false, 70466, 54, 107.8, Rune.LAW),
	SKULLS(true, false, 70467, 65, 120.0, Rune.DEATH),
	BLOOD_POOL(true, false, 70468, 77, 146.3, Rune.BLOOD),
	BLOODY_SKULLS(true, false, 70469, 83, 159.75, Rune.DEATH, Rune.BLOOD),
	LIVING_SOUL(true, false, 70470, 90, 213, Rune.SOUL),
	UNDEAD_SOUL(true, false, 70471, 95, 199.8, Rune.DEATH, Rune.SOUL),
	
	AIR_ESSLING(false, true, 15403, 1, 9.5, Rune.AIR),
	MIND_ESSLING(false, true, 15404, 1, 10.0, Rune.MIND),
	WATER_ESSLING(false, true, 15405, 5, 12.6, Rune.WATER),
	EARTH_ESSLING(false, true, 15406, 9, 14.3, Rune.EARTH),
	FIRE_ESSLING(false, true, 15407, 14, 17.4, Rune.FIRE),
	BODY_ESSHOUND(false, true, 15408, 20, 23.1, Rune.BODY),
	COSMIC_ESSHOUND(true, true, 15409, 27, 26.6, Rune.COSMIC),
	CHOAS_ESSHOUND(true, true, 15410, 35, 30.8, Rune.CHAOS),
	ASTRAL_ESSHOUND(true, true, 15411, 40, 35.7, Rune.ASTRAL),
	NATURE_ESSHOUND(true, true, 15412, 44, 43.4, Rune.NATURE),
	LAW_ESSHOUND(true, true, 15413, 54, 53.9, Rune.LAW),
	DEATH_ESSWRAITH(true, true, 15414, 65, 60.0, Rune.DEATH),
	BLOOD_ESSWRAITH(true, true, 15415, 77, 73.1, Rune.BLOOD),
	SOUL_ESSWRAITH(true, true, 15416, 90, 106.5, Rune.SOUL);
	
	private static Map<Integer, NodeInfo> MAP = new HashMap<>();
	
	static {
		for (NodeInfo n : NodeInfo.values())
			MAP.put(n.id, n);
	}
	
	public static NodeInfo forId(int objectId) {
		return MAP.get(objectId);
	}
	
	public static List<NodeInfo> bestNodesForLevel(boolean members, int level) {
		List<NodeInfo> list = new ArrayList<>();
		for (NodeInfo n : NodeInfo.values()) {
			if (!members && n.members)
				continue;
			if (n.levelRequired <= level)
				list.add(n);
		}
		list.sort((c1, c2) -> Double.compare(c2.avgXpDrop, c1.avgXpDrop));
		return list;
	}

	private boolean members;
	private boolean npc;
	private int id;
	private int levelRequired;
	private double avgXpDrop;
	private int[] runeId;

	NodeInfo(boolean members, boolean npc, int id, int levelRequired, double avgXpDrop, int... runeId) {
		this.members = members;
		this.npc = npc;
		this.id = id;
		this.levelRequired = levelRequired;
		this.avgXpDrop = avgXpDrop;
		this.runeId = runeId;
	}
	
	public boolean isNPC() {
		return npc;
	}

	public int getId() {
		return id;
	}

	public void setObjectId(int objectId) {
		this.id = objectId;
	}

	public int[] getRuneId() {
		return runeId;
	}

	public int getLevelRequired() {
		return levelRequired;
	}

	public void setLevelRequired(int levelRequired) {
		this.levelRequired = levelRequired;
	}
}
