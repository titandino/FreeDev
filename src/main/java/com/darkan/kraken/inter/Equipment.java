package com.darkan.kraken.inter;

public class Equipment {
	
	public static final IFComponent EQUIPMENT_ITEMS = new IFComponent(1464, 15); 
	public static final byte HEAD = 0, CAPE = 1, NECK = 2, WEAPON = 3, CHEST = 4, OFFHAND = 5, LEGS = 7, HANDS = 9, FEET = 10, RING = 12, AMMO = 13, AURA = 14, POCKET = 15;

	public static void clickSlot(int option, int slot) {
		EQUIPMENT_ITEMS.click(option, slot);
	}
	
}
