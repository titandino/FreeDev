// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
//
//  Copyright © 2021 Trenton Kress
//  This file is part of project: FreeDev
//
package com.darkan.api.inter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import com.darkan.api.item.Item;

import kraken.plugin.api.Filter;

public class ItemContainer extends IFComponent {

	public ItemContainer(int id, int componentId) {
		super(id, componentId);
	}
	
	public Item[] getItems() {
		try {
			IFSlot[] slots = getSlots();
			Item[] items = new Item[slots.length];
			for (int i = 0;i < slots.length;i++)
				items[i] = slots[i].getItem();
			return items;
		} catch(Exception e) {
			return new Item[1];
		}
	}

	public Item getItemBySlot(int slot) {
		Item[] items = getItems();
		if(slot < 0 || slot >= items.length) return null;
		return items[slot];
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
	

	public Item getItemByName(String... exactNames) {
		Set<String> names = new HashSet<>(Arrays.asList(Arrays.stream(exactNames).toArray(String[]::new)));
		for (Item item : getItems()) {
			if (item == null || !names.contains(item.getDef().name))
				continue;
			return item;
		}
		return null;
	}
	
	public Item getItemReg(String regex) {
		for (Item item : getItems()) {
			if (item != null && Pattern.compile(regex).matcher(item.getDef().name).find())
				return item;
		}
		return null;
	}

	public boolean contains(int itemId) {
		return contains(itemId, 1);
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

	public boolean containsAny(String... exactNames) {
		Set<String> names = new HashSet<>(Arrays.asList(Arrays.stream(exactNames).toArray(String[]::new)));
		for (Item item : getItems())
			if (item != null && names.contains(item.getDef().name))
				return true;
		return false;
	}
	
	public int count(String... exactNames) {
		Set<String> names = new HashSet<>(Arrays.asList(Arrays.stream(exactNames).toArray(String[]::new)));
		int count = 0;
		for (Item item : getItems()) {
			if (item == null || !names.contains(item.getDef().name))
				continue;
			count += item.getAmount();
		}
		return count;
	}
	
	public boolean containsAnyReg(String regex) {
		for (Item item : getItems()) {
			if (item != null && Pattern.compile(regex).matcher(item.getDef().name).find())
				return true;
		}
		return false;
	}
	
	public int countReg(String regex) {
		int count = 0;
		for (Item item : getItems()) {
			if (item == null || !Pattern.compile(regex).matcher(item.getDef().name).find())
				continue;
			count += item.getAmount();
		}
		return count;
	}
	
	public boolean isFull() {
		for (Item item : getItems())
			if (item == null || item.getId() == -1)
				return false;
		return true;
	}

	public void clickSlot(int option, int slot) {
		click(option, slot);
	}
	
	public boolean clickItem(int itemId, int option) {
		Item item = getItemById(itemId);
		if (item != null) {
			item.click(option);
			return true;
		}
		return false;
	}
	
	public boolean clickItem(int itemId, String option) {
		Item item = getItemById(itemId);
		if (item != null) {
			item.click(option);
			return true;
		}
		return false;
	}

	public boolean clickItem(String name, String option) {
		Item item = getItemByName(name);
		if (item != null) {
			item.click(option);
			return true;
		}
		return false;
	}
	
    public boolean clickItemReg(String regex, String option) {
        for (Item item : getItems()) {
            if (item != null && Pattern.compile(regex).matcher(item.getDef().name).find()) {
                item.click(option);
                return true;
            }
        }
        return false;
     }

	public int freeSlots() {
		int free = 0;
		for (Item item : getItems())
			if (item == null || item.getId() == -1)
				free++;
		return free;
	}

	public Item getItem(Filter<Item> filter) {
		for (Item item : getItems())
			if (item != null && item.getId() != -1 && filter.accept(item))
				return item;
		return null;
	}
	
	public List<Item> getItems(Filter<Item> filter) {
		List<Item> items = new ArrayList<Item>();
		for (Item item : getItems())
			if (item != null && item.getId() != -1 && filter.accept(item))
				items.add(item);
		return items;
	}
}
