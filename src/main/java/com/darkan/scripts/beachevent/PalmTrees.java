package com.darkan.scripts.beachevent;

import java.util.HashMap;
import java.util.Map;

import com.darkan.api.accessors.WorldObjects;
import com.darkan.api.inter.Interfaces;
import com.darkan.api.util.Utils;
import com.darkan.api.world.WorldObject;
import com.darkan.api.world.WorldTile;
import com.darkan.scripts.ScriptSkeleton;

import kraken.plugin.api.Player;

public class PalmTrees extends BeachActivity {
    
    private static Map<Integer, WorldTile> PALM_TREE_MAP = new HashMap<>();
    
    static {
        PALM_TREE_MAP.put(117502, new WorldTile(3184, 3215, 0));
        PALM_TREE_MAP.put(117506,  new WorldTile(3178, 3212, 0));
        PALM_TREE_MAP.put(117510, new WorldTile(3175, 3212, 0));
    }
    
    WorldObject currentTree = null;
	
	@Override
	public void loop(ScriptSkeleton ctx, Player self) {
	    if (Interfaces.getInventory().isFull()) {
	        WorldObjects.interactClosestReachable("Deposit coconuts");
	        ctx.setState("Depositing coconuts");
	    } else {
	        if (currentTree == null)
	            currentTree = WorldObjects.getClosestReachable(object -> object.hasOption("Pick coconut"));
	        else if (WorldObjects.getClosestReachable(object -> object.getId() == currentTree.getId()+1) != null)
	            currentTree = WorldObjects.getClosestReachable(object -> object.getId() != currentTree.getId() && object.hasOption("Pick coconut"));
	        
	        WorldTile loc = PALM_TREE_MAP.get(currentTree.getId());
	        
	        if (loc != null) {
	            currentTree = new WorldObject(currentTree.getId(), loc);
	        } else {
	            ctx.setState("Cannot find palm trees.");
	            return;
	        }

	        currentTree.interact("Pick coconut");
	        ctx.setState("Picking coconut from: " + currentTree.getId() + ", " + currentTree.getName() + ", " + currentTree.getX() + ", " + currentTree.getY() + ", " + currentTree.getPlane());
	    }
        ctx.sleepWhile(Integer.MAX_VALUE, () -> ctx.getTimeSinceLastAnimation() < Utils.gaussian(3500, 5000) || self.isAnimationPlaying() || self.isMoving());
	}
}
