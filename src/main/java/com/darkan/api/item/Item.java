package com.darkan.api.item;

import com.darkan.api.inter.IFComponent;
import com.darkan.api.inter.Interfaces;
import com.darkan.cache.def.items.ItemDef;

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
	
	public Item(int id) {
		this(id, 1);
	}
	
	public Item(int id, int amount) {
		this.id = id;
		this.amount = amount;
	}

	public int getId() {
		return id;
	}

	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public void click(int option) {
		container.clickComponent(option, slot);
	}
	
	public void click(String option) {
		int op = -1;
		if (container == Interfaces.getEquipment()) {
			op = getDef().getEquipOpIdForName(option);
			op += 2;
		}
		if (container == Interfaces.getInventory()) {
			op = getDef().getInvOpIdForName(option);
			op = switch(op) {
				case 0 -> 1;
				case 1 -> 2;
				case 2 -> 3;
				case 3 -> 7;
				case 4 -> 8;
				default -> -1;
			};
		}
		if (op != -1)
			click(op);
	}	
	
	public ItemDef getDef() {
		return ItemDef.get(id);
	}
	
	public String getName() {
		return getDef().name;
	}
	
	@Override
	public String toString() {
		return "[" + id + "("+getName()+"), " + amount + "]";
	}
}
