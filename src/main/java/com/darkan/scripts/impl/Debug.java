package com.darkan.scripts.impl;

import java.awt.Color;

import com.darkan.api.inter.chat.Message;
import com.darkan.api.scripting.MessageListener;
import com.darkan.api.util.Paint;
import com.darkan.api.world.WorldTile;
import com.darkan.scripts.Script;
import com.darkan.scripts.ScriptSkeleton;

import kraken.plugin.api.Player;

//@Script(value = "Debug", utility = true)
public class Debug extends ScriptSkeleton implements MessageListener {
	
	public Debug() {
		super("Debug", 600);
	}

	Player myPlayer;
	
	@Override
	public boolean onStart(Player self) {
		return true;
	}

	@Override
	public void loop(Player self) {
		myPlayer = self;
	}
	
	@Override
	public void onMessageReceived(Message message) {
		System.out.println(message);
	}
	
	@Override
	public void paintOverlay(long runtime) {
		if (myPlayer != null)
			Paint.drawTile(new WorldTile(myPlayer.getGlobalPosition()), Color.GREEN, false);
	}
	
	@Override
	public void paintImGui(long runtime) {
		
	}

	@Override
	public void onStop() {
		
	}
}
