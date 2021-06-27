package com.darkan.kraken.inter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.darkan.kraken.item.Item;

import kraken.plugin.api.Widget;

public class ItemContainer extends IFComponent {

	public ItemContainer(int id, int componentId) {
		super(id, componentId);
	}
	
	public Item[] getItems() {
		Widget[] invSlots = getChildren();
		if (invSlots == null)
			return new Item[28];
		Item[] items = new Item[invSlots.length];
		for (int i = 0;i < items.length;i++) {
			kraken.plugin.api.Item item = invSlots[i].getItem();
			if (item == null || item.getId() < 0)
				continue;
			items[i] = new Item(this, item.getId(), item.getAmount(), i);
		}
		return items;
	}
	
	public Item getItemById(int... ids) {
		Set<Integer> itemIds = new HashSet<>(Arrays.asList(Arrays.stream(ids).boxed().toArray(Integer[]::new)));
		for (Item item : getItems()) {
			if (item == null || !itemIds.contains(item.getId()))
				continue;
			return item;
		}
		return null;
	}
	
	public boolean contains(int itemId, int amount) {
		int amt = 0;
		for (Item item : getItems())
			if (item != null && item.getId() == itemId)
				amt += item.getAmount();
		return amt >= amount;
	}
	
	public boolean containsAny(int... ids) {
		Set<Integer> itemIds = new HashSet<>(Arrays.asList(Arrays.stream(ids).boxed().toArray(Integer[]::new)));
		for (Item item : getItems())
			if (item != null && itemIds.contains(item.getId()))
				return true;
		return false;
	}
	
	public int count(int... ids) {
		Set<Integer> itemIds = new HashSet<>(Arrays.asList(Arrays.stream(ids).boxed().toArray(Integer[]::new)));
		int count = 0;
		for (Item item : getItems()) {
			if (item == null || !itemIds.contains(item.getId()))
				continue;
			count += item.getAmount();
		}
		return count;
	}

	public boolean isFull() {
		for (Item item : getItems())
			if (item == null)
				return false;
		return true;
	}

	public void clickSlot(int option, int slot) {
		clickComponent(option, slot);
	}
	
	public boolean clickItem(int itemId, int option) {
		Item item = getItemById(itemId);
		if (item != null) {
			item.click(option);
			return true;
		}
		return false;
	}
}
