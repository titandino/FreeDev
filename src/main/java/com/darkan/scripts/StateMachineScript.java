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
package com.darkan.scripts;

import com.darkan.api.inter.chat.Message;
import com.darkan.api.listeners.MessageListener;

import kraken.plugin.api.ImGui;
import kraken.plugin.api.Time;

public abstract class StateMachineScript extends LoopScript implements MessageListener {
	
	private State currState;

	public StateMachineScript(String name, int refreshRate) {
		super(name, refreshRate);
	}
	
	public StateMachineScript(String name) {
		this(name, 200);
	}

	@Override
	protected final void loop() {
		if (currState == null)
			currState = getStartState();
		State next = currState.checkNext();
		if (next != null) {
			currState = next;
			return;
		}
		currState.loop(this);
	}

	public abstract State getStartState();
	
	@Override
	public void onPaint() {
		long runtime = System.currentTimeMillis() - getStarted();
		ImGui.label(getName() + " - " + Time.formatTime(runtime));
		ImGui.label("Current sub-state: " + (currState == null ? "None" : currState.getClass().getSimpleName()));
		ImGui.label(getState());
		ImGui.label(getError());
		paintImGui(runtime);
	}
	
	@Override
	public void onMessageReceived(Message message) {
		if (currState != null && currState instanceof MessageListener)
			((MessageListener)currState).onMessageReceived(message);
	}

	public State getCurrState() {
		return currState;
	}

}
