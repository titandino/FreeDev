package com.darkan.cache.banditupsetversion.dto;

import java.util.HashMap;
import java.util.Map;

public class ObjectInfo {
	public int id;
	public String name = "";
	public String[] options = new String[5];
	public int accessBlockFlag;
	public int sizeX = 1, sizeY = 1;
	public Map<Integer, Object> params = new HashMap<>();
	
	public ObjectInfo() {
		
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
	
	public int getOption(String action) {
		if (options == null)
			return -1;
		for (int i = 0;i < options.length;i++) {
			if (options[i] != null && options[i].equalsIgnoreCase(action))
				return i;
		}
		return -1;
	}
	
	//What a shame not everyone has access to this information.
	
//	public ObjectType[] types;
//	public int[][] modelIds;
//	public int clipType;
//	public boolean blocks;
//	public int interactable;
//	public byte groundContoured;
//	public boolean delayShading;
//	public int occludes;
//	public int[] animations;
//	public int decorDisplacement;
//	public byte ambient;
//	public byte contrast;
//	public short[] originalColors;
//	public short[] modifiedColors;
//	public short[] originalTextures;
//	public short[] modifiedTextures;
//	public boolean inverted;
//	public boolean castsShadow;
//	public int scaleX;
//	public int scaleY;
//	public int scaleZ;
//	public int offsetX;
//	public int offsetY;
//	public int offsetZ;
//	public boolean obstructsGround;
//	public int supportsItems;
//	public boolean ignoreAltClip;
//	public int varpBit = -1;
//	public int varp = -1;
//	public int[] transformTo;
//	public int ambientSoundId;
//	public int ambientSoundHearDistance;
//	public int[] audioTracks;
//	public boolean hidden;
//	public boolean members;
//	public boolean adjustMapSceneRotation;
//	public boolean hasAnimation = false;
//	public int mapSpriteRotation;
//	public int mapSpriteId;
//	public int ambientSoundVolume;
//	public boolean flipMapSprite;
//	public int[] animProbs;
//	public int mapIcon;
//	public int[] actionCursors;
}
