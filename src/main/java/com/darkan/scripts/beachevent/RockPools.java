package com.darkan.scripts.beachevent;

import com.darkan.api.accessors.NPCs;
import com.darkan.api.accessors.WorldObjects;
import com.darkan.api.inter.Interfaces;
import com.darkan.scripts.ScriptSkeleton;

import kraken.plugin.api.Player;

public class RockPools extends BeachActivity {
    
	@Override
	public void loop(ScriptSkeleton ctx, Player self) {
	    if (!Interfaces.getInventory().isFull())
	        NPCs.interactClosestReachable("Lure");
	    else
	        WorldObjects.interactClosestReachable("Deposit fish");
	    ctx.sleepWhile(3000, 30000, () -> self.isAnimationPlaying() || self.isMoving());
	}
}
