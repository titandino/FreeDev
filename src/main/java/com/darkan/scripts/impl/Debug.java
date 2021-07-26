package com.darkan.scripts.impl;

import com.darkan.api.entity.MyPlayer;
import com.darkan.api.inter.Interfaces;
import com.darkan.api.inter.chat.Message;
import com.darkan.api.scripting.MessageListener;
import com.darkan.api.util.Logger;
import com.darkan.api.util.Paint;
import com.darkan.api.world.WorldTile;
import com.darkan.scripts.Script;
import com.darkan.scripts.ScriptSkeleton;

import kraken.plugin.api.Client;
import kraken.plugin.api.ImGui;
import kraken.plugin.api.Player;
import kraken.plugin.api.Vector2i;
import kraken.plugin.api.Vector3;

@Script(value = "Debug", utility = true)
public class Debug extends ScriptSkeleton implements MessageListener {
	
	public Debug() {
		super("Debug", 600);
	}

	Player myPlayer;
	int interfaceId = 0;
	
	@Override
	public boolean onStart() {
		return true;
	}

	@Override
	public void loop() {
		myPlayer = MyPlayer.get();
	}
	
	@Override
	public void onMessageReceived(Message message) {
		System.out.println(message);
		Vector3 localKraken = myPlayer.getScenePosition();
		WorldTile playerLoc = new WorldTile(myPlayer.getGlobalPosition());
		System.out.println(localKraken);
		System.out.println(playerLoc.getXInRegion() + ", " + playerLoc.getYInRegion());
		System.out.println(playerLoc.getXDraw() + ", " + playerLoc.getYDraw());
	}
	
	@Override
	public void paintOverlay(long runtime) {
		if (myPlayer != null) {
			Paint.drawTile(new WorldTile(myPlayer.getGlobalPosition()), 0xFF0000FF, false);
			Vector2i mm = Client.worldToMinimap(myPlayer.getScenePosition());
	        if (mm != null)
	            ImGui.freeText(myPlayer.getName(), mm, 0xff0000ff);
		}
	}
	
	@Override
	public void paintImGui(long runtime) {
		interfaceId = ImGui.intInput("Interface ID", interfaceId);
		if (ImGui.button("Dump")) {
			try {
				Logger.writeToFile("interfaceDump.txt", Interfaces.get(interfaceId).toString());
			} catch (Exception e) {
				System.out.println("Error dumping interface");
			}
		}
	}

	@Override
	public void onStop() {
		
	}
}
