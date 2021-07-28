package com.darkan;

import static kraken.plugin.api.Debug.printStackTrace;

import kraken.plugin.api.PluginContext;
import kraken.stub.AbstractPlugin;

public class BasePluginEntry {
	private static final AbstractPlugin plugin = new BasePlugin();

	public static boolean onLoaded(PluginContext pluginContext) {
		try {
			return plugin.onLoaded(pluginContext);
		} catch (Throwable t) {
			printStackTrace("onLoaded", t);
			return false;
		}
	}

	public static int onLoop() {
		try {
			return plugin.onLoop();
		} catch (Throwable t) {
			printStackTrace("onLoop", t);
			return 60000;
		}
	}

	public static void onPaint() {
		try {
			plugin.onPaint();
		} catch (Throwable t) {
			printStackTrace("onPaint", t);
		}
	}

	public static void onPaintOverlay() {
		try {
			plugin.onPaintOverlay();
		} catch (Throwable t) {
			printStackTrace("onPaintOverlay", t);
		}
	}
}
