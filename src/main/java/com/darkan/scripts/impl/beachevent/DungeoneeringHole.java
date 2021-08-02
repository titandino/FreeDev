package com.darkan.scripts.impl.beachevent;

import com.darkan.api.accessors.WorldObjects;
import com.darkan.api.entity.MyPlayer;
import com.darkan.scripts.LoopScript;

public class DungeoneeringHole extends BeachActivity {

	@Override
	public void loop(LoopScript ctx) {
		if (!MyPlayer.get().isAnimationPlaying())
			WorldObjects.interactClosestReachable("Dungeoneer");
		ctx.setState("What hole does the player fit in? That's right, the dung hole.");
		ctx.sleepWhile(3000, Integer.MAX_VALUE, () -> MyPlayer.get().isAnimationPlaying() || MyPlayer.get().isMoving());
	}
}
