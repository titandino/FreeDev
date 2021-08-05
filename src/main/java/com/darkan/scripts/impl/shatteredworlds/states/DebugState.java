package com.darkan.scripts.impl.shatteredworlds.states;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.darkan.api.accessors.WorldObjects;
import com.darkan.api.entity.MyPlayer;
import com.darkan.api.inter.IFComponent;
import com.darkan.api.inter.IFSlot;
import com.darkan.api.inter.chat.Message;
import com.darkan.api.listeners.MessageListener;
import com.darkan.api.world.WorldTile;
import com.darkan.scripts.State;
import com.darkan.scripts.StateMachineScript;

public class DebugState extends State implements MessageListener {
	
	private static final String[] MUTATOR_PRIORITY_ORDER = {
		"Feeling Pumped",		//Unlimited adrenaline
		"Vampyric",				//Soul split
		"Electric",				//Free AOE DPS
		"Charged",				//Free AOE DPS
		"Power Grows",			//Free stat buffs
		"Zone of Restoration",	//Free health and prayer
		"Blood Money",			//Free anima
		"Laser Lodestones",		//Literally does nothing
		"Unstable Rifts",		//^
		"Hydra",				//More XP but more mobs
		"Honourable",			//More DPS more damage taken
		"Explosive",			//Enemies explode and damage you
		"Die Together",			//Have to kill monsters at the same time aids.
		"Combustion",			//Fire AOEs on the ground burn you
		"Catastrophe",			//
		"Freezing",
		"Hungry",
		"Leeching",
		"Protect",
		"Rockfall",
		"Static",
		"Volcanic",
		"Zombie Apocalypse",
		"Zone of Suffering"
		
	};
	
	private static final IFSlot[] MUTATORS = { new IFSlot(1868, 0, 2), new IFSlot(1868, 1, 2), new IFSlot(1868, 5, 2) };
	
	private static final IFSlot CRACKSMOKE_WORKAROUND = new IFSlot(1867, 5, 1);
	private static final IFComponent EQUIP_ME = new IFComponent(1867, 57);
	private static final IFComponent FEED_ME = new IFComponent(1867, 59);
	private static final IFComponent START_WORLD = new IFComponent(1867, 44);
	
	private static final WorldTile OUTSIDE = new WorldTile(3168, 3136, 0);

	@Override
	public State checkNext() {
		return null;
	}

	@Override
	public void loop(StateMachineScript ctx) {
		if (OUTSIDE.withinDistance(MyPlayer.getPosition(), 50)) {
			IFComponent bestMutator = getBestMutator();
			if (bestMutator != null) {
				ctx.setState("Selecting best mutator...");
				bestMutator.click(1);
				ctx.sleepWhile(1000, 10000, () -> OUTSIDE.withinDistance(MyPlayer.getPosition(), 50));
				return;
			}
			if (CRACKSMOKE_WORKAROUND.getText() != null) {
				ctx.setState("Starting world...");
				EQUIP_ME.click(1);
				FEED_ME.click(1);
				START_WORLD.click(1);
				ctx.sleepWhile(1000, 10000, () -> getBestMutator() == null);
				return;
			}
			if (WorldObjects.interactClosest("Enter", obj -> obj.getDef().name.equals("Shattered Worlds portal"))) {
				ctx.sleepWhile(1000, 10000, () -> CRACKSMOKE_WORKAROUND.getText() == null);
				ctx.setState("Clicking portal...");
				return;
			}
		}
	}
	
	public IFComponent getBestMutator() {
		Map<String, IFComponent> mutatorNames = new HashMap<>();
		for (IFSlot mutator : MUTATORS) {
			String name = mutator.getText();
			if (name == null || name.isEmpty())
				return null;
			mutatorNames.put(name, mutator.getParent());
		}
		for (String name : MUTATOR_PRIORITY_ORDER)
			if (mutatorNames.get(name) != null)
				return mutatorNames.get(name);
		System.err.println("Error getting perk: " + mutatorNames.toString());
		return null;
	}

	@Override
	public void onMessageReceived(Message message) {
		// TODO Auto-generated method stub
		
	}

}
