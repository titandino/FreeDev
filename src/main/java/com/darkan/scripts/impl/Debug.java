package com.darkan.scripts.impl;

import com.darkan.api.accessors.NPCs;
import com.darkan.api.accessors.WorldObjects;
import com.darkan.api.entity.MyPlayer;
import com.darkan.api.entity.NPC;
import com.darkan.api.inter.Interfaces;
import com.darkan.api.inter.chat.Message;
import com.darkan.api.listeners.MessageListener;
import com.darkan.api.util.Logger;
import com.darkan.api.util.Paint;
import com.darkan.api.world.WorldTile;
import com.darkan.scripts.LoopScript;
import com.darkan.scripts.Script;

import kraken.plugin.api.*;

@Script(value = "Debug", utility = true, debugOnly = true)
public class Debug extends LoopScript implements MessageListener {
	
	public Debug() {
		super("Debug", 600);
	}

	Player myPlayer;
	int interfaceId = 0;
	private int npcId;
	private NPC npc;
	
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
		//Interfaces.getInventory().getItemReg("torch").useOn(WorldObjects.getClosest(obj -> obj.getName().contains("Tree")));
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
		ImGui.label("Player Animation: "+ Players.self().getAnimationId());
		npcId = ImGui.intInput("NPC ID", npcId);
		if(npcId != 0) {
			if(npc == null || npc.getId() != npcId) {
				npc = NPCs.getClosest(n -> n.getId() == npcId);
			}
			if(npc != null)
				ImGui.label("NPC Animation: "+npc.getAnimationId());
		}
	}

	@Override
	public void onStop() {
		
	}
}
