package com.darkan.scripts.hom;

import com.darkan.api.accessors.NPCs;
import com.darkan.api.accessors.WorldObjects;
import com.darkan.api.entity.NPC;
import com.darkan.api.inter.Interfaces;
import com.darkan.scripts.Script;
import com.darkan.scripts.ScriptSkeleton;

import kraken.plugin.api.Client;
import kraken.plugin.api.ImGui;
import kraken.plugin.api.Player;
import kraken.plugin.api.Time;

@Script("Hall of Memories")
public class HallOfMemories extends ScriptSkeleton {

	private static final int MEMORY_JAR_EMPTY = 42898;
	private static final int MEMORY_JAR_PARTIAL = 42899;
	private static final int MEMORY_JAR_FULL = 42900;
	private static final int CORE_MEMORY_FRAGMENT_ITEM = 42901;

	private int startXp;
	private NPC memory;
	private boolean twoTick = true;
	private boolean useCoreFragments = true;

	public HallOfMemories() {
		super("Hall Of Memories", 600);
	}

	@Override
	public boolean onStart(Player self) {
		setState("Starting...");
		startXp = Client.getStatById(Client.DIVINATION).getXp();
		start();
		return true;
	}

	@Override
	public void loop(Player self) {
		if (self.isMoving())
			return;
		
		if ((!Interfaces.getInventory().isFull() || Interfaces.getInventory().contains(CORE_MEMORY_FRAGMENT_ITEM, 1)) 
				&& NPCs.interactClosestReachable("Capture", npc -> npc.getName().equals("Core memory fragment"))) {
			setState("Getting core memory fragment.");
			sleepWhile(3000, 12000, () -> self.isAnimationPlaying() || self.isMoving());
			return;
		}
		if (NPCs.interactClosestReachable("Capture", npc -> npc.getName().equals("Knowledge fragment"))) {
			setState("Getting knowledge fragments.");
			sleepWhile(700, 12000, () -> self.isMoving());
			return;
		}
		if (Interfaces.getInventory().freeSlots() > 5 && WorldObjects.interactClosest("Take-from", object -> object.getName().equals("Jar depot"))) {
			setState("Gathering empty jars.");
			sleepWhile(2400, 25000, () -> Interfaces.getInventory().freeSlots() > 5 || self.isMoving());
			return;
		}
    	if (useCoreFragments && Interfaces.getInventory().contains(CORE_MEMORY_FRAGMENT_ITEM, 1) 
    	        && WorldObjects.interactClosest("Interact", object -> object.getId() == 111376)) {
            setState("Spawning memory fragment");
            sleepWhile(2000, 25000, () -> self.isAnimationPlaying() || self.isMoving());
            return;
        }
		if ((Interfaces.getInventory().contains(MEMORY_JAR_EMPTY, 1) || Interfaces.getInventory().contains(MEMORY_JAR_PARTIAL, 1))) {
			memory = HOMConfig.getNPCForLevel(Client.getStatById(Client.DIVINATION).getCurrent());
			if (memory != null) {
				setState("2 ticking memory...");
				memory.interact("Harvest");
				if (twoTick)
					sleep(600);
				else
					sleepWhile(3000, 40000, () -> self.isAnimationPlaying() || self.isMoving());
				return;
			}
			setState("Filling memory jars.");
		} else if (Interfaces.getInventory().contains(MEMORY_JAR_FULL, 1) && !self.isAnimationPlaying() && WorldObjects.interactClosest("Offer-memory")) {
			setState("Depositing memory jars.");
			sleepWhile(5000, 75000, () -> self.isAnimationPlaying() || self.isMoving());
		}
	}

	@Override
	public void paintImGui(long runtime) {
		twoTick = ImGui.checkbox("2 Tick", twoTick);
		useCoreFragments = ImGui.checkbox("Use core memory fragments", useCoreFragments);
		ImGui.label("XP p/h: " + Time.perHour(runtime, Client.getStatById(Client.DIVINATION).getXp() - startXp));
	}

	@Override
	public void paintOverlay(long runtime) {

	}

	@Override
	public void onStop() {

	}
}
