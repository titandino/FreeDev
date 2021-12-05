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

import com.darkan.api.item.Item;
import com.darkan.api.util.Utils;

import kraken.plugin.api.Actions;
import kraken.plugin.api.Widgets;

public class IFSlot {

	private int interfaceId;
	private int componentId;
	private int slotId;

	public IFSlot(int id, int componentId, int slotId) {
		this.interfaceId = id;
		this.componentId = componentId;
		this.slotId = slotId;
	}

	public boolean click(int option) {
		if (isOpen()) {
			Actions.menu(Actions.MENU_EXECUTE_WIDGET, option, slotId, getHash(), Utils.random(0, Integer.MAX_VALUE));
			return true;
		}
		return false;
	}

	public int getHash() {
		return interfaceId << 16 | componentId;
	}
	
	public boolean isOpen() {
		try {
			return Widgets.getGroupById(interfaceId).getWidgets()[componentId].getChildren()[slotId] != null;
		} catch (Exception e) {
			return false;
		}
	}
	
	public String getText() {
		try {
			return Widgets.getGroupById(interfaceId).getWidgets()[componentId].getChildren()[slotId].getText();
		} catch (Exception e) {
			return null;
		}
	}
	
	public ComponentType getType() {
		try {
			return ComponentType.forId(Widgets.getGroupById(interfaceId).getWidgets()[componentId].getChildren()[slotId].getType());
		} catch (Exception e) {
			return null;
		}
	}
	
	public Item getItem() {
		try {
			kraken.plugin.api.Item item = Widgets.getGroupById(interfaceId).getWidgets()[componentId].getChildren()[slotId].getItem();
			return new Item(this, item.getId(), item.getAmount());
		} catch (Exception e) {
			return null;
		}
	}
	
	public IFComponent getParent() {
		return new IFComponent(interfaceId, componentId);
	}

	public int getInterfaceId() {
		return interfaceId;
	}

	public int getComponentId() {
		return componentId;
	}
	
	public int getSlotId() {
		return slotId;
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("\t\tIFSlot: (" + interfaceId + ", " + componentId + ", " + slotId + ", " + getType() + ")\r\n");
		String text = getText();
		if (text != null)
			s.append("\t\tText: \"" + text + "\"\r\n");
		Item item = getItem();
		if (item != null)
			s.append("\t\tItem: \"" + item + "\"\r\n");
		return s.toString();
	}

	public boolean isChild(IFComponent component) {
		return this.interfaceId == component.interfaceId && this.componentId == component.componentId;
	}
}

