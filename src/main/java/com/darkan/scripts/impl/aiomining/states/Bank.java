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
package com.darkan.scripts.impl.aiomining.states;

import com.darkan.api.accessors.WorldObjects;
import com.darkan.api.entity.MyPlayer;
import com.darkan.api.inter.Interfaces;
import com.darkan.api.pathing.action.Traversal;
import com.darkan.api.world.WorldObject;
import com.darkan.scripts.State;
import com.darkan.scripts.StateMachineScript;
import com.darkan.scripts.impl.aiomining.OreData;

public class Bank extends State {
	
	private WorldObject depositObj;
	private OreData ore;
	
	public Bank(OreData ore) {
		this.ore = ore;
	}

	@Override
	public State checkNext() {
		if (Interfaces.getInventory().freeSlots() > 8)
			return new MineOre(ore);
		
		depositObj = getClosestOreBank();
		if (depositObj == null && ore.getToBank() != null)
			return new Traversal(this, () -> getClosestOreBank() != null, ore.getToBank());
		return null;
	}

	@Override
	public void loop(StateMachineScript ctx) {
		if (depositObj.interact("Deposit-All (Into Metal Bank)"))
			ctx.sleepWhile(3000, 20000, () -> Interfaces.getInventory().freeSlots() < 8);
		if (ore == OreData.Copper || ore == OreData.Tin)
			ore = ore == OreData.Copper ? OreData.Tin : OreData.Copper;
		ctx.setState("Depositing into metal bank...");
	}
	
	public WorldObject getClosestOreBank() {
		return WorldObjects.getClosest(obj -> obj.hasOption("Deposit-All (Into Metal Bank)") && obj.withinDistance(MyPlayer.getPosition()));
	}
}
