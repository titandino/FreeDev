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

import java.util.HashSet;
import java.util.Set;

import kraken.plugin.api.Widgets;

public class Interfaces {
	
	private static final ItemContainer INVENTORY = new ItemContainer(1473, 7);
	private static final ItemContainer EQUIPMENT = new ItemContainer(1464, 15);
	
	private static final ItemContainer BANK = new ItemContainer(517, 188);
	private static final ItemContainer BANK_INVENTORY = new ItemContainer(517, 15);
	private static final ItemContainer BANK_EQUIPMENT = new ItemContainer(517, 28);
	
	private static final ItemContainer DEPOSIT_BOX = new ItemContainer(11, 19);
	
	private static final ItemContainer AREA_LOOT = new ItemContainer(1622, 10);
		
	private static final Set<Integer> VISIBLE_INTERFACES = new HashSet<>();
	
	public static void setVisibility(int id, boolean visible) {
		if (visible)
			VISIBLE_INTERFACES.add(id);
		else
			VISIBLE_INTERFACES.remove(id);
	}
	
	public static boolean visible(int id) {
		return VISIBLE_INTERFACES.contains(id);
	}
	
	public static boolean isOpen(IFComponent component) {
		try {
			return Widgets.getGroupById(component.getInterfaceId()) != null && Widgets.getGroupById(component.getInterfaceId()).getWidgets()[component.getComponentId()] != null;
		} catch(Exception e) {
			return false;
		}
	}
	
	public static boolean click(int option, int interfaceId, int componentId, int slotId) {
		return new IFSlot(interfaceId, componentId, slotId).click(slotId);
	}
	
	public static boolean click(int option, int interfaceId, int componentId) {
		return new IFComponent(interfaceId, componentId).click(option, -1);
	}
	
	public static boolean dialContinue(int interfaceId, int componentId, int slotId) {
		return new IFComponent(interfaceId, componentId).dialContinue(slotId);
	}
	
	public static boolean dialContinue(int interfaceId, int componentId) {
		return new IFComponent(interfaceId, componentId).dialContinue();
	}

	public static ItemContainer getInventory() {
		return INVENTORY;
	}
	
	public static ItemContainer getEquipment() {
		return EQUIPMENT;
	}
	
   public static ItemContainer getBank() {
        return BANK;
    }
   
    public static ItemContainer getBankInventory() {
        return BANK_INVENTORY;
    }
    
    public static ItemContainer getBankEquipment() {
        return BANK_EQUIPMENT;
    }
    
    public static ItemContainer getAreaLoot() {
        return AREA_LOOT;
    }

	public static Interface get(int interfaceId) {
		return new Interface(interfaceId);
	}

	public static IFComponent getComponent(int interfaceId, int componentId) {
		try {
			return new Interface(interfaceId).getComponents()[componentId];
		} catch(Exception e) {
			return null;
		}
	}

	public static ItemContainer getDepositBox() {
		return DEPOSIT_BOX;
	}
}
