package com.darkan.scripts.impl.beachevent;

import java.util.Random;

import com.darkan.api.accessors.WorldObjects;
import com.darkan.scripts.ScriptSkeleton;

import kraken.plugin.api.Player;

public class Barbeques extends BeachActivity {
    
    private String cookingOption = "";
    private final Random random = new Random();
	
	@Override
	public void loop(ScriptSkeleton ctx, Player self) {  
	    if (cookingOption.equals("")) {
            if (random.nextBoolean())
                cookingOption = "Use";
            else
                cookingOption = "Chop";
	    }
	    if (!self.isAnimationPlaying() && !self.isMoving()) {
	        WorldObjects.interactClosestReachable(cookingOption, object -> cookingOption.equals("Use") ? object.getName().equals("Grill") : object.getName().equals("Large Chopping Board"));
	        ctx.setState(cookingOption.equals("Use") ? "Grilling at the barbeques... " : "Chopping at the cutting boards...");
	    }
        ctx.sleepWhile(2400, Integer.MAX_VALUE, () -> self.isAnimationPlaying());

	}
}
