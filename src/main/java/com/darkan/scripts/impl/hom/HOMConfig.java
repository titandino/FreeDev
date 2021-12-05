// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
//
//  Copyright © 2021 Trenton Kress
//  This file is part of project: FreeDev
//
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
