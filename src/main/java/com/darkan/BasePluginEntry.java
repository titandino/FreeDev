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

import static kraken.plugin.api.Debug.printStackTrace;

import kraken.plugin.api.Plugin;
import kraken.plugin.api.PluginContext;

public class BasePluginEntry {
	private static final Plugin plugin = new BasePlugin();

	public boolean onLoaded(PluginContext pluginContext) {
		try {
			return plugin.onLoaded(pluginContext);
		} catch (Throwable t) {
			printStackTrace("onLoaded", t);
			return false;
		}
	}

	public int onLoop() {
		try {
			return plugin.onLoop();
		} catch (Throwable t) {
			printStackTrace("onLoop", t);
			return 60000;
		}
	}

	public void onPaint() {
		try {
			plugin.onPaint();
		} catch (Throwable t) {
			printStackTrace("onPaint", t);
		}
	}

	public void onPaintOverlay() {
		try {
			plugin.onPaintOverlay();
		} catch (Throwable t) {
			printStackTrace("onPaintOverlay", t);
		}
	}
}
