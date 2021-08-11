package com.darkan.scripts.impl.shatteredworlds.states;

import com.darkan.api.accessors.NPCs;
import com.darkan.api.accessors.WorldObjects;
import com.darkan.api.entity.MyPlayer;
import com.darkan.api.inter.IFSlot;
import com.darkan.api.inter.Interfaces;
import com.darkan.api.inter.chat.Message;
import com.darkan.api.item.Item;
import com.darkan.api.listeners.MessageListener;
import com.darkan.scripts.State;
import com.darkan.scripts.StateMachineScript;
import com.darkan.scripts.impl.shatteredworlds.ShatteredWorlds;

public class ClearWorld extends State implements MessageListener {
	
	private static IFSlot OBJECTIVE_STATUS = new IFSlot(1866, 25, 3);
	private static IFSlot WORLD = new IFSlot(1866, 31, 0);
	
	private boolean completedFloor = false;
	private boolean clickedPortal = false;

	@Override
	public State checkNext() {
		if (isDead() || !WorldObjects.getNearby(obj -> obj.withinDistance(MyPlayer.getPosition(), 10) && obj.getName().contains("Bank chest")).isEmpty())
			return new StartWorld();
		if (completedFloor && clickedPortal && SelectMutator.isOpen())
			return new SelectMutator();
		return null;
	}

	@Override
	public void loop(StateMachineScript ctx) {
		if (completedFloor) {
			if (WorldObjects.interactClosest("Repair", o -> o.getName().equals("Portal"))) {
				ctx.sleepWhile(2000, 20000, () -> MyPlayer.get().isMoving());
				ctx.setState("Repairing portal...");
				return;
			}
			if (WorldObjects.interactClosest("Continue", o -> o.getName().equals("Portal"))) {
				ctx.sleepWhile(2000, 20000, () -> MyPlayer.get().isMoving());
				clickedPortal = true;
			}
			ctx.setState("Proceeding to next floor...");
			return;
		}
		if (Interfaces.getEquipment().getItem(i -> i.getDef().getCombatStyle() == ((ShatteredWorlds)ctx).getAttackStyle()) == null) {
			for (Item item : Interfaces.getInventory().getItems(i -> i.getDef().getCombatStyle() == ((ShatteredWorlds)ctx).getAttackStyle()))
				item.click("Wield");
			ctx.sleepWhile(2000, 10000, () -> Interfaces.getEquipment().getItem(i -> i.getDef().getCombatStyle() == ((ShatteredWorlds)ctx).getAttackStyle()) == null);
			ctx.setState("Equipping chosen combat style...");
			return;
		}
		if (Interfaces.getInventory().isFull() || MyPlayer.getHealthPerc() < 50.0) {
			Interfaces.getInventory().clickItemReg("chicken", "Eat");
			ctx.sleep(1200);
		}
		if (MyPlayer.get().isMoving() || MyPlayer.get().getInteractingIndex() != -1)
			return;
		if (NPCs.interactClosest("Attack")) {
			ctx.setState("Attacking a new monster...");
			ctx.sleepWhile(3000, 50000, () -> MyPlayer.getHealthPerc() > 50.0 && MyPlayer.get().isMoving() && MyPlayer.get().getInteractingIndex() != -1);
			return;
		}
	}

	public static String getObjective() {
		return OBJECTIVE_STATUS.getText();
	}

	public static String getWorld() {
		return WORLD.getText();
	}
	
	public static boolean isDead() {
		String text = OBJECTIVE_STATUS.getText();
		return text != null && text.toLowerCase().contains("you are dead");
	}

	@Override
	public void onMessageReceived(Message message) {
		if (message.isGame() && message.getText().contains("Proceed to the exit portal to advance.") || message.getText().contains("You now have the 3 required portal repair kits to fix the portal!"))
			completedFloor = true;
		else if (message.isGame() && message.getText().contains("New Objective")) {
			completedFloor = false;
			clickedPortal = false;
		}
	}
}
