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
package com.darkan.api.entity;

import com.darkan.api.world.WorldTile;

import kraken.plugin.api.Player;

public class MyPlayer {
	
	private static VarManager vars = new VarManager();
	private static Player myPlayer;
	private static int maxHealth;
	private static int health;

	public static double getHealthPerc() {
		return ((double) getHealth() / (double) getMaxHealth()) * 100.0;
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

	public static WorldTile getPosition() {
		return new WorldTile(myPlayer.getGlobalPosition());
	}

	public static int getMaxHealth() {
		return maxHealth;
	}

	public static void setMaxHealth(int maxHealth) {
		MyPlayer.maxHealth = maxHealth;
	}

	public static int getHealth() {
		return health;
	}

	public static void setHealth(int health) {
		MyPlayer.health = health;
	}
	
}
