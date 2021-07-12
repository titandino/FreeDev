package com.darkan.scripts.beachevent;

import com.darkan.api.accessors.WorldObjects;
import com.darkan.api.inter.Interfaces;
import com.darkan.scripts.ScriptSkeleton;

import kraken.plugin.api.Player;

public class PalmTrees extends BeachActivity {
	
	@Override
	public void loop(ScriptSkeleton ctx, Player self) {
	    if (Interfaces.getInventory().isFull())
	        WorldObjects.interactClosestReachable("Deposit coconuts");
	    else
	        WorldObjects.interactClosestReachable("Pick coconut");
	    ctx.sleepWhile(3000, 30000, () -> self.isAnimationPlaying() || self.isMoving());
	}
}
