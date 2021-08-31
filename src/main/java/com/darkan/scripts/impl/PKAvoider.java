package com.darkan.scripts.impl;

import com.darkan.api.entity.MyPlayer;
import com.darkan.api.inter.Interfaces;
import com.darkan.api.util.Area;
import com.darkan.api.world.WorldTile;
import com.darkan.scripts.LoopScript;
import com.darkan.scripts.Script;

import kraken.plugin.api.Player;
import kraken.plugin.api.Players;

@Script(value = "PKer Avoider", utility = true)
public class PKAvoider extends LoopScript {
	
	public static final int RING_OF_FORTUNE = 39808;
	
	private static Area OPEN_WILDY = new Area(new WorldTile(2944, 3521), new WorldTile(3400, 3810));
	private static Area FORINTHRY = new Area(new WorldTile(3008, 10048), new WorldTile(3136, 10176));

	public PKAvoider() {
		super("PK Avoider", 100);
	}

	@Override
	public boolean onStart() {
		return true;
	}

	@Override
	public void loop() {
		if (!OPEN_WILDY.inside(MyPlayer.getPosition()) && !FORINTHRY.inside(MyPlayer.getPosition()))
			return;
		Player player = Players.closest(p -> !p.getName().equals(MyPlayer.get().getName()));
		if (player != null) {
			if (Interfaces.getEquipment().clickItem(RING_OF_FORTUNE, "Grand Exchange"))
				sleep(600);
		}
	}
	
	@Override
	public void paintOverlay(long runtime) {
		
	}
	
	@Override
	public void paintImGui(long runtime) {
		
	}

	@Override
	public void onStop() {
		
	}
}
