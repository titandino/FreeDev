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
package com.darkan.scripts.impl.hom;

import com.darkan.api.accessors.NPCs;
import com.darkan.api.accessors.WorldObjects;
import com.darkan.api.entity.MyPlayer;
import com.darkan.api.entity.NPC;
import com.darkan.api.inter.Interfaces;
import com.darkan.api.inter.chat.Message;
import com.darkan.api.listeners.MessageListener;
import com.darkan.api.util.Timer;
import com.darkan.scripts.LoopScript;
import com.darkan.scripts.Script;

import kraken.plugin.api.Client;
import kraken.plugin.api.ImGui;
import kraken.plugin.api.Time;

@Script("Hall of Memories")
public class HallOfMemories extends LoopScript implements MessageListener {

	private static final int MEMORY_JAR_EMPTY = 42898;
	private static final int MEMORY_JAR_PARTIAL = 42899;
	private static final int MEMORY_JAR_FULL = 42900;
	private static final int CORE_MEMORY_FRAGMENT_ITEM = 42901;

	private int startXp;
	private NPC memory;
	private boolean twoTick = true;
	private boolean useCoreFragments = false;
	private boolean collectKnowledgeFrags = true;
	private boolean collectMemoryFrags = true;
	private Timer corePopTimer = new Timer();

	public HallOfMemories() {
		super("Hall Of Memories", 600);
	}

	@Override
	public boolean onStart() {
		setState("Starting...");
		startXp = Client.getStatById(Client.DIVINATION).getXp();
		start();
		return true;
	}

	@Override
	public void loop() {
		if (MyPlayer.get().isMoving())
			return;
		
		if ((!Interfaces.getInventory().isFull() || Interfaces.getInventory().contains(CORE_MEMORY_FRAGMENT_ITEM, 1)) 
				&& collectMemoryFrags && NPCs.interactClosestReachable("Capture", npc -> npc.getName().equals("Core memory fragment"))) {
			setState("Getting core memory fragment.");
			sleepWhile(3000, 12000, () -> MyPlayer.get().isAnimationPlaying() || MyPlayer.get().isMoving());
			return;
		}
		if (collectKnowledgeFrags && NPCs.interactClosestReachable("Capture", npc -> npc.getName().equals("Knowledge fragment"))) {
			setState("Getting knowledge fragments.");
			sleepWhile(700, 12000, () -> MyPlayer.get().isMoving());
			return;
		}
		if (!Interfaces.getInventory().containsAny(MEMORY_JAR_EMPTY, MEMORY_JAR_PARTIAL, MEMORY_JAR_FULL) && WorldObjects.interactClosest("Take-from", object -> object.getName().equals("Jar depot"))) {
			setState("Gathering empty jars.");
			sleepWhile(2400, 35000, () -> Interfaces.getInventory().freeSlots() > 5 || MyPlayer.get().isMoving());
			return;
		} else if (Interfaces.getInventory().contains(MEMORY_JAR_EMPTY, 1) || Interfaces.getInventory().contains(MEMORY_JAR_PARTIAL, 1)) {
			memory = HOMConfig.getNPCForLevel(Client.getStatById(Client.DIVINATION).getCurrent());
			if (memory != null) {	    
			    if (corePopTimer.time() > 10000 && memory.getName().toUpperCase().contains("MEMORIES") && useCoreFragments && Interfaces.getInventory().contains(CORE_MEMORY_FRAGMENT_ITEM, 1) 
			                && WorldObjects.interactClosest("Interact", object -> object.getId() == 111376)) {
			            setState("Spawning memory fragment");
			            sleepWhile(1000, 25000, () -> MyPlayer.get().isAnimationPlaying() || MyPlayer.get().isMoving());
			            return;
			    }
				setState("2 ticking memory...");
				memory.interact("Harvest");
				if (twoTick)
					sleep(600);
				else
					sleepWhile(3000, 40000, () -> MyPlayer.get().isAnimationPlaying() || MyPlayer.get().isMoving());
				return;
			}
			setState("Filling memory jars.");
		} else if (Interfaces.getInventory().contains(MEMORY_JAR_FULL, 1) && !MyPlayer.get().isAnimationPlaying() && WorldObjects.interactClosest("Offer-memory")) {
			setState("Depositing memory jars.");
			sleepWhile(5000, 75000, () -> Interfaces.getInventory().contains(MEMORY_JAR_FULL, 1));
		}
	}

	@Override
	public void paintImGui(long runtime) {
		twoTick = ImGui.checkbox("2 Tick", twoTick);
		useCoreFragments = ImGui.checkbox("Use core memory fragments", useCoreFragments);
		collectMemoryFrags = ImGui.checkbox("Collect core memory fragments", collectMemoryFrags);
		collectKnowledgeFrags = ImGui.checkbox("Collect knowledge fragments", collectKnowledgeFrags);
		ImGui.label("XP p/h: " + Time.perHour(runtime, Client.getStatById(Client.DIVINATION).getXp() - startXp));
	}

	@Override
	public void paintOverlay(long runtime) {

	}

	@Override
	public void onStop() {

	}

	@Override
	public void onMessageReceived(Message message) {
		if (message.isGame() && message.getText().contains("Core memory playback initiated, please stand by..."))
			corePopTimer.click();
	}
}
