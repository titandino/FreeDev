package com.darkan.cache.def.enums;

import java.util.Map;

import com.darkan.cache.def.vars.ScriptVarType;

import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;

public class EnumDef {
	public ScriptVarType keyType = ScriptVarType.INT;
	public ScriptVarType valueType = ScriptVarType.INT;
	public int defaultInt = 0;
	public String defaultString = null;
	public Map<Integer, Object> values = new Int2ObjectAVLTreeMap<>();
}
