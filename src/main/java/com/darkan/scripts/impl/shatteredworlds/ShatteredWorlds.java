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
