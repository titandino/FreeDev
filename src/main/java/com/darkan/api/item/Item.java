package com.darkan.api.item;

import com.darkan.api.inter.IFComponent;
import com.darkan.api.inter.Interfaces;
import com.darkan.cache.Cache;
import com.darkan.cache.dto.ItemInfo;

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
	
	public void click(String option) {
		int op = -1;
		if (container == Interfaces.getEquipment())
			op = getDef().getEquipOpIdForName(option);
		if (container == Interfaces.getInventory())
			op = getDef().getInvOpIdForName(option);
		if (op != -1)
			click(op);
	}	
	
	public ItemInfo getDef() {
		return Cache.getItem(id);
	}
}
