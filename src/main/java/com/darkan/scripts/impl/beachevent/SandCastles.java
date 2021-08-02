package com.darkan.scripts.impl.beachevent;

import java.util.HashMap;
import java.util.Map;

import com.darkan.api.accessors.NPCs;
import com.darkan.api.accessors.WorldObjects;
import com.darkan.api.util.Utils;
import com.darkan.cache.def.npcs.NPCDef;
import com.darkan.scripts.LoopScript;

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
	public void loop(LoopScript ctx) {
		for (int npcId : SAND_CASTLE_MAP.keySet()) {
			if (NPCs.getClosest(n -> npcId == n.getId()) != null) {
				if (WorldObjects.interactClosestReachable("Build", o -> o.getName().equals(SAND_CASTLE_MAP.get(npcId)))) {
					currentNpc = npcId;
					ctx.setState("Helping " + NPCDef.get(npcId).name + " build the " + SAND_CASTLE_MAP.get(npcId) + "...");
					ctx.sleepWhile(3500, Integer.MAX_VALUE, () -> ctx.getTimeSinceLastAnimation() < Utils.gaussian(3000, 2500) && NPCs.getClosest(n -> currentNpc == n.getId()) != null);
					break;
				}
			}
		}
	}

}
