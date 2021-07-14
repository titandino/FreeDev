package com.darkan.scripts.impl.beachevent;

import com.darkan.scripts.ScriptSkeleton;

import kraken.plugin.api.Player;

public abstract class BeachActivity {
	public abstract void loop(ScriptSkeleton ctx, Player self);
}
