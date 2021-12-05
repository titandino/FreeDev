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
package com.darkan.api.pathing.action.node;

public class TraversalNodeList {
	
	private TraversalNode head;
	private TraversalNode curr;

	public TraversalNodeList(TraversalNode start) {
		this.head = start;
		this.curr = start;
	}
	
	public TraversalNodeList add(TraversalNode node) {
		this.curr.setNext(node);
		node.setPrev(this.curr);
		this.curr = node;
		return this;
	}

	public TraversalNode getHead() {
		return head;
	}
	
	public TraversalNodeList copy() {
		TraversalNodeList copy = new TraversalNodeList(this.head.copy());
		copy.curr = copy.head;
		while(copy.curr.next != null) {
			copy.curr.next = copy.curr.next.copy();
			copy.curr = copy.curr.next;
		}
		copy.curr = copy.head;
		return copy;
	}
}
