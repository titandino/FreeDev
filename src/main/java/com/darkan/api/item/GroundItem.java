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
package com.darkan.api.item;

import com.darkan.api.util.Utils;
import com.darkan.api.world.Interactable;
import com.darkan.api.world.WorldTile;
import com.darkan.cache.def.items.ItemDef;

import kraken.plugin.api.Actions;

public class GroundItem implements Interactable {

	private int id;
	private WorldTile position;
	
	public GroundItem(kraken.plugin.api.GroundItem item) {
		this.id = item.getId();
		this.position = new WorldTile(item.getGlobalPosition());
	}
	
	public ItemDef getDef() {
		return ItemDef.get(id);
	}

	public int getId() {
		return id;
	}

	public WorldTile getPosition() {
		return position;
	}

	@Override
	public String name() {
		return getDef().name;
	}

	@Override
	public boolean hasOption(String option) {
		return getDef().containsGroundOp(option);
	}

	public void pickup() {
		interact(2);
	}

	@Override
	public boolean interact(int option) {
		if (option == 2) {
			Actions.menu(Actions.MENU_EXECUTE_GROUND_ITEM, getId(), getPosition().getX(), getPosition().getY(), Utils.random(0, Integer.MAX_VALUE));
			return true;
		}
		return false;
	}

	@Override
	public boolean interact(String action) {
		int op = getDef().getGroundOpIdForName(action);
		if (op != -1) {
			interact(op);
			return true;
		}
		return false;
	}
	
}
