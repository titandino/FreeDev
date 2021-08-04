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
