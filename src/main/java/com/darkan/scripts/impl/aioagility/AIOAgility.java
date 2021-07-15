package com.darkan.scripts.impl.aioagility;

import com.darkan.api.accessors.WorldObjects;
import com.darkan.scripts.Script;
import com.darkan.scripts.ScriptSkeleton;

import kraken.plugin.api.ImGui;
import kraken.plugin.api.Player;

@Script("AIO Agility")
public class AIOAgility extends ScriptSkeleton {
	private AgilityNode[] course = Courses.BURTHORPE;
	private int currNodeIdx = -1;

	public AIOAgility() {
		super("AIO Agility", 300);
	}
	
	@Override
	public boolean onStart(Player self) {
		setState("Starting up...");
		if (currNodeIdx == -1) {
			for (int i = 0;i < course.length;i++) {
				setState("Checking if "+course[i]+" is good to start at...");
				if (course[i].getArea().inside(self.getGlobalPosition())) {
					currNodeIdx = i;
					return true;
				}
			}
			return false;
		}
		return true;
	}

	@Override
	public void loop(Player self) {
		int nextIdx = getNext();
		if (course[currNodeIdx].getArea().inside(self.getGlobalPosition())) {
			if (WorldObjects.getClosest(obj -> obj.getId() == course[currNodeIdx].getObjectId()).interact(0)) {
				setState("Using " + course[currNodeIdx].getName() + "...");
				sleepWhile(2500, 25000, () -> self.isMoving() || self.isAnimationPlaying());
			}
		} else {
			setState("Checking if we should move to "+course[nextIdx].getName()+"...");
			if (course[nextIdx].getArea().inside(self.getGlobalPosition()))
				currNodeIdx = nextIdx;
		}
	}
	
	public int getNext() {
		if (currNodeIdx == course.length-1)
			return 0;
		return currNodeIdx + 1;
	}

	@Override
	public void paintOverlay(long runtime) {
		
	}
	
	@Override
	public void paintImGui(long runtime) {
		ImGui.label("Current obstacle: " + (currNodeIdx != -1 ? course[currNodeIdx].getName() : "None"));
		printGenericXpGain(runtime);
	}

	@Override
	public void onStop() {
		
	}

}
