package com.darkan.cache.def.items;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.darkan.cache.Cache;
import com.darkan.cache.def.params.Params;
import com.darkan.cache.util.Utils;

public class ItemDef {
	public int groundModel;
	public String name;
	public String examine;
	public int modelZoom;
	public int modelAngleX;
	public int modelAngleY;
	public int modelOffsetX;
	public int modelOffsetY;
	public byte stackable;
	public int price;
	public byte equipmentSlot;
	public byte equipmentType;
	public boolean op15Bool;
	public boolean members;
	public int unused;
	public int maleModel1;
	public int maleModel2;
	public int femaleModel1;
	public int femaleModel2;
	public byte equipmentType2;
	public String[] groundActions = new String[5];
	public String[] inventoryActions = new String[5];
	public short[] originalColors;
	public short[] replacementColors;
	public short[] originalTextures;
	public short[] replacementTextures;
	public byte[] recolorPalette;
	public boolean stockMarket = false;
	public int maleModel3;
	public int femaleModel3;
	public int maleHeadModel1;
	public int femaleHeadModel1;
	public int maleHeadModel2;
	public int femaleHeadModel2;
	public int modelAngleZ;
	public byte searchable;
	public int notedItemId;
	public int notedTemplate;
	public int[] stackAmounts;
	public int[] stackIds;
	public int resizeX;
	public int resizeY;
	public int resizeZ;
	public byte ambient;
	public int contrast;
	public byte teamId;
	public int lentItemId;
	public int lendTemplate;
	public int maleModelOffsetX;
	public int maleModelOffsetY;
	public int maleModelOffsetZ;
	public int femaleModelOffsetX;
	public int femaleModelOffsetY;
	public int femaleModelOffsetZ;
	public int[] questIds;
	public int anInt1919;
	public int boughtItemId;
	public int boughtTemplate;
	public int[] groundCursors;
	public int[] inventoryCursors;
	public int shardItemId;
	public int shardTemplateId;
	public Params params = new Params();
	public String buffEffect;
	public int geBuyLimit;
	public short category;
	public boolean randomizeGroundPos = false;
	public short shardCombineAmount;
	public String shardName;
	public boolean neverStackable = false;
	
	private static ItemDefParser PARSER = new ItemDefParser();
	
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
				System.out.println(ex);
			}
			result.append(newLine);
		}
		result.append("}");

		return result.toString();
	}
}
