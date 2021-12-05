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
package com.darkan;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import com.darkan.api.util.JsonFileManager;
import com.google.gson.JsonIOException;

public final class Settings {
	
	private static Settings SETTINGS;
	private static Settings DEFAULTS = new Settings();
	
	public static Settings getConfig() {
		if (SETTINGS == null)
			loadConfig();
		return SETTINGS;
	}

	private String cachePath;
	private boolean debug;
	
	public Settings() {
		this.cachePath = "C:/ProgramData/Jagex/RuneScape/";
		this.debug = false;
	}	

	public static void loadConfig() {
		try {
			File configFile = new File(System.getProperty("user.home")+"/.freedev/config.json");
			if (configFile.exists())
				SETTINGS = JsonFileManager.loadJsonFile(new File(System.getProperty("user.home")+"/.freedev/config.json"), Settings.class);
			else
				SETTINGS = new Settings();
			for (Field f : SETTINGS.getClass().getDeclaredFields()) {
			    if (f.get(SETTINGS) == null)
			    	f.set(SETTINGS, f.get(DEFAULTS));
			}
			JsonFileManager.saveJsonFile(SETTINGS, configFile);
		} catch (JsonIOException | IOException | IllegalArgumentException | IllegalAccessException e1) {
			e1.printStackTrace();
			System.exit(5);
		}
	}

	public boolean isDebug() {
		return debug;
	}

	public String getCachePath() {
		return cachePath != null ? cachePath : DEFAULTS.cachePath;
	}
}
