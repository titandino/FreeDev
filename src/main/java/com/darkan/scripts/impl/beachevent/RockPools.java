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
