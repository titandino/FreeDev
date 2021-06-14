package com.darkan.kraken.item;

public class Item {
	
	private int id;
	private int amount;
	
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
	
}
