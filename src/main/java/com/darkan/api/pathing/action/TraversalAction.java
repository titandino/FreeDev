package com.darkan.api.pathing.action;

import java.util.function.Supplier;

import com.darkan.api.pathing.action.node.TraversalNode;
import com.darkan.api.pathing.action.node.TraversalNodeList;

public class TraversalAction {
	
	private Supplier<Boolean> finishedCondition;
	private TraversalNodeList nodes;
	private TraversalNode curr;
	
	public TraversalAction(Supplier<Boolean> finishedCondition, TraversalNodeList nodes) {
		this.finishedCondition = finishedCondition;
		this.nodes = nodes.copy();
		this.curr = this.nodes.getHead();
	}

	public boolean process() {
		if (finishedCondition.get())
			return false;
		if (curr.process())
			return true;
		else if (curr.getNext() != null) {
			curr = curr.getNext();
			return true;
		} else
			return false;
	}

	public TraversalNodeList getNodes() {
		return nodes;
	}
}
