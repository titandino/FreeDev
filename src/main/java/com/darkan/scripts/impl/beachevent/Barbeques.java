// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
//
//  Copyright © 2021 Trenton Kress
//  This file is part of project: FreeDev
//
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
