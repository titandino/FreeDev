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
package com.darkan.scripts.impl;

import com.darkan.api.accessors.WorldObjects;
import com.darkan.api.entity.MyPlayer;
import com.darkan.api.inter.Interfaces;
import com.darkan.scripts.LoopScript;
import com.darkan.scripts.Script;

import kraken.plugin.api.Inventory;

@Script("Gather Nightshades")
public class NightShadeGatherer extends LoopScript {
	
	public NightShadeGatherer() {
		super("Gather Nightshades", 250);
	}
	
	@Override
	public boolean onStart() {
		return true;
	}
	
	@Override
	public void loop() {
		if (MyPlayer.get().isMoving())
			return;
		if (Inventory.isFull()) {
			if (Interfaces.getInventory().containsAnyReg("of the porter"))
				Interfaces.getEquipment().clickItem("Grace of the elves", "Charge all porters");
			sleep(3000);
			return;
		}
		if (!Inventory.isFull() && WorldObjects.interactClosest("Pick", o -> o.getId() == 114921))
			sleepWhile(1000, 10000, () -> MyPlayer.get().isMoving());
	}

	@Override
	public void paintOverlay(long runtime) {
		
	}

	@Override
	public void paintImGui(long runtime) {
		printGenericXpGain(runtime);
	}

	@Override
	public void onStop() {
		
	}
}
