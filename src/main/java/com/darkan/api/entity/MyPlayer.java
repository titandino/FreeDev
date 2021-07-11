package com.darkan.api.entity;

import kraken.plugin.api.Client;

public class MyPlayer {
	
	private static VarManager vars = new VarManager();

	public static double getHealthPerc() {
		return ((double) Client.getCurrentHealth() / (double) Client.getMaxHealth()) * 100.0;
	}

	public static VarManager getVars() {
		return vars;
	}
	
}
