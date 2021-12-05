// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
//
//  Copyright © 2021 Trenton Kress
//  This file is part of project: FreeDev
//
package com.darkan.api.pathing.action;

import java.util.List;
import java.util.function.Supplier;

import com.darkan.api.pathing.FixedTileStrategy;
import com.darkan.api.pathing.LocalPathing;
import com.darkan.api.pathing.action.node.TraversalNode;
import com.darkan.api.pathing.action.node.TraversalNodeList;
import com.darkan.api.world.WorldTile;

public class TraversalProcessor {
	
	private Supplier<Boolean> finishedCondition;
	private TraversalNodeList nodes;
	private TraversalNode curr;
	
	public TraversalProcessor(Supplier<Boolean> finishedCondition, TraversalNodeList nodes) {
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
		List<WorldTile> path = LocalPathing.findLocalRoute(new WorldTile(3187, 3425, 0), 1, new FixedTileStrategy(new WorldTile(3182, 3371, 0)), false);
		for (WorldTile tile : path)
			System.out.println(tile);
	}
}
