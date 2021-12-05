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
package com.darkan.scripts.impl.shatteredworlds;

import com.darkan.cache.def.items.ItemDef.CombatStyle;
import com.darkan.scripts.Script;
import com.darkan.scripts.State;
import com.darkan.scripts.StateMachineScript;
import com.darkan.scripts.impl.shatteredworlds.states.StartWorld;

@Script(value = "Shattered Worlds", debugOnly = true)
public class ShatteredWorlds extends StateMachineScript {	
	
	public CombatStyle attackStyle = CombatStyle.RANGE_CROSSBOW;

	public ShatteredWorlds() {
		super("Shattered Worlds", 200);
	}
	
	@Override
	public boolean onStart() {
		return true;
	}

	@Override
	public State getStartState() {
		return new StartWorld();
	}

	@Override
	public void paintImGui(long runtime) {
		printGenericXpGain(runtime);
	}

	@Override
	public void paintOverlay(long runtime) {
		
	}

	@Override
	public void onStop() {
		
	}

	public CombatStyle getAttackStyle() {
		return attackStyle;
	}
}
