package com.darkan.api.item;

import com.darkan.api.inter.IFComponent;

public class Item {
	
	private IFComponent container;
	private int id;
	private int amount;
	private int slot;
	
	public Item(IFComponent container, int id, int amount, int slot) {
		this.container = container;
		this.id = id;
		this.amount = amount;
		this.slot = slot;
	}

	public int getId() {
		return id;
	}

	public int getAmount() {
		return amount;
	}
	
	public void click(int option) {
		container.clickComponent(option, slot);
	}
	
}
