package com.darkan.cache.banditupsetversion.dto;

import java.util.HashMap;
import java.util.Map;

public class ItemInfo {

	public int id;
	public String name = "";
	public String[] groundActions = new String[5];
	public String[] inventoryActions = new String[5];
	public Map<Integer, Object> params = new HashMap<>();
	
	public ItemInfo() {
		
	}
	
	public boolean containsEquipmentOption(int optionId, String option) {
		if (params == null)
			return false;
		Object wearingOption = params.get(528 + optionId);
		if (wearingOption != null && wearingOption instanceof String)
			return wearingOption.equals(option);
		return false;
	}

	public String getEquipmentOption(int optionId) {
		if (params == null)
			return "null";
		Object wearingOption = params.get(optionId == 4 ? 1211 : (528 + optionId));
		if (wearingOption != null && wearingOption instanceof String)
			return (String) wearingOption;
		return "null";
	}
	
	public String getInventoryOption(int optionId) {
		switch(id) {
		case 6099:
		case 6100:
		case 6101:
		case 6102:
			if (optionId == 2)
				return "Temple";
			break;
		case 19760:
		case 13561:
		case 13562:
			if (optionId == 0)
				return inventoryActions[1];
			else if (optionId == 1)
				return inventoryActions[0];
			break;
		}
		if (inventoryActions == null)
			return "null";
		if (optionId >= inventoryActions.length)
			return "null";
		if (inventoryActions[optionId] == null)
			return "null";
		return inventoryActions[optionId];
	}
	
	public boolean containsOption(int i, String option) {
		if (inventoryActions == null || inventoryActions[i] == null || inventoryActions.length <= i)
			return false;
		return inventoryActions[i].equals(option);
	}

	public boolean containsOption(String option) {
		if (inventoryActions == null)
			return false;
		for (String o : inventoryActions) {
			if (o == null || !o.equals(option))
				continue;
			return true;
		}
		return false;
	}
	
	//What a shame not everyone has access to this information realtime.
	
//	public int modelId;
//	public String examine;
//	public int modelZoom = 2000;
//	public int modelRotationX;
//	public int modelRotationY;
//	public int modelOffsetX;
//	public int modelOffsetY;
//	public byte stackable;
//	public int price = 1;
//	public byte equipmentSlot = -1;
//	public byte equipmentType = -1;
//	public boolean members;
//	public int unused;
//	public int maleModel1 = -1;
//	public int maleModel2 = -1;
//	public int femaleModel1 = -1;
//	public int femaleModel2 = -1;
//	public byte equipmentType2 = -1;
//	public short[] originalColors;
//	public short[] replacementColors;
//	public short[] originalTextures;
//	public short[] replacementTextures;
//	public byte[] recolorPalette;
//	public boolean stockMarket = false;
//	public int maleModel3 = -1;
//	public int femaleModel3 = -1;
//	public int maleHeadModel1 = -1;
//	public int femaleHeadModel1 = -1;
//	public int maleHeadModel2 = -1;
//	public int femaleHeadModel2 = -1;
//	public int modelAngleZ;
//	public byte searchable;
//	public int notedItemId = -1;
//	public int notedTemplate = -1;
//	public int[] stackAmounts;
//	public int[] stackIds;
//	public int resizeX;
//	public int resizeY;
//	public int resizeZ;
//	public byte ambient;
//	public int contrast;
//	public byte teamId;
//	public int lentItemId = -1;
//	public int lendTemplate = -1;
//	public int maleModelOffsetX;
//	public int maleModelOffsetY;
//	public int maleModelOffsetZ;
//	public int femaleModelOffsetX;
//	public int femaleModelOffsetY;
//	public int femaleModelOffsetZ;
//	public int[] questIds;
//	public int pickSizeShift;
//	public int bindId = -1;
//	public int boundTemplate = -1;
//	public int[] groundCursors;
//	public int[] inventoryCursors;
//	public int shardItemId = -1;
//	public int shardTemplateId = -1;
//	public String buffEffect;
//	public int geBuyLimit;
//	public short category;
//	public boolean randomizeGroundPos = false;
//	public short shardCombineAmount;
//	public String shardName;
//	public boolean neverStackable = false;
//	public boolean noted = false;
//	public boolean lended = false;
}
