package com.darkan.scripts;

import com.darkan.kraken.util.Util;

import kraken.plugin.api.Client;
import kraken.plugin.api.Debug;
import kraken.plugin.api.ImGui;
import kraken.plugin.api.Player;
import kraken.plugin.api.Players;
import kraken.plugin.api.Time;

public abstract class ScriptSkeleton {
	
	private String name;
	private long started = 0;
	private String state = "Initializing...";
	private String error = "";
	private boolean enabled = false;
	private int gausVariance = 4000;
	
	private long loopTimer;
	private long lastLoop;
	
	private int loopDelay;
	private int currDelay;
	
	private int localPlayerAtt = 0;
	
	public ScriptSkeleton(String name) {
		this(name, 600);
	}
	
	public ScriptSkeleton(String name, int loopDelay) {
		this.name = name;
		this.loopDelay = loopDelay;
	}
	
	public abstract boolean onStart(Player self);
	protected abstract void loop(Player self);
	public abstract void paintImGui(long runtime);
	public abstract void paintOverlay(long runtime);
	public abstract void onStop();

	protected int onLoop() {
		try {
			Player self = Players.self();
			if(self == null) {
				state = "Finding local player... " + localPlayerAtt++;
				return 0;
			}
			localPlayerAtt = 0;
			if (!enabled) {
				if (Client.getStatById(Client.HITPOINTS).getXp() <= 100)
					return 0;
				boolean started = onStart(self);
				if (started) {
					start();
					enabled = true;
				}
				return 0;
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
		paintImGui(runtime);
	}
	
	public void onPaintOverlay() {
		long runtime = System.currentTimeMillis() - started;
		paintOverlay(runtime);
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

	public void stop() {
		onStop();
	}

	public final void process() {
		long currTime = System.currentTimeMillis();
		loopTimer -= (currTime - lastLoop);
		lastLoop = currTime;
		if (loopTimer <= 0)
			loopTimer = onLoop();
	}

	public void onVarChange() {
		
	}
}