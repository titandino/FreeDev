package com.darkan.kraken.inter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.darkan.kraken.item.Item;

import kraken.plugin.api.Widget;
import kraken.plugin.api.WidgetGroup;
import kraken.plugin.api.Widgets;

public class Inventory {
		
	public static Item[] getItems() {
		WidgetGroup base = Widgets.getGroupById(1473);
		if (base == null)
			return new Item[28];
		Widget[] children = Widgets.getGroupById(1473).getWidgets();
		if (children == null || children.length < 7)
			return new Item[28];
		Widget[] invSlots = Widgets.getGroupById(1473).getWidgets()[7].getChildren();
		if (invSlots == null)
			return new Item[28];
		Item[] items = new Item[invSlots.length];
		for (int i = 0;i < items.length;i++) {
			kraken.plugin.api.Item item = invSlots[i].getItem();
			if (item == null || item.getId() < 0)
				continue;
			items[i] = new Item(item.getId(), item.getAmount());
		}
		return items;
	}
	
	public static boolean contains(int itemId, int amount) {
		int amt = 0;
		for (Item item : getItems())
			if (item != null && item.getId() == itemId)
				amt += item.getAmount();
		return amt >= amount;
	}
	
	public static boolean containsAny(int... ids) {
		Set<Integer> itemIds = new HashSet<>(Arrays.asList(Arrays.stream(ids).boxed().toArray(Integer[]::new)));
		for (Item item : getItems())
			if (item != null && itemIds.contains(item.getId()))
				return true;
		return false;
	}
	
	public static int count(int... ids) {
		Set<Integer> itemIds = new HashSet<>(Arrays.asList(Arrays.stream(ids).boxed().toArray(Integer[]::new)));
		int count = 0;
		for (Item item : getItems()) {
			if (item == null || !itemIds.contains(item.getId()))
				continue;
			count += item.getAmount();
		}
		return count;
	}

	public static boolean isFull() {
		for (Item item : getItems())
			if (item == null)
				return false;
		return true;
	}
}
