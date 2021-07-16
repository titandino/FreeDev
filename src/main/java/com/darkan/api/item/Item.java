package com.darkan.api.item;

import com.darkan.api.inter.IFSlot;
import com.darkan.api.inter.Interfaces;
import com.darkan.cache.def.items.ItemDef;

import kraken.plugin.api.Debug;

public class Item {
	
	private IFSlot slot;
	private int id;
	private int amount;
	
	public Item(IFSlot slot, int id, int amount) {
		this.slot = slot;
		this.id = id;
		this.amount = amount;
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
		slot.click(option);
	}
	
	public void click(String option) {
		int op = -1;
		if (slot.getId() == Interfaces.getEquipment().getId()) {
			op = getDef().getEquipOpIdForName(option);
			op += 2;
		}
		if (slot.getId() == Interfaces.getInventory().getId()) {
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
        if (slot.getId() == Interfaces.getBankInventory().getId()) {
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
	
	public String getName() {
		return getDef().name;
	}
	
	@Override
	public String toString() {
		return "[" + id + "("+getName()+"), " + amount + "]";
	}
}
