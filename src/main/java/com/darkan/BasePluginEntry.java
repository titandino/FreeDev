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
