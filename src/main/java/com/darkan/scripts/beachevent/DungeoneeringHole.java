package com.darkan.scripts.beachevent;

import com.darkan.api.accessors.WorldObjects;
import com.darkan.scripts.ScriptSkeleton;

import kraken.plugin.api.Player;

public class DungeoneeringHole extends BeachActivity {

	@Override
	public void loop(ScriptSkeleton ctx, Player self) {
		if (!self.isAnimationPlaying())
			WorldObjects.interactClosestReachable("Dungeoneer");
		ctx.sleepWhile(3000, 10000, () -> self.isAnimationPlaying() || self.isMoving());
	}
}
