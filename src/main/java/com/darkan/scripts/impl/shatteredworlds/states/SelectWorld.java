package com.darkan.scripts.impl.shatteredworlds.states;

import com.darkan.api.inter.IFComponent;
import com.darkan.api.inter.IFSlot;
import com.darkan.scripts.State;
import com.darkan.scripts.StateMachineScript;

public class SelectWorld extends State {
	
	private static final IFSlot CRACKSMOKE_WORKAROUND = new IFSlot(1867, 5, 1);
	
	private static final IFComponent EQUIP_ME = new IFComponent(1867, 57);
	private static final IFComponent FEED_ME = new IFComponent(1867, 59);
	private static final IFComponent START_WORLD = new IFComponent(1867, 44);
	
	private static final IFSlot CRACKSMOKE_WORKAROUND_2 = new IFSlot(1865, 20, 1);
	private static final IFComponent CONTINUE = new IFComponent(1865, 103);

	@Override
	public State checkNext() {
		if (SelectMutator.isOpen())
			return new SelectMutator();
		if (ClearWorld.getObjective() != null && !ClearWorld.getObjective().contains("you are dead") && !ClearWorld.getObjective().contains("new objective"))
			return new ClearWorld();
		return null;
	}

	@Override
	public void loop(StateMachineScript ctx) {
		if (worldCompleteOpen()) {
			CONTINUE.click(1);
			ctx.sleep(2000);
			return;
		}
		if (isOpen()) {
			EQUIP_ME.click(1);
			FEED_ME.click(1);
			START_WORLD.click(1);
			ctx.sleepWhile(1000, 10000, () -> !SelectMutator.isOpen());
			ctx.setState("Starting world...");
			return;
		}
		ctx.setState("Starting world... but the interface isn't open!");
	}
	
	public static boolean worldCompleteOpen() {
		return CRACKSMOKE_WORKAROUND_2.getText() != null;
	}

	public static boolean isOpen() {
		return CRACKSMOKE_WORKAROUND.getText() != null;
	}
}
