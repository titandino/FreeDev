package com.darkan.scripts.beachevent;

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
	    if (!self.isAnimationPlaying())
	        WorldObjects.interactClosestReachable(cookingOption);
	}
}
