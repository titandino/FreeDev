package com.darkan.kraken.entity;

import kraken.plugin.api.Client;

public class MyPlayer {

	public static double getHealthPerc() {
		return ((double) Client.getCurrentHealth() / (double) Client.getMaxHealth()) * 100.0;
	}
	
}
