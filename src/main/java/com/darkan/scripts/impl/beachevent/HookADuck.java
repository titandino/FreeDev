package com.darkan.scripts.beachevent;

import com.darkan.api.accessors.WorldObjects;
import com.darkan.api.util.Utils;
import com.darkan.scripts.ScriptSkeleton;

import kraken.plugin.api.Player;

public class HookADuck extends BeachActivity {

	@Override
	public void loop(ScriptSkeleton ctx, Player self) {
		if (!self.isAnimationPlaying())
			WorldObjects.interactClosestReachable("Use a Light weight rod");
		ctx.setState("Hooking... Ducks...");
		ctx.sleepWhile(3000, Integer.MAX_VALUE, () -> ctx.getTimeSinceLastAnimation() < Utils.gaussian(3000, 2500));
	}
}
