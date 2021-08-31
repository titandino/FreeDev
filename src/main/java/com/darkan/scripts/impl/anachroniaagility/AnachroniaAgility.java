package com.darkan.scripts.impl.anachroniaagility;

import com.darkan.api.entity.MyPlayer;
import com.darkan.api.inter.Interfaces;
import com.darkan.api.item.Item;
import com.darkan.api.util.Utils;
import com.darkan.api.world.WorldObject;
import com.darkan.scripts.LoopScript;
import com.darkan.scripts.Script;

import kraken.plugin.api.Client;
import kraken.plugin.api.ImGui;
import kraken.plugin.api.Time;

@Script("Anachronia Agility")
public class AnachroniaAgility extends LoopScript {
	
	public static final int PAGE_ID = 47925, ENHANCED_EXCALIBUR = 14632, AUGMENTED_ENHANCED_EXCALIBUR = 36619;
	
	private int startPages;
	private AgilityNode currNode = null;
	private boolean reverse = true;

	public AnachroniaAgility() {
		super("Anachronia Agility", 200);
	}
	
	@Override
	public boolean onStart() {
		setState("Starting up...");
		startPages = Interfaces.getInventory().count(PAGE_ID);
		if (currNode == null) {
			for (AgilityNode node : AgilityNode.values()) {
				setState("Checking if "+node+" is good to start at...");
				if (node.getArea().inside(MyPlayer.getPosition())) {
					if (node == getEnd())
						reverse = true;
					currNode = node;
					return true;
				}
			}
			return false;
		}
		return true;
	}

	@Override
	public void loop() {
		AgilityNode next = getNext();
		if (MyPlayer.getHealthPerc() < 10) {
			sleep(1600);
			return;
		} else if (MyPlayer.getHealthPerc() < 40) {
			Item excal = Interfaces.getEquipment().getItemById(ENHANCED_EXCALIBUR, AUGMENTED_ENHANCED_EXCALIBUR);
			if (excal == null)
				excal = Interfaces.getInventory().getItemById(ENHANCED_EXCALIBUR, AUGMENTED_ENHANCED_EXCALIBUR);
			if (excal != null) {
				excal.click("Activate");
				sleep(1600);
				return;
			}
		}
		if (currNode.getArea().inside(MyPlayer.getPosition())) {
			setState("Moving to " + currNode.name() + "...");
			if (reverse) {
				if (next == getEnd())
					return;
				WorldObject nextObj = next.getReverseObj() != null ? next.getReverseObj() : next.getObject();
				if (nextObj.interact(0, false))
					sleepWhile(1000, Utils.gaussian(10000, 5000), () -> MyPlayer.get().isMoving() || MyPlayer.get().isAnimationPlaying());
			} else {
				if (currNode == getEnd())
					return;
				currNode.getObject().interact(0, false);
				sleepWhile(1000, Utils.gaussian(10000, 5000), () -> MyPlayer.get().isMoving() || MyPlayer.get().isAnimationPlaying());
			}
		} else {
			setState("Checking if we should move to "+next+"...");
			if (next.getArea().inside(MyPlayer.getPosition()))
				currNode = next;
		}
	}
	
	public AgilityNode getNext() {
		AgilityNode[] nodes = AgilityNode.values();
		
		if (reverse) {
			if (currNode.ordinal()-1 < getStart().ordinal()) {
				reverse = false;
				return getStart();
			}
			return nodes[currNode.ordinal()-1];
		}
		
		if (currNode.ordinal() == getEnd().ordinal()) {
			reverse = true;
			return getEnd();
		}
		return nodes[currNode.ordinal()+1];
	}
	
	public AgilityNode getStart() {
		if (Client.getStatById(Client.AGILITY).getMax() >= 85)
			return AgilityNode.END;
		if (Client.getStatById(Client.AGILITY).getMax() >= 70)
			return AgilityNode.CLIFF_3;
		if (Client.getStatById(Client.AGILITY).getMax() >= 50)
			return AgilityNode.RUINED_TEMP3;
		return AgilityNode.CLIFF;
	}
	
	public AgilityNode getEnd() {
		if (Client.getStatById(Client.AGILITY).getMax() >= 85)
			return AgilityNode.END;
		if (Client.getStatById(Client.AGILITY).getMax() >= 70)
			return AgilityNode.VINE3;
		if (Client.getStatById(Client.AGILITY).getMax() >= 50)
			return AgilityNode.CLIFF_3;
		return AgilityNode.RUINED_TEMP3;
	}

	@Override
	public void paintOverlay(long runtime) {
		
	}
	
	@Override
	public void paintImGui(long runtime) {
		ImGui.label("Current obstacle: " + (currNode != null ? currNode.name() : "None"));
		ImGui.label("Direction: " + (reverse ? "counter-clockwise" : "clockwise"));
		ImGui.label("Pages p/h: " + Time.perHour(runtime, Interfaces.getInventory().count(PAGE_ID) - startPages));
		ImGui.label("HP Perc: " + MyPlayer.getHealthPerc());
		printGenericXpGain(runtime);
	}

	@Override
	public void onStop() {
		
	}

}
