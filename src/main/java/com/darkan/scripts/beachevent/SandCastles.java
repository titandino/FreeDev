package com.darkan.scripts.beachevent;

import java.util.HashMap;
import java.util.Map;

import com.darkan.api.accessors.NPCs;
import com.darkan.api.accessors.WorldObjects;
import com.darkan.scripts.ScriptSkeleton;

import kraken.plugin.api.Player;

public class SandCastles extends BeachActivity {

	private static Map<Integer, String> SAND_CASTLE_MAP = new HashMap<>();
	
	static {
		SAND_CASTLE_MAP.put(21164, "Wizards' Sandtower");
		SAND_CASTLE_MAP.put(21165, "Sand Exchange");
		SAND_CASTLE_MAP.put(21166, "Sand Pyramid");
		SAND_CASTLE_MAP.put(21167, "Lumbridge Sandcastle");
	}
	
	private int currentNpc;
	
	@Override
	public void loop(ScriptSkeleton ctx, Player self) {
		for (int npcId : SAND_CASTLE_MAP.keySet()) {
			if (NPCs.getClosest(n -> npcId == n.getId()) != null) {
				if (WorldObjects.interactClosestReachable("Build", o -> o.getName().equals(SAND_CASTLE_MAP.get(npcId)))) {
					currentNpc = npcId;
					ctx.sleepWhile(3500, 50000, () -> self.isAnimationPlaying() && NPCs.getClosest(n -> currentNpc == n.getId()) != null);
					break;
				}
			}
		}
	}

}
