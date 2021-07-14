package com.darkan.scripts.impl.beachevent;

import com.darkan.api.accessors.NPCs;
import com.darkan.api.accessors.WorldObjects;
import com.darkan.api.inter.Interfaces;
import com.darkan.scripts.ScriptSkeleton;

import kraken.plugin.api.Player;

public class RockPools extends BeachActivity {
    
	@Override
	public void loop(ScriptSkeleton ctx, Player self) {
	    if (!Interfaces.getInventory().isFull()) {
	        if (!self.isAnimationPlaying() && !self.isMoving()) {
	            NPCs.interactClosestReachable("Lure");
	            ctx.setState("Fishing at the rock pools!");
	        }
	    } else {
	        WorldObjects.interactClosestReachable("Deposit fish");
	        ctx.setState("Depositing inventory of fish.");
	    }
	    ctx.sleepWhile(3000, Integer.MAX_VALUE, () -> self.isAnimationPlaying() || self.isMoving());
	}
}
