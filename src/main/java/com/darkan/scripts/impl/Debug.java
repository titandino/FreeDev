package com.darkan.scripts.impl;

import com.darkan.api.scripting.MessageListener;
import com.darkan.scripts.ScriptSkeleton;

import kraken.plugin.api.Player;

//@Script(value = "Debug", utility = true)
public class Debug extends ScriptSkeleton implements MessageListener {
	
	public Debug() {
		super("Debug", 600);
	}

	@Override
	public boolean onStart(Player self) {
		return true;
	}

	@Override
	public void loop(Player self) {
		
	}
	
	@Override
	public void onMessageReceived(String message) {
		System.out.println(message);
	}
	
	@Override
	public void paintOverlay(long runtime) {
		
	}
	
	@Override
	public void paintImGui(long runtime) {
		
	}

	@Override
	public void onStop() {
		
	}
}
