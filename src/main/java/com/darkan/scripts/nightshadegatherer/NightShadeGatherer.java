package com.darkan.scripts.nightshadegatherer;

import com.darkan.api.accessors.WorldObjects;
import com.darkan.api.inter.Interfaces;
import com.darkan.scripts.Script;
import com.darkan.scripts.ScriptSkeleton;

import kraken.plugin.api.Inventory;
import kraken.plugin.api.Player;

@Script("Gather Nightshades")
public class NightShadeGatherer extends ScriptSkeleton {
	
	public NightShadeGatherer() {
		super("Gather Nightshades", 250);
	}
	
	@Override
	public boolean onStart(Player self) {
		return true;
	}
	
	@Override
	public void loop(Player self) {
		if (self.isMoving())
			return;
		if (Inventory.isFull()) {
			if (Interfaces.getInventory().containsAnyReg("of the porter"))
				Interfaces.getEquipment().clickItem("Grace of the elves", "Charge all porters");
			sleep(3000);
			return;
		}
		if (!Inventory.isFull() && WorldObjects.interactClosest("Pick", o -> o.getId() == 114921))
			sleepWhile(1000, 10000, () -> self.isMoving());
	}

	@Override
	public void paintOverlay(long runtime) {
		
	}

	@Override
	public void paintImGui(long runtime) {
		printGenericXpGain(runtime);
	}

	@Override
	public void onStop() {
		
	}
}
