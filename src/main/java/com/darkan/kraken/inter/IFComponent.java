package com.darkan.kraken.inter;

import kraken.plugin.api.Actions;

public class IFComponent {
	
	private int id;
	private int componentId;
	
	public IFComponent(int id, int componentId) {
		this.id = id;
		this.componentId = componentId;
	}

	public void click(int option, int slotId) {
		Actions.menu(Actions.MENU_EXECUTE_WIDGET, option, slotId, getHash(), 1);
	}
	
	public int getHash() {
		return id << 16 | componentId;
	}
}
