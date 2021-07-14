package com.darkan.scripts.beachevent;

import com.darkan.api.accessors.WorldObjects;
import com.darkan.scripts.ScriptSkeleton;

import kraken.plugin.api.Player;

public class DungeoneeringHole extends BeachActivity {

	@Override
	public void loop(ScriptSkeleton ctx, Player self) {
		if (!self.isAnimationPlaying())
			WorldObjects.interactClosestReachable("Dungeoneer");
		ctx.setState("What hole does the player fit in? That's right, the dung hole.");
		ctx.sleepWhile(3000, Integer.MAX_VALUE, () -> self.isAnimationPlaying() || self.isMoving());
	}
}
