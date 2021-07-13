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
			return Widgets.getGroupById(component.getId()) != null && Widgets.getGroupById(component.getId()).getWidgets()[component.getComponentId()] != null;
		} catch(Exception e) {
			return false;
		}
	}

	public static ItemContainer getInventory() {
		return INVENTORY;
	}
	
	public static ItemContainer getEquipment() {
		return EQUIPMENT;
	}

	public static Interface get(int interfaceId) {
		return new Interface(interfaceId);
	}

	public static IFComponent getComponent(int interfaceId, int componentId) {
		return new IFComponent(interfaceId, componentId);
	}
}
