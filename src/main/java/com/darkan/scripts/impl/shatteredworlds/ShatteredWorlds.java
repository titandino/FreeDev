package com.darkan.scripts.impl.shatteredworlds;

import com.darkan.scripts.Script;
import com.darkan.scripts.State;
import com.darkan.scripts.StateMachineScript;
import com.darkan.scripts.impl.shatteredworlds.states.DebugState;

@Script(value = "Shattered Worlds", debugOnly = true)
public class ShatteredWorlds extends StateMachineScript {

	public ShatteredWorlds() {
		super("Shattered Worlds", 200);
	}
	
	@Override
	public boolean onStart() {
		return true;
	}

	@Override
	public State getStartState() {
		return new DebugState();
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

}
