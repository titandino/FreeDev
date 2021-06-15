package com.darkan.plugins.anachroniaagility;

import com.darkan.plugins.PluginSkeleton;

import kraken.plugin.api.Actions;
import kraken.plugin.api.Client;
import kraken.plugin.api.ImGui;
import kraken.plugin.api.Inventory;
import kraken.plugin.api.Player;
import kraken.plugin.api.Time;

public class AnachroniaAgility extends PluginSkeleton {
	
	private static final int PAGE_ID = 47925;
	
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
		startPages = Inventory.count(PAGE_ID);
		if (currNode == null) {
			for (AgilityNode node : AgilityNode.values()) {
				setState("Checking if "+node+" is good to start at...");
				if (node.getArea().inside(self.getGlobalPosition())) {
					if (node == AgilityNode.END)
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
				if (next == AgilityNode.END)
					return;
				if (next.getReverseObj() != null)
					next.getReverseObj().interact(Actions.MENU_EXECUTE_OBJECT1);
				else
					next.getObject().interact(Actions.MENU_EXECUTE_OBJECT1);
			} else {
				if (currNode == AgilityNode.END)
					return;
				currNode.getObject().interact(Actions.MENU_EXECUTE_OBJECT1);
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
		
		if (currNode.ordinal() == AgilityNode.END.ordinal()) {
			reverse = true;
			return AgilityNode.END;
		}
		return nodes[currNode.ordinal()+1];
	}

	@Override
	public void paint(long runtime) {
		ImGui.label("Current obstacle: " + currNode.name());
		ImGui.label("Direction: " + (reverse ? "counter-clockwise" : "clockwise"));
		ImGui.label("Pages p/h: " + Time.perHour(runtime, Inventory.count(PAGE_ID) - startPages));
		ImGui.label("XP p/h: " + Time.perHour(runtime, Client.getStatById(Client.AGILITY).getXp() - startXp));
	}

}
