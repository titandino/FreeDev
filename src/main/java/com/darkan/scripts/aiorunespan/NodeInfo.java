package com.darkan.scripts.aiorunespan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum NodeInfo {
	CYCLONE(false, false, 70455, 1, Rune.AIR),
	MIND_STORM(false, false, 70456, 1, Rune.MIND),
	WATER_POOL(false, false, 70457, 5, Rune.WATER),
	ROCK_FRAGMENT(false, false, 70458, 9, Rune.EARTH),
	FIRE_BALL(false, false, 70459, 14, Rune.FIRE),
	VINE(false, false, 70460, 17, Rune.WATER, Rune.EARTH),
	FLESHLY_GROWTH(false, false, 70461, 20, Rune.BODY),
	FIRE_STORM(false, false, 70462, 27, Rune.AIR, Rune.FIRE),
	CHAOTIC_CLOUD(true, false, 70463, 35, Rune.CHAOS),
	NEBULA(true, false, 70464, 40, Rune.COSMIC, Rune.ASTRAL),
	SHIFTER(true, false, 70465, 44, Rune.NATURE),
	JUMPER(true, false, 70466, 54, Rune.LAW),
	SKULLS(true, false, 70467, 65, Rune.DEATH),
	BLOOD_POOL(true, false, 70468, 77, Rune.BLOOD),
	BLOODY_SKULLS(true, false, 70469, 83, Rune.DEATH, Rune.BLOOD),
	LIVING_SOUL(true, false, 70470, 90, Rune.SOUL),
	UNDEAD_SOUL(true, false, 70471, 95, Rune.DEATH, Rune.SOUL),
	
	AIR_ESSLING(false, true, 15403, Rune.AIR, 1),
	MIND_ESSLING(false, true, 15404, Rune.MIND, 1),
	WATER_ESSLING(false, true, 15405, Rune.WATER, 5),
	EARTH_ESSLING(false, true, 15406, Rune.EARTH, 9),
	FIRE_ESSLING(false, true, 15407, Rune.FIRE, 14),
	BODY_ESSHOUND(false, true, 15408, Rune.BODY, 20),
	COSMIC_ESSHOUND(true, true, 15409, Rune.COSMIC, 27),
	CHOAS_ESSHOUND(true, true, 15410, Rune.CHAOS, 35),
	ASTRAL_ESSHOUND(true, true, 15411, Rune.ASTRAL, 40),
	NATURE_ESSHOUND(true, true, 15412, Rune.NATURE, 44),
	LAW_ESSHOUND(true, true, 15413, Rune.LAW, 54),
	DEATH_ESSWRAITH(true, true, 15414, Rune.DEATH, 65),
	BLOOD_ESSWRAITH(true, true, 15415, Rune.BLOOD, 77),
	SOUL_ESSWRAITH(true, true, 15416, Rune.SOUL, 90);
	
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
		list.sort((c1, c2) -> c2.levelRequired - c1.levelRequired);
		return list;
	}

	private boolean members;
	private boolean npc;
	private int id;
	private int levelRequired;
	private int[] runeId;

	NodeInfo(boolean members, boolean npc, int id, int levelRequired, int... runeId) {
		this.members = members;
		this.npc = npc;
		this.id = id;
		this.levelRequired = levelRequired;
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
