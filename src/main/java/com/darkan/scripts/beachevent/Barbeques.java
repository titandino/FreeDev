package com.darkan.scripts.beachevent;

import java.util.Random;

import com.darkan.api.accessors.WorldObjects;
import com.darkan.api.inter.Interfaces;
import com.darkan.scripts.ScriptSkeleton;

import kraken.plugin.api.Player;

public class Barbeques extends BeachActivity {
    
    private String cooking_option = "";
    private final Random random = new Random();
	
	@Override
	public void loop(ScriptSkeleton ctx, Player self) {
	    if (cooking_option.equals("")) {
            if (random.nextBoolean())
                cooking_option = "Use";
            else
                cooking_option = "Chop";
	    }
	    if (!self.isAnimationPlaying())
	        WorldObjects.interactClosestReachable(cooking_option);
	}
}
