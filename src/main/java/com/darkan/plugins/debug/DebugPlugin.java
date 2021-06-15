package com.darkan.plugins.debug;

import java.util.Arrays;

import com.darkan.plugins.PluginSkeleton;

import kraken.plugin.api.Debug;
import kraken.plugin.api.Player;
import kraken.plugin.api.Widget;
import kraken.plugin.api.Widgets;

public class DebugPlugin extends PluginSkeleton {

	public DebugPlugin() {
		super("Debug", 600);
	}

	@Override
	public boolean onStart(Player self) {
		return true;
	}

	@Override
	public void loop(Player self) {
		Widget[] group = Widgets.getGroupById(1430).getWidgets();
		Debug.log(Arrays.toString(group));
		Widget[] sub1 = group[5].getChildren();
		Debug.log(Arrays.toString(sub1));
		Widget[] sub2 = sub1[1].getChildren();
		Debug.log(Arrays.toString(sub2));
		Widget sub3 = sub2[8];
		Debug.log(sub3.toString());
	}
	
	@Override
	public void paint(long runtime) {
		
	}
}
