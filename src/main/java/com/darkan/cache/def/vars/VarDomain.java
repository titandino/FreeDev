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
package com.darkan.cache.def.vars;

import java.util.HashMap;
import java.util.Map;

public enum VarDomain {
	PLAYER(0, 60), 
	NPC(1, 61), 
	CLIENT(2, 62), 
	WORLD(3, 63), 
	REGION(4, 64), 
	OBJECT(5, 65), 
	CLAN(6, 66), 
	CLAN_SETTING(7, 67),
	UNK(8, 68);
	
	private static Map<Integer, VarDomain> MAP = new HashMap<>();
	
	static {
		for (VarDomain d : VarDomain.values())
			MAP.put(d.id, d);
	}
	
	public static VarDomain forId(int id) {
		return MAP.get(id);
	}

	private int id;
	private int configArchive;

	private VarDomain(int id, int configArchive) {
		this.id = id;
		this.configArchive = configArchive;
	}

	public int getId() {
		return id;
	}

	public int getConfigArchive() {
		return configArchive;
	}
}
