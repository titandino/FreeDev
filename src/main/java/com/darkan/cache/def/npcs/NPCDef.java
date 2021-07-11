package com.darkan.cache.def.npcs;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

import com.darkan.api.entity.VarManager;
import com.darkan.cache.Cache;
import com.darkan.cache.def.Def;
import com.darkan.cache.def.npcs.NPCDefParser.MovementType;
import com.darkan.cache.def.params.Params;
import com.darkan.cache.util.Utils;

public class NPCDef extends Def {
	
	private static NPCDefParser PARSER = new NPCDefParser();
	
	public static NPCDefParser getParser() {
		return PARSER;
	}
	
	public static NPCDef get(int id) {
		return PARSER.get(Cache.get(), id);
	}
	

	public static NPCDef get(int id, VarManager vars) {
		NPCDef def = get(id);
		if (def != null)
			def = NPCDef.get(def.getIdForPlayer(vars));
		return def;
	}
	
	public int getIdForPlayer(VarManager vars) {
		if (transformTo == null || transformTo.length == 0)
			return id;
		if (vars == null) {
			int varIdx = transformTo[transformTo.length - 1];
			return varIdx;
		}
		int index = -1;
		if (varpBit != -1) {
			index = vars.getVarBit(varpBit);
		} else if (varp != -1) {
			index = vars.getVar(varp);
		}
		if (index >= 0 && index < transformTo.length - 1 && transformTo[index] != -1) {
			return transformTo[index];
		} else {
			int varIdx = transformTo[transformTo.length - 1];
			return varIdx;
		}
	}

	public Params params = new Params();
	public int[] modelIds;
	public String name = "";
	public int size = 1;
	public String[] options = new String[5];
	public short[] originalColors;
	public short[] modifiedColors;
	public short[] originalTextures;
	public short[] modifiedTextures;
	public byte[] recolourPalette;
	public int[] headModels;
	public boolean drawMapdot;
	public int combatLevel;
	public int resizeX;
	public int resizeY;
	public boolean aBool4904;
	public byte ambient;
	public byte contrast;
	public Map<Integer, Integer> headIcons;
	public int rotation;
	public int varpBit;
	public int varp;
	public int[] transformTo;
	public boolean visible = true;
	public boolean isClickable = true;
	public boolean animateIdle = true;
	public int aShort4874;
	public int aShort4897;
	public byte aByte4883;
	public byte aByte4899;
	public byte walkMask;
	public int[][] modelTranslation;
	public int height;
	public byte respawnDirection;
	public int basId;
	public MovementType movementType;
	public int walkingAnimation;
	public int rotate180Animation;
	public int rotate90RightAnimation;
	public int rotate90LeftAnimation;
	public int specialByte;
	public int anInt4875;
	public int anInt4873;
	public int anInt4854;
	public int anInt4861;
	public int attackOpCursor;
	public int armyIcon;
	public int anInt4909;
	public boolean aBool4884;
	public int mapIcon;
	public boolean aBool4890;
	public String[] membersOptions = new String[5];
	public byte aByte4868;
	public byte aByte4869;
	public byte aByte4905;
	public byte aByte4871;
	public int aByte4916;
	public int[] quests;
	public boolean aBool4872;
	public int anInt4917;
	public int anInt4911;
	public int anInt4919;
	public int anInt4913;
	public int anInt4908;
	public boolean aBool4920 = true;
	public int[] actionCursors;
	
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
}
