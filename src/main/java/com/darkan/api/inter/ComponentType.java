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
package com.darkan.api.inter;

import java.util.HashMap;
import java.util.Map;

public enum ComponentType {
	CONTAINER(0),
	TYPE_1(1),
	TYPE_2(2),
	FIGURE(3), 
	TEXT(4), 
	SPRITE(5), 
	MODEL(6),
	TYPE_7(7),
	TYPE_8(8),
	LINE(9),
	TYPE_10(10),
	TYPE_11(11),
	TYPE_12(12),
	TYPE_13(13),
	TYPE_14(14),
	TYPE_15(15),
	TYPE_16(16);
	
	private static Map<Integer, ComponentType> MAP = new HashMap<>();

	static {
		for (ComponentType t : ComponentType.values())
			MAP.put(t.id, t);
	}
	
	public static ComponentType forId(int id) {
		return MAP.get(id);
	}

	private int id;

	private ComponentType(int id) {
		this.id = id;
	}
}
