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
package com.darkan.scripts.impl.aiorunespan;

import java.util.List;

import com.darkan.api.accessors.NPCs;
import com.darkan.api.accessors.WorldObjects;
import com.darkan.api.entity.MyPlayer;
import com.darkan.api.entity.NPC;
import com.darkan.api.inter.Interfaces;
import com.darkan.api.world.Interactable;
import com.darkan.api.world.WorldObject;
import com.darkan.scripts.LoopScript;
import com.darkan.scripts.Script;

import kraken.plugin.api.Client;
import kraken.plugin.api.ImGui;
import kraken.plugin.api.Time;

@Script("AIO Runespan")
public class AIORunespan extends LoopScript {
	public AIORunespan() {
		super("AIO Runespan");
	}

	private int startXp;
	private boolean members = true;

	@Override
	public boolean onStart() {
		startXp = Client.getStatById(Client.RUNECRAFTING).getXp();
		return true;
	}

	@Override
	protected void loop() {
		if (MyPlayer.get().isAnimationPlaying() || MyPlayer.get().isMoving())
			return;
		if (!Interfaces.getInventory().contains(Rune.ESSENCE, 1) && NPCs.interactClosestReachable("Collect", n -> n.getId() == 15402)) {
			setState("Ran out of essence... Grabbing more...");
			sleepWhile(2500, 20000, () -> !Interfaces.getInventory().contains(Rune.ESSENCE, 1));
			return;
		} 
		if (Interfaces.getInventory().contains(Rune.ESSENCE, 1)) {
			setState("Searching for best node to siphon...");
			Interactable node = getBestNearbyNode();
			if (node == null) {
				sleep(2000);
				return;
			}
			
			setState("Clicking closest " + node.name() + "...");
			node.interact("Siphon");
			sleepWhile(2500, 20000, () -> MyPlayer.get().isAnimationPlaying() || MyPlayer.get().isMoving());
		}
	}

	private Interactable getBestNearbyNode() {
		List<NodeInfo> sorted = NodeInfo.bestNodesForLevel(members, Client.getStatById(Client.RUNECRAFTING).getCurrent());
		List<NPC> closestNpcs = NPCs.getOrderedClosest();
		List<WorldObject> closestObjects = WorldObjects.getOrderedClosest();

		for (NodeInfo node : sorted) {
			if (node.isNPC()) {
				for (NPC npc : closestNpcs)
					if (npc.getId() == node.getId())
						return npc;
			} else {
				for (WorldObject object : closestObjects)
					if (object.getId() == node.getId())
						return object;
			}
		}
		return null;
	}

	@Override
	public void paintImGui(long runtime) {
		members = ImGui.checkbox("Members", members);
		ImGui.label("XP p/h: " + Time.perHour(runtime, Client.getStatById(Client.RUNECRAFTING).getXp() - startXp));
	}

	@Override
	public void paintOverlay(long runtime) {
		
	}

	@Override
	public void onStop() {
		
	}
}
