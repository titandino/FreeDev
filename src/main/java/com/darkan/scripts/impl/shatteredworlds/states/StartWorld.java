package com.darkan.scripts.impl.shatteredworlds.states;

import com.darkan.api.accessors.WorldObjects;
import com.darkan.api.entity.MyPlayer;
import com.darkan.api.inter.chat.Message;
import com.darkan.api.listeners.MessageListener;
import com.darkan.api.world.WorldTile;
import com.darkan.scripts.State;
import com.darkan.scripts.StateMachineScript;

public class StartWorld extends State implements MessageListener {
	
	
	private static final WorldTile OUTSIDE = new WorldTile(3168, 3136, 0);

	@Override
	public State checkNext() {
		if (SelectMutator.isOpen())
			return new SelectMutator();
		if (SelectWorld.isOpen() || SelectWorld.worldCompleteOpen())
			return new SelectWorld();
		if (ClearWorld.getObjective() != null && !ClearWorld.getObjective().contains("you are dead") && !ClearWorld.getObjective().contains("new objective"))
			return new ClearWorld();
		return null;
	}

	@Override
	public void loop(StateMachineScript ctx) {
		if (OUTSIDE.withinDistance(MyPlayer.getPosition(), 50) || WorldObjects.getClosest(o -> o.hasOption("Return to Shattered Worlds")) != null) {
			if (WorldObjects.interactClosest("Enter", obj -> obj.getDef().name.equals("Shattered Worlds portal")) || WorldObjects.interactClosest("Return to Shattered Worlds")) {
				ctx.sleepWhile(3000, 10000, () -> !SelectWorld.isOpen() && !SelectMutator.isOpen());
				ctx.setState("Clicking portal...");
				return;
			}
		}
	}

	@Override
	public void onMessageReceived(Message message) {
		// TODO Auto-generated method stub
		
	}

}
