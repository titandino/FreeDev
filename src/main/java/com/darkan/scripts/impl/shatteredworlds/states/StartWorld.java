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
package com.darkan.scripts.impl.shatteredworlds.states;

import com.darkan.api.accessors.WorldObjects;
import com.darkan.api.entity.MyPlayer;
import com.darkan.api.inter.chat.Message;
import com.darkan.api.listeners.MessageListener;
import com.darkan.api.world.WorldTile;
import com.darkan.scripts.State;
import com.darkan.scripts.StateMachineScript;

public class StartWorld extends State implements MessageListener {
	
	
	private static final WorldTile OUTSIDE = new WorldTile(3168, 3136, 0);

	@Override
	public State checkNext() {
		if (SelectMutator.isOpen())
			return new SelectMutator();
		if (SelectWorld.isOpen() || SelectWorld.worldCompleteOpen())
			return new SelectWorld();
		if (ClearWorld.getObjective() != null && !ClearWorld.getObjective().contains("you are dead") && !ClearWorld.getObjective().contains("new objective"))
			return new ClearWorld();
		return null;
	}

	@Override
	public void loop(StateMachineScript ctx) {
		if (OUTSIDE.withinDistance(MyPlayer.getPosition(), 50) || WorldObjects.getClosest(o -> o.hasOption("Return to Shattered Worlds")) != null) {
			if (WorldObjects.interactClosest("Enter", obj -> obj.getDef().name.equals("Shattered Worlds portal")) || WorldObjects.interactClosest("Return to Shattered Worlds")) {
				ctx.sleepWhile(3000, 10000, () -> !SelectWorld.isOpen() && !SelectMutator.isOpen());
				ctx.setState("Clicking portal...");
				return;
			}
		}
	}

	@Override
	public void onMessageReceived(Message message) {
		// TODO Auto-generated method stub
		
	}

}
