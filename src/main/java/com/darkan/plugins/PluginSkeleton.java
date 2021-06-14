package com.darkan.plugins;

import kraken.plugin.AbstractPlugin;
import kraken.plugin.api.ImGui;
import kraken.plugin.api.Player;
import kraken.plugin.api.Players;
import kraken.plugin.api.PluginContext;
import kraken.plugin.api.Time;

import com.darkan.kraken.Util;

public abstract class PluginSkeleton extends AbstractPlugin {
	
	private String name;
	private long started;
	private String state = "Initializing...";
	private String error = "";
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
	
	public boolean onLoad(PluginContext context) {
		return true;
	}
	
	public abstract void loop(Player self);
	public abstract void paint(long runtime);
	
	public boolean onLoaded(PluginContext context) {
		try {
			context.setName(name);
			if (onLoad(context)) {
				start();
				return true;
			}
			return false;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public int onLoop() {
		try {
			Player self = Players.self();
			if(self == null) {
				state = "Finding local player...";
				return 600;
			}
			
			currDelay = loopDelay;
			loop(self);
			return Util.gaussian(currDelay, gausVariance);
		} catch(Exception e) {
			error = e.toString();
			e.printStackTrace();
			System.out.println(e.getMessage());
			return Util.gaussian(2000, gausVariance);
		}
	}

	public void onPaint() {
		long runtime = System.currentTimeMillis() - started;
		ImGui.label(name + " - " + Time.formatTime(System.currentTimeMillis() - started));
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
