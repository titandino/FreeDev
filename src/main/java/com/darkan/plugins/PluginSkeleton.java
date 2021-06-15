package com.darkan.plugins;

import com.darkan.kraken.util.Util;

import kraken.plugin.AbstractPlugin;
import kraken.plugin.api.Client;
import kraken.plugin.api.Debug;
import kraken.plugin.api.ImGui;
import kraken.plugin.api.Player;
import kraken.plugin.api.Players;
import kraken.plugin.api.PluginContext;
import kraken.plugin.api.Time;

public abstract class PluginSkeleton extends AbstractPlugin {
	
	private String name;
	private long started = 0;
	private String state = "Initializing...";
	private String error = "";
	private boolean enabled = false;
	private int gausVariance = 4000;
	
	private int loopDelay;
	private int currDelay;
	
	public PluginSkeleton(String name) {
		this(name, 600);
	}
	
	public PluginSkeleton(String name, int loopDelay) {
		this.name = name;
		this.loopDelay = loopDelay;
	}
	
	public abstract boolean onStart(Player self);
	public abstract void loop(Player self);
	public abstract void paint(long runtime);
	
	public boolean onLoaded(PluginContext context) {
		try {
			context.setName(name);
			return true;
		} catch(Exception e) {
			Debug.logErr(e);
			return false;
		}
	}

	public int onLoop() {
		try {
			Player self = Players.self();
			if(self == null) {
				state = "Finding local player...";
				return 10;
			}
			if (!enabled) {
				if (Client.getStatById(Client.HITPOINTS).getXp() <= 100)
					return 10;
				boolean started = onStart(self);
				if (started) {
					start();
					enabled = true;
				}
				return 10;
			}
			
			currDelay = loopDelay;
			loop(self);
			return Util.gaussian(currDelay, gausVariance);
		} catch(Exception e) {
			error = e.toString();
			Debug.logErr(e);
			return Util.gaussian(2000, gausVariance);
		}
	}

	public void onPaint() {
		long runtime = System.currentTimeMillis() - started;
		ImGui.label(name + " - " + Time.formatTime(runtime));
		ImGui.label(state);
		ImGui.label(error);
		paint(runtime);
	}
	
	public final void setState(String state) {
		this.state = state;
	}
	
	public final void sleep(int ms) {
		currDelay += Util.gaussian(ms, gausVariance);
	}
	
	public final void start() {
		started = System.currentTimeMillis();
	}
}
