package com.darkan.api.inter;

import kraken.plugin.api.Actions;
import kraken.plugin.api.Widget;
import kraken.plugin.api.Widgets;

public class IFComponent {

	private int id;
	private int componentId;

	public IFComponent(int id, int componentId) {
		this.id = id;
		this.componentId = componentId;
	}

	public void clickComponent(int option, int slotId) {
		Actions.menu(Actions.MENU_EXECUTE_WIDGET, option, slotId, getHash(), 1);
	}

	public int getHash() {
		return id << 16 | componentId;
	}

	public Widget[] getChildren() {
		return Widgets.getGroupById(id).getWidgets()[componentId].getChildren();
	}

	public String getText() {
		try {
			return Widgets.getGroupById(id).getWidgets()[componentId].getText();
		} catch (Exception e) {
			return null;
		}
	}

	public int getId() {
		return id;
	}

	public int getComponentId() {
		return componentId;
	}
}
