package com.darkan.scripts.impl.beachevent;

import com.darkan.api.accessors.NPCs;
import com.darkan.api.accessors.WorldObjects;
import com.darkan.api.entity.MyPlayer;
import com.darkan.api.inter.Interfaces;
import com.darkan.scripts.LoopScript;

public class RockPools extends BeachActivity {
    
	@Override
	public void loop(LoopScript ctx) {
	    if (!Interfaces.getInventory().isFull()) {
	        if (!MyPlayer.get().isAnimationPlaying() && !MyPlayer.get().isMoving()) {
	            NPCs.interactClosestReachable("Lure");
	            ctx.setState("Fishing at the rock pools!");
	        }
	    } else {
	        WorldObjects.interactClosestReachable("Deposit fish");
	        ctx.setState("Depositing inventory of fish.");
	    }
	    ctx.sleepWhile(3000, Integer.MAX_VALUE, () -> MyPlayer.get().isAnimationPlaying() || MyPlayer.get().isMoving());
	}
}
