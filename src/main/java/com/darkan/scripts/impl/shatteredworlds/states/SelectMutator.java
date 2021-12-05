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

import java.util.HashMap;
import java.util.Map;

import com.darkan.api.inter.IFComponent;
import com.darkan.api.inter.IFSlot;
import com.darkan.scripts.State;
import com.darkan.scripts.StateMachineScript;

public class SelectMutator extends State {

	private static final String[] MUTATOR_PRIORITY_ORDER = { 
			"Feeling Pumped", // Unlimited adrenaline
			"Vampyric", // Soul split
			"Electric", // Free AOE DPS
			"Charged", // Free AOE DPS
			"Power Grows", // Free stat buffs
			"Zone of Restoration", // Free health and prayer
			"Blood Money", // Free anima
			"Laser Lodestones", // Literally does nothing
			"Unstable Rifts", // ^
			"Hydra", // More XP but more mobs
			"Honourable", // More DPS more damage taken
			"Explosive", // Enemies explode and damage you
			"Die Together", // Have to kill monsters at the same time aids.
			"Combustion", // Fire AOEs on the ground burn you
			"Catastrophe", //
			"Freezing", "Hungry", "Leeching", "Protect", "Rockfall", "Static", "Volcanic", "Zombie Apocalypse", "Zone of Suffering" };

	private static final IFSlot[] MUTATORS = { new IFSlot(1868, 0, 2), new IFSlot(1868, 1, 2), new IFSlot(1868, 5, 2) };
	
	@Override
	public State checkNext() { //need a way of checking on mutator if you're still in a world
		if (ClearWorld.getObjective() != null && !ClearWorld.getObjective().contains("you are dead") && !ClearWorld.getObjective().contains("new objective"))
			return new ClearWorld();
		if (!isOpen())
			return new StartWorld();
		return null;
	}

	@Override
	public void loop(StateMachineScript ctx) {
		IFComponent bestMutator = getBestMutator();
		if (bestMutator != null) {
			ctx.setState("Selecting best mutator...");
			bestMutator.click(1);
			ctx.sleep(3000);
			return;
		}
		ctx.setState("Selecting best mutator... but there is none!");
	}
	
	public static IFComponent getBestMutator() {
		Map<String, IFComponent> mutatorNames = new HashMap<>();
		for (IFSlot mutator : MUTATORS) {
			String name = mutator.getText();
			if (name == null || name.isEmpty())
				return null;
			mutatorNames.put(name, mutator.getParent());
		}
		for (String name : MUTATOR_PRIORITY_ORDER)
			if (mutatorNames.get(name) != null)
				return mutatorNames.get(name);
		System.err.println("Error getting perk: " + mutatorNames.toString());
		return null;
	}

	public static boolean isOpen() {
		return getBestMutator() != null;
	}

}
