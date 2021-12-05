// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
//
//  Copyright © 2021 Trenton Kress
//  This file is part of project: FreeDev
//
package com.darkan.cache.def.enums;

import java.util.HashMap;
import java.util.Map;

import com.darkan.Constants;
import com.darkan.api.world.WorldTile;
import com.darkan.cache.Cache;
import com.darkan.cache.def.items.ItemDef;
import com.darkan.cache.def.npcs.NPCDef;
import com.darkan.cache.def.structs.StructDef;
import com.darkan.cache.def.vars.ScriptVarType;

public class EnumDef {
	public int id;
	public ScriptVarType keyType = ScriptVarType.INT;
	public ScriptVarType valueType = ScriptVarType.INT;
	public int defaultInt = 0;
	public String defaultString = null;
	public Map<Integer, Object> values = new HashMap<>();
	
	private static EnumDefParser PARSER = new EnumDefParser();
	
	public static EnumDefParser getParser() {
		return PARSER;
	}
	
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
		if (valueType == ScriptVarType.COMPONENT) {
			if (o instanceof String)
				return o;
			long interfaceId = ((int) o) >> 16;
			long componentId = ((int) o) - (interfaceId << 16);
			return "IComponent("+interfaceId+", "+componentId+")";
		} else if (valueType  == ScriptVarType.WORLDTILE) {
			if (o instanceof String)
				return o;
			return new WorldTile(((int) o));
		} else if (valueType  == ScriptVarType.STAT) {
			if (o instanceof String)
				return o;
			int idx = (int) o;
			if (idx < 0 || idx >= Constants.SKILL_NAME.length)
				return o;
			return idx+"("+Constants.SKILL_NAME[((int) o)]+")";
		} else if (valueType  == ScriptVarType.ITEM) {
			if (o instanceof String)
				return o;
			return ((int) o)+ (((int) o) != -1 ? " ("+ItemDef.get(((int) o)).name+")" : ""); 
		} else if (valueType  == ScriptVarType.NPC) {
			if (o instanceof String)
				return o;
			return ((int) o)+ (((int) o) != -1 ? " ("+NPCDef.get(((int) o)).name+")" : ""); 
		} else if (valueType  == ScriptVarType.STRUCT) {
			return o + ": " + StructDef.get((int) o);
		}
		return o;
	}
}
