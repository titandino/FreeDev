package com.darkan.scripts.impl.hom;

import com.darkan.api.accessors.NPCs;
import com.darkan.api.entity.NPC;

public enum HOMConfig {

	AAGI(70),
	SEREN(70), //80 unlocks max fill %
	JUNA(70), //85 unlocks max fill %
	SWORD_OF_EDICTS(70), //90 unlocks max fill %
	CRES(70), //95 unlocks max fill %
	LUSTROUS_MEMORIES(70),
	BRILLIANT_MEMORIES(80),
	RADIANT_MEMORIES(85),
	LUMINOUS_MEMORIES(90),
	INCANDESCENT_MEMORIES(95);
	
	private int levelRequirement;
	
	private HOMConfig(int levelRequirement) {
		this.levelRequirement = levelRequirement;
	}

	public int getLevelRequirement() {
		return levelRequirement;
	}
	
   public static NPC getNPCForLevel(int level) {
        NPC n = null;
        for (HOMConfig c : HOMConfig.values()) {
            if (level < c.getLevelRequirement())
                break;
            n = NPCs.getClosestReachable(npc -> npc.getName().toUpperCase().equals(c.name().replaceAll("_", " ")));

            if (n != null && !n.getName().toUpperCase().contains("MEMORIES"))
                return n;
        }
        return n;
    }
}
