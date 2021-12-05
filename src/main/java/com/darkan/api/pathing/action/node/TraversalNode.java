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

public abstract class TraversalNode {
	
	protected TraversalNode prev;
	protected TraversalNode next;
	
	public abstract boolean canStart();
	public abstract boolean process();
	public abstract boolean reached();
	public abstract TraversalNode copy();
	
	public TraversalNode getPrev() {
		return prev;
	}
	
	public void setPrev(TraversalNode prev) {
		this.prev = prev;
	}
	
	public TraversalNode getNext() {
		return next;
	}
	
	public void setNext(TraversalNode next) {
		this.next = next;
	}
	
}
