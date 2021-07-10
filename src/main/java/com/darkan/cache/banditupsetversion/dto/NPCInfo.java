package com.darkan.cache.banditupsetversion.dto;

import java.util.HashMap;
import java.util.Map;

public class NPCInfo {
	public int id;
	public String name = "";
	public byte size = 1;
	public String[] options;
	public Map<Integer, Object> params = new HashMap<>();
	
	public NPCInfo() {
		
	}

	public int getOption(String action) {
		if (options == null)
			return -1;
		for (int i = 0;i < options.length;i++) {
			if (options[i] != null && options[i].equalsIgnoreCase(action))
				return i;
		}
		return -1;
	}
	
	public boolean hasOption(String option) {
		if (options == null)
			return false;
		for (String o : options) {
			if (o == null || !o.equalsIgnoreCase(option))
				continue;
			return true;
		}
		return false;
	}
	
	//What a shame not everyone has access to this information.
	
//	public int[] modelIds;
//	public short[] originalColors;
//	public short[] modifiedColors;
//	public short[] originalTextures;
//	public short[] modifiedTextures;
//	public byte[] recolourPalette;
//	public int[] headModels;
//	public boolean drawMapdot;
//	public int combatLevel;
//	public int resizeX;
//	public int resizeY;
//	public byte ambient;
//	public byte contrast;
//	public Map<Integer, Integer> headIcons;
//	public int rotation;
//	public int varpBit;
//	public int varp;
//	public int[] transformTo;
//	public boolean visible = true;
//	public boolean isClickable = true;
//	public boolean animateIdle = true;
//	public byte walkMask;
//	public int[][] modelTranslation;
//	public int height;
//	public byte respawnDirection;
//	public int basId;
//	public MovementType movementType;
//	public int walkingAnimation;
//	public int rotate180Animation;
//	public int rotate90RightAnimation;
//	public int rotate90LeftAnimation;
//	public int specialByte;
//	public int attackOpCursor;
//	public int armyIcon;
//	public int mapIcon;
//	public String[] membersOptions = new String[5];
//	public int[] quests;
//	public int[] actionCursors;

}
