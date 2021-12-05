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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.darkan.api.accessors.NPCs;
import com.darkan.api.entity.NPC;
import com.darkan.scripts.LoopScript;
import com.darkan.scripts.Script;

import kraken.plugin.api.ImGui;
import kraken.plugin.api.Time;

@Script(value = "Spirit Attraction", utility = true)
public class SpiritAttraction extends LoopScript {

	private static Set<String> SPIRIT_NAMES = new HashSet<>(Arrays.asList(new String[] { 
			"Seren spirit", "Fire spirit", "Divine blessing", "Divine fire spirit", 
			"Forge phoenix", "Manifested knowledge", "Divine forge phoenix", "Divine carpet dust" 
			}));
	
	private int spirits = 0;
	
	public SpiritAttraction() {
		super("Spirit Attraction", 2000);
	}
	
	@Override
	public boolean onStart() {
		return true;
	}

	@Override
	public void loop() {
		setState("Waiting for spirit...");
		NPC serenSpirit = NPCs.getClosestReachable(npc -> SPIRIT_NAMES.contains(npc.getName()));
		if (serenSpirit != null) {
			setState("Interacting with spirit...");
			serenSpirit.interact(0);
			spirits++;
			sleep(2500);
		}
	}
	
	@Override
	public void paintOverlay(long runtime) {
		
	}

	@Override
	public void paintImGui(long runtime) {
		ImGui.label("Spirits p/h: " + Time.perHour(runtime, spirits));
	}

	@Override
	public void onStop() {
		
	}
}
