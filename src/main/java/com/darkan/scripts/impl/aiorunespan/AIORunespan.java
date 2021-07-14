package com.darkan.scripts.impl.aiorunespan;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.darkan.api.accessors.NPCs;
import com.darkan.api.accessors.WorldObjects;
import com.darkan.api.entity.NPC;
import com.darkan.api.inter.Interfaces;
import com.darkan.api.world.Interactable;
import com.darkan.scripts.Script;
import com.darkan.scripts.ScriptSkeleton;

import kraken.plugin.api.Client;
import kraken.plugin.api.ImGui;
import kraken.plugin.api.Player;
import kraken.plugin.api.Time;

@Script("AIO Runespan")
public class AIORunespan extends ScriptSkeleton {
	public AIORunespan() {
		super("AIO Runespan");
	}

	private int startXp;
	private boolean members = true;

	@Override
	public boolean onStart(Player self) {
		startXp = Client.getStatById(Client.RUNECRAFTING).getXp();
		return true;
	}

	@Override
	protected void loop(Player self) {
		if (self.isAnimationPlaying() || self.isMoving())
			return;
		if (!Interfaces.getInventory().contains(Rune.ESSENCE, 1)) {
			setState("Ran out of essence... Looking for nearby essence swarm...");
			NPC essence = NPCs.getClosestReachable(n -> n.getId() == 15402);
			if (essence != null) {
				setState("Ran out of essence... Grabbing more...");
				essence.interact("Collect");
			}
			sleep(3500);
		} else {
			setState("Searching for best node to siphon...");
			NodeInfo bestNodeInfo = getBestNearbyNode();
			if (bestNodeInfo == null) {
				sleep(2000);
				return;
			}			
			Interactable node;
			if (bestNodeInfo.isNPC())
				node = NPCs.getClosestReachable(n -> n.getId() == bestNodeInfo.getId());
			else
				node = WorldObjects.getClosestReachable(o -> o.getId() == bestNodeInfo.getId());

			if (node != null) {
				setState("Clicking closest " + bestNodeInfo.name().toLowerCase() + "...");
				node.interact("Siphon");
				sleep(5500);
			}
		}
	}

	private NodeInfo getBestNearbyNode() {
		List<NodeInfo> sorted = NodeInfo.bestNodesForLevel(members, Client.getStatById(Client.RUNECRAFTING).getCurrent());
		Set<Integer> reachableNpcs = NPCs.getNearbyReachable().stream().map(n -> n.getId()).collect(Collectors.toSet());
		Set<Integer> reachableObjects = WorldObjects.getNearbyReachable().stream().map(o -> o.getId()).collect(Collectors.toSet());
		
		for (NodeInfo n : sorted) {
			if (n.isNPC() && reachableNpcs.contains(n.getId()))
				return n;
			else if (!n.isNPC() && reachableObjects.contains(n.getId()))
				return n;
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
