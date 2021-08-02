package com.darkan.scripts.impl.beachevent;

import java.util.Random;

import com.darkan.api.accessors.WorldObjects;
import com.darkan.api.entity.MyPlayer;
import com.darkan.scripts.LoopScript;

public class Barbeques extends BeachActivity {
    
    private String cookingOption = "";
    private final Random random = new Random();
	
	@Override
	public void loop(LoopScript ctx) {  
	    if (cookingOption.equals("")) {
            if (random.nextBoolean())
                cookingOption = "Use";
            else
                cookingOption = "Chop";
	    }
	    if (!MyPlayer.get().isAnimationPlaying() && !MyPlayer.get().isMoving()) {
	        WorldObjects.interactClosestReachable(cookingOption, object -> cookingOption.equals("Use") ? object.getName().equals("Grill") : object.getName().equals("Large Chopping Board"));
	        ctx.setState(cookingOption.equals("Use") ? "Grilling at the barbeques... " : "Chopping at the cutting boards...");
	    }
        ctx.sleepWhile(2400, Integer.MAX_VALUE, () -> MyPlayer.get().isAnimationPlaying());

	}
}
