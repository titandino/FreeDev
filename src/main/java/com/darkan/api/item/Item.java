package com.darkan.api.item;

import com.darkan.api.inter.IFComponent;
import com.darkan.api.inter.Interfaces;
import com.darkan.cache.def.items.ItemDef;

import kraken.plugin.api.Debug;

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
		if (container == Interfaces.getEquipment()) {
			op = getDef().getEquipOpIdForName(option);
			op += 2;
		}
		if (container == Interfaces.getInventory()) {
			op = getDef().getInvOpIdForName(option);
			Debug.log("invent option: " + op);
			op = switch(op) {
				case 0 -> 1;
				case 1 -> 2;
				case 2 -> 3;
				case 3 -> 7;
				case 4 -> 8;
				default -> -1;
			};
		}
        if (container == Interfaces.getBankInventory()) {
            op = switch(option) {
                case "Deposit-1" -> 2;
                case "Deposit-5" -> 3;
                case "Deposit-10" -> 4;
                case "Deposit-50" -> 5;
                case "Deposit-X" -> 6;
                case "Deposit-All" -> 7;
                default -> 1;
            };
        }
		if (op != -1)
			click(op);
	}	
	
	public ItemDef getDef() {
		return ItemDef.get(id);
	}
}
