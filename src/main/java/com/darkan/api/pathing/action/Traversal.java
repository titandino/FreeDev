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

import java.util.function.Supplier;

import com.darkan.api.pathing.action.node.TraversalNodeList;
import com.darkan.scripts.State;
import com.darkan.scripts.StateMachineScript;

public class Traversal extends State {
	
	private State next;
	private TraversalProcessor processor;
	
	public Traversal(State next, Supplier<Boolean> finishedCondition, TraversalNodeList nodes) {
		this.next = next;
		this.processor = new TraversalProcessor(finishedCondition, nodes);
	}

	@Override
	public State checkNext() {
		if (!processor.process())
			return next;
		return null;
	}

	@Override
	public void loop(StateMachineScript ctx) {

	}

}
