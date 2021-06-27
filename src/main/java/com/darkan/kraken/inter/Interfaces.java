package com.darkan.kraken.inter;

import java.util.HashSet;
import java.util.Set;

public class Interfaces {
	
	private static final ItemContainer INVENTORY = new ItemContainer(1473, 7);
	private static final ItemContainer EQUIPMENT = new ItemContainer(1464, 15);
	
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

	public static ItemContainer getInventory() {
		return INVENTORY;
	}
	
	public static ItemContainer getEquipment() {
		return EQUIPMENT;
	}
}
