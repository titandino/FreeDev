package com.darkan.api.entity;

import kraken.plugin.api.Client;
import kraken.plugin.api.Player;

public class MyPlayer {
	
	private static VarManager vars = new VarManager();
	private static Player myPlayer;

	public static double getHealthPerc() {
		return ((double) Client.getCurrentHealth() / (double) Client.getMaxHealth()) * 100.0;
	}

	public static VarManager getVars() {
		return vars;
	}

	public static Player get() {
		return myPlayer;
	}

	public static void set(Player self) {
		myPlayer = self;
	}
	
}
