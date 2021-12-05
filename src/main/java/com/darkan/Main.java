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
package com.darkan;

import com.darkan.cache.def.items.ItemDef;
import com.darkan.cache.def.structs.StructDef;

public class Main {
	
	public static void main(String[] args) {
		for (int i = 0;i < ItemDef.getParser().getMaxId();i++) {
			ItemDef def = ItemDef.get(i);
			if (def == null)
				continue;
			//System.out.println(ItemDef.get(i).name + " -> " + ItemDef.get(i).getMaterials());
			if (def.name.contains("arrows"))
				System.out.println(def);
		}
		System.out.println(StructDef.get(1317));
	}

}
