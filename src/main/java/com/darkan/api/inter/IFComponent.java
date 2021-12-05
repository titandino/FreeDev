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
import kraken.plugin.api.Widget;
import kraken.plugin.api.WidgetGroup;
import kraken.plugin.api.Widgets;

public class IFComponent {

	protected int interfaceId;
	protected int componentId;

	public IFComponent(int hash) {
		this.interfaceId = hash >> 16;
		this.componentId = (hash & 0xFF);
	}

	public IFComponent(int id, int componentId) {
		this.interfaceId = id;
		this.componentId = componentId;
	}

	public boolean click(int option, int slotId) {
		if (isOpen()) {
			Actions.menu(Actions.MENU_EXECUTE_WIDGET, option, slotId, getHash(), Utils.random(0, Integer.MAX_VALUE));
			return true;
		}
		return false;
	}
	
	public boolean click(int option) {
		return click(option, -1);
	}
	
	public boolean dialContinue(int slotId) {
		if (isOpen()) {
			Actions.menu(Actions.MENU_EXECUTE_DIALOGUE, 0, slotId, getHash(), Utils.random(0, Integer.MAX_VALUE));
			return true;
		}
		return false;
	}
	
	public boolean dialContinue() {
		return dialContinue(-1);
	}

	public int getHash() {
		return interfaceId << 16 | componentId;
	}
	
	public boolean isOpen() {
		try {
			return Widgets.getGroupById(interfaceId).getWidgets()[componentId] != null;
		} catch (Exception e) {
			return false;
		}
	}

	public IFSlot[] getSlots() {
		if (getType() != ComponentType.CONTAINER)
			return null;
		try {
			IFSlot[] slots = new IFSlot[Widgets.getGroupById(interfaceId).getWidgets()[componentId].getChildren().length];
			for (int i = 0;i < slots.length;i++)
				slots[i] = new IFSlot(interfaceId, componentId, i);
			return slots;
		} catch (Exception e) {
			return null;
		}
	}

	public String getText() {
		if (getType() != ComponentType.TEXT)
			return null;
		try {
			return Widgets.getGroupById(interfaceId).getWidgets()[componentId].getText();
		} catch (Exception e) {
			return null;
		}
	}
	
	public ComponentType getType() {
		try {
			return ComponentType.forId(Widgets.getGroupById(interfaceId).getWidgets()[componentId].getType());
		} catch (Exception e) {
			return null;
		}
	}
	
	public Item[] getItems() {
		try {
			IFSlot[] slots = getSlots();
			Item[] items = new Item[slots.length];
			for (int i = 0;i < slots.length;i++)
				items[i] = slots[i].getItem();
			return items;
		} catch (Exception e) {
			return null;
		}
	}

	public Widget getWidget() {
		WidgetGroup group = Widgets.getGroupById(interfaceId);
		if(group == null || group.getWidgets() == null || group.getWidgets().length < componentId+1) return null;
		return group.getWidgets()[componentId];
	}

	public int getInterfaceId() {
		return interfaceId;
	}

	public int getComponentId() {
		return componentId;
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("\tIFComponent: (" + interfaceId + ", " + componentId + ", " + getType() + ")\r\n");
		IFSlot[] slots = getSlots();
		String text = getText();
		if (text != null)
			s.append("\tText: \"" + text + "\"\r\n");
		if (slots != null && slots.length > 0) {
			s.append("\tSlots: \r\n");
			for (IFSlot slot : slots) {
				try {
					s.append(slot.toString());
				} catch (Exception e) {
					
				}
			}
		}
		return s.toString();
	}
}
