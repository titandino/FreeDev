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

import com.darkan.api.entity.MyPlayer;
import com.darkan.api.inter.Interfaces;
import com.darkan.api.util.Area;
import com.darkan.api.world.WorldTile;
import com.darkan.scripts.LoopScript;
import com.darkan.scripts.Script;

import kraken.plugin.api.Player;
import kraken.plugin.api.Players;

@Script(value = "PKer Avoider", utility = true)
public class PKAvoider extends LoopScript {
	
	public static final int RING_OF_FORTUNE = 39808;
	
	private static Area OPEN_WILDY = new Area(new WorldTile(2944, 3521), new WorldTile(3400, 3810));
	private static Area FORINTHRY = new Area(new WorldTile(3008, 10048), new WorldTile(3136, 10176));

	public PKAvoider() {
		super("PK Avoider", 100);
	}

	@Override
	public boolean onStart() {
		return true;
	}

	@Override
	public void loop() {
		if (!OPEN_WILDY.inside(MyPlayer.getPosition()) && !FORINTHRY.inside(MyPlayer.getPosition()))
			return;
		Player player = Players.closest(p -> !p.getName().equals(MyPlayer.get().getName()));
		if (player != null) {
			if (Interfaces.getEquipment().clickItem(RING_OF_FORTUNE, "Grand Exchange"))
				sleep(600);
		}
	}
	
	@Override
	public void paintOverlay(long runtime) {
		
	}
	
	@Override
	public void paintImGui(long runtime) {
		
	}

	@Override
	public void onStop() {
		
	}
}
