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

import com.darkan.api.inter.IFComponent;
import com.darkan.api.inter.IFSlot;
import com.darkan.scripts.State;
import com.darkan.scripts.StateMachineScript;

public class SelectWorld extends State {
	
	private static final IFSlot CRACKSMOKE_WORKAROUND = new IFSlot(1867, 5, 1);
	
	private static final IFComponent EQUIP_ME = new IFComponent(1867, 57);
	private static final IFComponent FEED_ME = new IFComponent(1867, 59);
	private static final IFComponent START_WORLD = new IFComponent(1867, 44);
	
	private static final IFSlot CRACKSMOKE_WORKAROUND_2 = new IFSlot(1865, 20, 1);
	private static final IFComponent CONTINUE = new IFComponent(1865, 103);

	@Override
	public State checkNext() {
		if (SelectMutator.isOpen())
			return new SelectMutator();
		if (ClearWorld.getObjective() != null && !ClearWorld.getObjective().contains("you are dead") && !ClearWorld.getObjective().contains("new objective"))
			return new ClearWorld();
		return null;
	}

	@Override
	public void loop(StateMachineScript ctx) {
		if (worldCompleteOpen()) {
			CONTINUE.click(1);
			ctx.sleep(2000);
			return;
		}
		if (isOpen()) {
			EQUIP_ME.click(1);
			FEED_ME.click(1);
			START_WORLD.click(1);
			ctx.sleepWhile(1000, 10000, () -> !SelectMutator.isOpen());
			ctx.setState("Starting world...");
			return;
		}
		ctx.setState("Starting world... but the interface isn't open!");
	}
	
	public static boolean worldCompleteOpen() {
		return CRACKSMOKE_WORKAROUND_2.getText() != null;
	}

	public static boolean isOpen() {
		return CRACKSMOKE_WORKAROUND.getText() != null;
	}
}
