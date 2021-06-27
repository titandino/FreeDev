package com.darkan.plugins.pkavoider;

import com.darkan.kraken.inter.Interfaces;
import com.darkan.kraken.util.Area;
import com.darkan.kraken.world.WorldTile;
import com.darkan.plugins.PluginSkeleton;

import kraken.plugin.api.Player;
import kraken.plugin.api.Players;
import kraken.plugin.api.Vector3i;

public class PKAvoider extends PluginSkeleton {
	
	public static final int RING_OF_FORTUNE = 39808;
	
	private static Area OPEN_WILDY = new Area(new WorldTile(2944, 3521), new WorldTile(3400, 3810));
	private static Area FORINTHRY = new Area(new WorldTile(3008, 10048), new WorldTile(3136, 10176));

	public PKAvoider() {
		super("PK Avoider", 100);
	}

	@Override
	public boolean onStart(Player self) {
		return true;
	}

	@Override
	public void loop(Player self) {
		Vector3i pos = self.getGlobalPosition();
		if (!OPEN_WILDY.inside(pos) && !FORINTHRY.inside(pos))
			return;
		Player player = Players.closest(p -> !p.getName().equals(self.getName()));
		if (player != null) {
			if (Interfaces.getEquipment().clickItem(RING_OF_FORTUNE, 2))
				sleep(600);
		}
	}
	
	@Override
	public void paint(long runtime) {
		
	}
}
