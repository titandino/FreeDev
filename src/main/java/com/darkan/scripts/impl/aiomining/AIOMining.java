package com.darkan.scripts.impl.aiomining;

import com.darkan.scripts.Script;
import com.darkan.scripts.State;
import com.darkan.scripts.StateMachineScript;
import com.darkan.scripts.impl.aiomining.states.MineOre;

@Script("AIO Mining")
public class AIOMining extends StateMachineScript {
	
	private OreData currentOre = OreData.Coal;

	public AIOMining() {
		super("AIO Mining");
	}

	@Override
	public boolean onStart() {
		return true;
	}

	@Override
	public State getStartState() {
		return new MineOre(currentOre);
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
