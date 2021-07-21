package com.darkan.scripts.impl.aiomining;

import com.darkan.api.pathing.action.node.TraversalNodeList;
import com.darkan.api.pathing.action.node.impl.ObjectNode;
import com.darkan.api.util.Area;

public enum OreData {
	Copper(43188, new TraversalNodeList(new ObjectNode(66876, "Enter", new Area(2290, 4513, 2295, 4520))), new TraversalNodeList(new ObjectNode(67002, "Exit to", new Area(2874, 3499, 2878, 3505)))),
	Tin(43190, new TraversalNodeList(new ObjectNode(66876, "Enter", new Area(2290, 4513, 2295, 4520))), new TraversalNodeList(new ObjectNode(67002, "Exit to", new Area(2874, 3499, 2878, 3505))));
	
	private int varbit;
	private TraversalNodeList fromBank;
	private TraversalNodeList toBank;
	
	private OreData(int varbit, TraversalNodeList fromBank, TraversalNodeList toBank) {
		this.varbit = varbit;
		this.fromBank = fromBank;
		this.toBank = toBank;
	}

	public int getVarbit() {
		return varbit;
	}

	public TraversalNodeList getToBank() {
		return toBank;
	}

	public TraversalNodeList getFromBank() {
		return fromBank;
	}
}
