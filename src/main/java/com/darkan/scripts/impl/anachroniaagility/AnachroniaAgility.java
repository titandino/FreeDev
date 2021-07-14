package com.darkan.scripts.anachroniaagility;

import com.darkan.api.entity.MyPlayer;
import com.darkan.api.inter.Interfaces;
import com.darkan.api.item.Item;
import com.darkan.scripts.Script;
import com.darkan.scripts.ScriptSkeleton;

import kraken.plugin.api.Client;
import kraken.plugin.api.ImGui;
import kraken.plugin.api.Player;
import kraken.plugin.api.Time;

@Script("Anachronia Agility")
public class AnachroniaAgility extends ScriptSkeleton {
	
	public static final int PAGE_ID = 47925, ENHANCED_EXCALIBUR = 14632, AUGMENTED_ENHANCED_EXCALIBUR = 36619;
	
	private int startXp;
	private int startPages;
	private AgilityNode currNode = null;
	private boolean reverse = true;

	public AnachroniaAgility() {
		super("Anachronia Agility", 600);
	}
	
	@Override
	public boolean onStart(Player self) {
		setState("Starting up...");
		startXp = Client.getStatById(Client.AGILITY).getXp();
		startPages = Interfaces.getInventory().count(PAGE_ID);
		if (currNode == null) {
			for (AgilityNode node : AgilityNode.values()) {
				setState("Checking if "+node+" is good to start at...");
				if (node.getArea().inside(self.getGlobalPosition())) {
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
	public void loop(Player self) {
		if (self.isAnimationPlaying() || self.isMoving())
			return;
		AgilityNode next = getNext();
		if (currNode.getArea().inside(self.getGlobalPosition())) {
			setState("Moving to " + currNode.name() + "...");
			if (reverse) {
				if (next == getEnd())
					return;
				if (next.getReverseObj() != null)
					next.getReverseObj().interact(0);
				else
					next.getObject().interact(0);
			} else {
				if (currNode == getEnd())
					return;
				currNode.getObject().interact(0);
			}
			Item excal = Interfaces.getEquipment().getItemById(ENHANCED_EXCALIBUR, AUGMENTED_ENHANCED_EXCALIBUR);
			if (excal == null)
				excal = Interfaces.getInventory().getItemById(ENHANCED_EXCALIBUR, AUGMENTED_ENHANCED_EXCALIBUR);
			if (excal != null && MyPlayer.getHealthPerc() < 40) {
				excal.click("Activate");
				sleep(5000);
			}
			sleep(2500);
		} else {
			setState("Checking if we should move to "+next+"...");
			if (next.getArea().inside(self.getGlobalPosition()))
				currNode = next;
		}
	}
	
	public AgilityNode getNext() {
		AgilityNode[] nodes = AgilityNode.values();
		
		if (reverse) {
			if (currNode.ordinal()-1 < 0) {
				reverse = false;
				return AgilityNode.CLIFF;
			}
			return nodes[currNode.ordinal()-1];
		}
		
		if (currNode.ordinal() == getEnd().ordinal()) {
			reverse = true;
			return getEnd();
		}
		return nodes[currNode.ordinal()+1];
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
		ImGui.label("Current obstacle: " + currNode != null ? currNode.name() : "None");
		ImGui.label("Direction: " + (reverse ? "counter-clockwise" : "clockwise"));
		ImGui.label("Pages p/h: " + Time.perHour(runtime, Interfaces.getInventory().count(PAGE_ID) - startPages));
		ImGui.label("XP p/h: " + Time.perHour(runtime, Client.getStatById(Client.AGILITY).getXp() - startXp));
	}

	@Override
	public void onStop() {
		
	}

}
