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
package com.darkan.api.profile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.darkan.api.entity.MyPlayer;
import com.darkan.api.util.JsonFileManager;
import com.darkan.api.util.Logger;
import com.google.gson.JsonIOException;

import kraken.plugin.api.Player;

public final class PlayerProfiles {
	
	private static PlayerProfiles MAP;
	private static PlayerProfile DEFAULTS = new PlayerProfile();
	
	private Map<String, PlayerProfile> profiles = new HashMap<>();
	
	public static PlayerProfile get() {
		Player myPlayer = MyPlayer.get();
		if (myPlayer == null || myPlayer.getName() == null)
			return DEFAULTS;
		return getByName(myPlayer.getName());
	}
	
	public static PlayerProfile getByName(String name) {
		if (MAP == null)
			loadConfig();
		if (MAP.profiles.get(name) == null) {
			MAP.profiles.put(name, new PlayerProfile());
			save();
		} else {
			PlayerProfile p = MAP.profiles.get(name);
			try {
				for (Field f : p.getClass().getDeclaredFields()) {
				    if (f.get(p) == null)
						f.set(p, f.get(DEFAULTS));
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				Logger.handle(e);
				System.exit(5);
			}
			save();
		}
		return MAP.profiles.get(name);
	}
	
	public static void save() {
		try {
			File configFile = new File(System.getProperty("user.home")+"/.freedev/profiles.json");
			JsonFileManager.saveJsonFile(MAP, configFile);
		} catch (JsonIOException | IOException e) {
			Logger.handle(e);
			System.exit(5);
		}
	}

	public static void loadConfig() {
		try {
			File configFile = new File(System.getProperty("user.home")+"/.freedev/profiles.json");
			if (configFile.exists())
				MAP = JsonFileManager.loadJsonFile(new File(System.getProperty("user.home")+"/.freedev/profiles.json"), PlayerProfiles.class);
			else
				MAP = new PlayerProfiles();
			JsonFileManager.saveJsonFile(MAP, configFile);
		} catch (JsonIOException | IOException | IllegalArgumentException e) {
			Logger.handle(e);
			System.exit(5);
		}
	}
}
