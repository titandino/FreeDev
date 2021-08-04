package com.darkan.scripts.impl.aiomining.states;

import com.darkan.api.accessors.WorldObjects;
import com.darkan.api.entity.MyPlayer;
import com.darkan.api.inter.Interfaces;
import com.darkan.api.pathing.action.Traversal;
import com.darkan.api.world.WorldObject;
import com.darkan.scripts.State;
import com.darkan.scripts.StateMachineScript;
import com.darkan.scripts.impl.aiomining.OreData;

public class Bank extends State {
	
	private WorldObject depositObj;
	private OreData ore;
	
	public Bank(OreData ore) {
		this.ore = ore;
	}

	@Override
	public State checkNext() {
		if (Interfaces.getInventory().freeSlots() > 8)
			return new MineOre(ore);
		
		depositObj = getClosestOreBank();
		if (depositObj == null && ore.getToBank() != null)
			return new Traversal(this, () -> getClosestOreBank() != null, ore.getToBank());
		return null;
	}

	@Override
	public void loop(StateMachineScript ctx) {
		if (depositObj.interact("Deposit-All (Into Metal Bank)"))
			ctx.sleepWhile(3000, 20000, () -> Interfaces.getInventory().freeSlots() < 8);
		if (ore == OreData.Copper || ore == OreData.Tin)
			ore = ore == OreData.Copper ? OreData.Tin : OreData.Copper;
		ctx.setState("Depositing into metal bank...");
	}
	
	public WorldObject getClosestOreBank() {
		return WorldObjects.getClosest(obj -> obj.hasOption("Deposit-All (Into Metal Bank)") && obj.withinDistance(MyPlayer.getPosition()));
	}
}
