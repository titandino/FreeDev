package com.darkan.cache.def.items;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.darkan.api.util.Utils;
import com.darkan.cache.Cache;
import com.darkan.cache.def.params.Params;

public class ItemDef {
	public int id;
	public int modelId;
	public String name = "";
	public String examine;
	public int modelZoom = 2000;
	public int modelRotationX;
	public int modelRotationY;
	public int modelOffsetX;
	public int modelOffsetY;
	public byte stackable;
	public int price = 1;
	public byte equipmentSlot = -1;
	public byte equipmentType = -1;
	public boolean op15Bool;
	public boolean members;
	public int unused;
	public int maleModel1 = -1;
	public int maleModel2 = -1;
	public int femaleModel1 = -1;
	public int femaleModel2 = -1;
	public byte equipmentType2 = -1;
	public String[] groundActions = new String[5];
	public String[] inventoryActions = new String[5];
	public short[] originalColors;
	public short[] replacementColors;
	public short[] originalTextures;
	public short[] replacementTextures;
	public byte[] recolorPalette;
	public boolean stockMarket = false;
	public int maleModel3 = -1;
	public int femaleModel3 = -1;
	public int maleHeadModel1 = -1;
	public int femaleHeadModel1 = -1;
	public int maleHeadModel2 = -1;
	public int femaleHeadModel2 = -1;
	public int modelAngleZ;
	public byte searchable;
	public int notedItemId = -1;
	public int notedTemplate = -1;
	public int[] stackAmounts;
	public int[] stackIds;
	public int resizeX;
	public int resizeY;
	public int resizeZ;
	public byte ambient;
	public int contrast;
	public byte teamId;
	public int lentItemId = -1;
	public int lendTemplate = -1;
	public int maleModelOffsetX;
	public int maleModelOffsetY;
	public int maleModelOffsetZ;
	public int femaleModelOffsetX;
	public int femaleModelOffsetY;
	public int femaleModelOffsetZ;
	public int[] questIds;
	public int pickSizeShift;
	public int bindId = -1;
	public int boundTemplate = -1;
	public int[] groundCursors;
	public int[] inventoryCursors;
	public int shardItemId = -1;
	public int shardTemplateId = -1;
	public Params params = new Params();
	public String buffEffect;
	public int geBuyLimit;
	public short category;
	public boolean randomizeGroundPos = false;
	public short shardCombineAmount;
	public String shardName;
	public boolean neverStackable = false;
	public boolean noted = false;
	public boolean lended = false;
	
	public void toNote() {
		ItemDef realItem = get(notedItemId);
		members = realItem.members;
		price = realItem.price;
		name = realItem.name;
		stackable = 1;
		noted = true;
	}

	public void toBind() {
		ItemDef realItem = get(bindId);
		originalColors = realItem.originalColors;
		maleModel3 = realItem.maleModel3;
		femaleModel3 = realItem.femaleModel3;
		teamId = realItem.teamId;
		price = 0;
		members = realItem.members;
		name = realItem.name;
		inventoryActions = new String[5];
		groundActions = realItem.groundActions;
		if (realItem.inventoryActions != null)
			for (int optionIndex = 0; optionIndex < 4; optionIndex++)
				inventoryActions[optionIndex] = realItem.inventoryActions[optionIndex];
		inventoryActions[4] = "Discard";
		maleModel1 = realItem.maleModel1;
		maleModel2 = realItem.maleModel2;
		femaleModel1 = realItem.femaleModel1;
		femaleModel2 = realItem.femaleModel2;
		params = realItem.params;
		equipmentSlot = realItem.equipmentSlot;
		equipmentType = realItem.equipmentType;
	}

	public void toLend() {
		ItemDef realItem = get(lentItemId);
		originalColors = realItem.originalColors;
		maleModel3 = realItem.maleModel3;
		femaleModel3 = realItem.femaleModel3;
		teamId = realItem.teamId;
		price = 0;
		members = realItem.members;
		name = realItem.name;
		inventoryActions = new String[5];
		groundActions = realItem.groundActions;
		if (realItem.inventoryActions != null)
			for (int optionIndex = 0; optionIndex < 4; optionIndex++)
				inventoryActions[optionIndex] = realItem.inventoryActions[optionIndex];
		inventoryActions[4] = "Discard";
		maleModel1 = realItem.maleModel1;
		maleModel2 = realItem.maleModel2;
		femaleModel1 = realItem.femaleModel1;
		femaleModel2 = realItem.femaleModel2;
		params = realItem.params;
		equipmentSlot = realItem.equipmentSlot;
		equipmentType = realItem.equipmentType;
		lended = true;
	}
	
	public int getInvOpIdForName(String opName) {
		for (int i = 0;i < 5;i++) {
			if (containsInvOp(i, opName))
				return i;
		}
		return -1;
	}
	
	public String getInvOp(int optionId) {
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
	
	public boolean containsInvOp(int i, String option) {
		if (inventoryActions == null || inventoryActions[i] == null || inventoryActions.length <= i)
			return false;
		return getInvOp(i).equalsIgnoreCase(option);
	}

	public boolean containsInvOp(String option) {
		if (inventoryActions == null)
			return false;
		for (String o : inventoryActions) {
			if (o == null || !o.equalsIgnoreCase(option))
				continue;
			return true;
		}
		return false;
	}
	
	public int getEquipOpIdForName(String opName) {
		for (int i = 0;i < 5;i++) {
			if (containsEquipOp(i, opName))
				return i;
		}
		return -1;
	}
	
	public String getEquipOp(int optionId) {
		if (params == null)
			return "null";
		Object wearingOption = params.get(optionId == 4 ? 1211 : (528 + optionId));
		if (wearingOption != null && wearingOption instanceof String)
			return (String) wearingOption;
		return "null";
	}
	
	public boolean containsEquipOp(String option) {
		if (params == null)
			return false;
		for (int i = 0;i < 5;i++)
			if (containsEquipOp(i, option))
				return true;
		return false;
	}
	
	public boolean containsEquipOp(int optionId, String option) {
		if (params == null)
			return false;
		String equipOp = getEquipOp(optionId);
		if (equipOp != null && !equipOp.equals("null") && equipOp.equalsIgnoreCase(option))
			return true;
		return false;
	}
	
	private static ItemDefParser PARSER = new ItemDefParser();
	
	public static ItemDefParser getParser() {
		return PARSER;
	}
	
	public static ItemDef get(int id) {
		return PARSER.get(Cache.get(), id);
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		String newLine = System.getProperty("line.separator");

		result.append(this.getClass().getName());
		result.append(" {");
		result.append(newLine);

		// determine fields declared in this class only (no fields of
		// superclass)
		Field[] fields = this.getClass().getDeclaredFields();

		// print field names paired with their values
		for (Field field : fields) {
			if (Modifier.isStatic(field.getModifiers()))
				continue;
			result.append("  ");
			try {
				result.append(field.getType().getCanonicalName() + " " + field.getName() + ": ");
				result.append(Utils.getFieldValue(this, field));
			} catch (Throwable ex) {
				ex.printStackTrace();
			}
			result.append(newLine);
		}
		result.append("}");

		return result.toString();
	}
}
