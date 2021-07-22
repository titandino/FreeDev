package com.darkan.api.pathing.action;

import java.util.function.Supplier;

import com.darkan.api.pathing.FixedTileStrategy;
import com.darkan.api.pathing.Pathing;
import com.darkan.api.pathing.action.node.TraversalNode;
import com.darkan.api.pathing.action.node.TraversalNodeList;
import com.darkan.api.world.WorldTile;

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
	
	public static void main(String[] args) {
		System.out.println(Pathing.findRoute(new WorldTile(3187, 3425, 0), 1, new FixedTileStrategy(3182, 3371), false)); //TODO extend range of findroute
	}
}
