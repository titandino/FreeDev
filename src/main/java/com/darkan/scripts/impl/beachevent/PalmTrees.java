package com.darkan.scripts.impl.beachevent;

import java.util.HashMap;
import java.util.Map;

import com.darkan.api.accessors.WorldObjects;
import com.darkan.api.entity.MyPlayer;
import com.darkan.api.inter.Interfaces;
import com.darkan.api.util.Utils;
import com.darkan.api.world.WorldObject;
import com.darkan.api.world.WorldTile;
import com.darkan.scripts.LoopScript;

public class PalmTrees extends BeachActivity {
    
    private static Map<Integer, WorldTile> PALM_TREE_MAP = new HashMap<>();
    
    static {
        PALM_TREE_MAP.put(117502, new WorldTile(3184, 3215, 0));
        PALM_TREE_MAP.put(117506,  new WorldTile(3178, 3212, 0));
        PALM_TREE_MAP.put(117510, new WorldTile(3175, 3212, 0));
    }
    
    WorldObject currentTree = null;
	
	@Override
	public void loop(LoopScript ctx) {
	    if (Interfaces.getInventory().isFull()) {
	        WorldObjects.interactClosestReachable("Deposit coconuts");
	        ctx.setState("Depositing coconuts");
	    } else {
	        currentTree = WorldObjects.getClosest(object -> 
	            PALM_TREE_MAP.containsKey(object.getId()) && (currentTree != null ? currentTree.getId() != object.getId() : true));
	        
	        WorldTile loc = PALM_TREE_MAP.get(currentTree.getId());
	        
	        if (loc != null) {
	            currentTree = new WorldObject(currentTree.getId(), loc);
	        } else {
	            ctx.setState("Cannot find palm trees.");
	            return;
	        }

	        currentTree.interact("Pick coconut");
	        ctx.setState("Picking coconuts from: " + currentTree.getId() + ", " + currentTree.getName() + ", " + currentTree.getX() + ", " + currentTree.getY() + ", " + currentTree.getPlane());
	    }
        ctx.sleepWhile(Integer.MAX_VALUE, () -> ctx.getTimeSinceLastAnimation() < Utils.gaussian(3500, 5000) || ctx.getTimeSinceLastMoving() < Utils.gaussian(3500, 5000)|| MyPlayer.get().isAnimationPlaying() || MyPlayer.get().isMoving());
	}
}
