package com.darkan.kraken;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import kraken.plugin.api.Inventory;
import kraken.plugin.api.WidgetItem;

public class Util {
	
	private static final Random RANDOM = new SecureRandom();
	
	public static int gaussian(int mean, int variance) {
		return (int) (RANDOM.nextGaussian() * Math.sqrt(variance) + mean);
	}
	
	public static int invCount(int... ids) {
		Set<Integer> itemIds = new HashSet<>(Arrays.asList(Arrays.stream(ids).boxed().toArray(Integer[]::new)));
		int count = 0;
		for (WidgetItem item : Inventory.getItems()) {
			if (item == null || !itemIds.contains(item.getId()))
				continue;
			count += item.getAmount();
		}
		return count;
	}
}
