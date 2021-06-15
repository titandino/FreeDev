package com.darkan.kraken.inter;

import kraken.plugin.api.Actions;

public class Interface {

	public static void click(int option, int interfaceId, int childId, int slotId) {
		Actions.menu(Actions.MENU_EXECUTE_WIDGET, option, slotId, interfaceId << 16 | childId, 1);
	}
	
}
