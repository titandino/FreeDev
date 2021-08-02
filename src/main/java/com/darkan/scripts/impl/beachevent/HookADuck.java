package com.darkan.scripts.impl.beachevent;

import com.darkan.api.accessors.WorldObjects;
import com.darkan.api.entity.MyPlayer;
import com.darkan.api.util.Utils;
import com.darkan.scripts.LoopScript;

public class HookADuck extends BeachActivity {

	@Override
	public void loop(LoopScript ctx) {
		if (!MyPlayer.get().isAnimationPlaying())
			WorldObjects.interactClosestReachable("Use a Light weight rod");
		ctx.setState("Hooking... Ducks...");
		ctx.sleepWhile(3000, Integer.MAX_VALUE, () -> ctx.getTimeSinceLastAnimation() < Utils.gaussian(3000, 2500));
	}
}
