package com.darkan.cache.def.enums;

import java.util.Map;

import com.darkan.Constants;
import com.darkan.cache.Cache;
import com.darkan.cache.def.structs.StructDef;
import com.darkan.cache.def.vars.ScriptVarType;
import com.darkan.kraken.world.WorldTile;

import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;

public class EnumDef {
	public ScriptVarType keyType = ScriptVarType.INT;
	public ScriptVarType valueType = ScriptVarType.INT;
	public int defaultInt = 0;
	public String defaultString = null;
	public Map<Integer, Object> values = new Int2ObjectAVLTreeMap<>();
	
	private static EnumDefParser PARSER = new EnumDefParser();
	
	public static EnumDef get(int id) {
		return PARSER.get(Cache.get(), id);
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		if (values == null)
			return "null";
		s.append("<"+keyType+", "+valueType+"> - " + defaultString + " - " + defaultInt + " { ");
		s.append("\r\n");
		for (Integer l : values.keySet()) {
			s.append(keyToType(l));
			s.append(" = ");
			s.append(valToType(values.get(l)));
			s.append("\r\n");
		}
		s.append("} \r\n");
		return s.toString();
	}
	
	public Object keyToType(long l) {
		if (keyType == ScriptVarType.INTERFACE) {
			long interfaceId = l >> 16;
			long componentId = l - (interfaceId << 16);
			return "IComponent("+interfaceId+", "+componentId+")";
		} else if (keyType == ScriptVarType.WORLDTILE) {
			return new WorldTile((int) l);
		} else if (keyType == ScriptVarType.STAT) {
			int idx = (int) l;
			if (idx >= Constants.SKILL_NAME.length)
				return l;
			return idx+"("+Constants.SKILL_NAME[((int) l)]+")";
		} else if (keyType == ScriptVarType.ITEM) {
			return l;// + "("+ItemDefinitions.getDefs((int) l).getName()+")"; 
		} else if (keyType == ScriptVarType.NPC) {
			return l;// + "("+NPCDefinitions.getDefs((int) l).getName()+")"; 
		} else if (keyType == ScriptVarType.STRUCT) {
			return l + ": " + StructDef.get((int) l); 
		}
		return l;
	}
	
	public Object valToType(Object o) {
		if (valueType == ScriptVarType.INTERFACE) {
			if (o instanceof String)
				return o;
			long interfaceId = ((int) o) >> 16;
			long componentId = ((int) o) - (interfaceId << 16);
			return "IComponent("+interfaceId+", "+componentId+")";
		} else if (valueType == ScriptVarType.WORLDTILE) {
			if (o instanceof String)
				return o;
			return new WorldTile(((int) o));
		} else if (valueType == ScriptVarType.STAT) {
			if (o instanceof String)
				return o;
			int idx = (int) o;
			if (idx >= Constants.SKILL_NAME.length)
				return o;
			return idx+"("+Constants.SKILL_NAME[((int) o)]+")";
		} else if (valueType == ScriptVarType.ITEM) {
			if (o instanceof String)
				return o;
			return o;
			//return ((int) o)+" ("+ItemDefinitions.getDefs(((int) o)).getName()+")"; 
		} else if (valueType == ScriptVarType.NPC) {
			if (o instanceof String)
				return o;
			return o;
			//return ((int) o)+" ("+NPCDefinitions.getDefs(((int) o)).getName()+")"; 
		} else if (valueType == ScriptVarType.STRUCT) {
			return o + ": " + StructDef.get((int) o);
		} else if (valueType == ScriptVarType.ENUM) {
			return o + ": " + get((int) o);
		}
		return o;
	}
}
