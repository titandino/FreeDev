package com.darkan.scripts.impl.aiomining.states;

import com.darkan.api.accessors.SpotAnims;
import com.darkan.api.accessors.WorldObjects;
import com.darkan.api.entity.MyPlayer;
import com.darkan.api.inter.Interfaces;
import com.darkan.api.inter.chat.Message;
import com.darkan.api.listeners.MessageListener;
import com.darkan.api.pathing.action.Traversal;
import com.darkan.api.util.Utils;
import com.darkan.api.world.SpotAnim;
import com.darkan.api.world.WorldObject;
import com.darkan.scripts.State;
import com.darkan.scripts.StateMachineScript;
import com.darkan.scripts.impl.aiomining.OreData;

public class MineOre extends State implements MessageListener {
	
	private boolean oreBoxFilled = false;
	private WorldObject rock;
	private OreData ore;
	
	public MineOre(OreData ore) {
		this.ore = ore;
	}
	
	@Override
	public State checkNext() {
		if ((Interfaces.getInventory().getItemReg("ore box") == null || oreBoxFilled) && Interfaces.getInventory().isFull() /*MyPlayer.getVars().getVarBit(currentOre.getVarbit()) < 120*/)
			return new Bank(ore);
		rock = getClosestRock();
		if (rock == null && ore.getFromBank() != null)
			return new Traversal(this, () -> getClosestRock() != null, ore.getFromBank());
		return null;
	}

	@Override
	public void loop(StateMachineScript ctx) {
		if (Interfaces.getInventory().getItemReg("ore box") != null && !oreBoxFilled && Interfaces.getInventory().freeSlots() <= Utils.random(2, 6)) {
			if (Interfaces.getInventory().clickItemReg("ore box", "Fill"))
				ctx.sleep(Utils.gaussian(2000, 1000));
			ctx.setState("Filling ore box...");
			return;
		}
		SpotAnim rt = getRockertunity();
		if (rt != null)
			rock = WorldObjects.getClosestTo(rt != null ? rt.getPosition() : MyPlayer.getPosition(), obj -> obj.hasOption("Mine") && obj.getName().contains(ore.name()) && obj.withinDistance(MyPlayer.getPosition()));
		if (rock.interact("Mine"))
			ctx.sleepWhile(3000, Utils.gaussian(9000, 8000), () -> getRockertunity() == null && (MyPlayer.get().isMoving() || Interfaces.getInventory().freeSlots() > Utils.random(2, 6)));
		ctx.setState("Mining...");
	}
	
	public WorldObject getClosestRock() {
		return WorldObjects.getClosestReachable(obj -> obj.hasOption("Mine") && obj.getName().contains(ore.name()) && obj.withinDistance(MyPlayer.getPosition()));
	}

	public SpotAnim getRockertunity() {
		return SpotAnims.getClosest(sa -> sa.getId() == 7164 || sa.getId() == 7165);
	}
	
	@Override
	public void onMessageReceived(Message message) {
		if (message.isGame() && message.getText().contains("anything in your backpack into your ore box"))
			oreBoxFilled = true;
	}
}
