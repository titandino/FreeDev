package com.darkan.api.entity;

import java.util.HashSet;
import java.util.Set;

import com.darkan.cache.def.vars.impl.varbits.VarbitDef;

import kraken.plugin.api.Client;
import kraken.plugin.api.ConVar;

public class VarManager {

	public static final int[] BIT_MASKS = new int[32];

	static {
		int i = 2;
		for (int i2 = 0; i2 < 32; i2++) {
			BIT_MASKS[i2] = i - 1;
			i += i;
		}
	}

	private int[] values;
	private Set<Integer> synced;

	public VarManager() {
		values = new int[VarbitDef.getParser().getMaxId()];
		synced = new HashSet<>();
	}
	
	public void setVar(int id, int value) {
		values[id] = value;
	}
	
	public void setVarBit(int id, int value) {
		VarbitDef defs = VarbitDef.get(id);
		int mask = BIT_MASKS[defs.endBit - defs.startBit];
		if (value < 0 || value > mask) {
			value = 0;
		}
		mask <<= defs.startBit;
		int varpValue = (getVar(defs.baseVar) & (mask ^ 0xffffffff) | value << defs.startBit & mask);
		if (varpValue != getVar(defs.baseVar)) {
			setVar(defs.baseVar, varpValue);
		}
	}

	public int getVar(int id) {
		if (!synced.contains(id)) {
			ConVar var = Client.getConVarById(id);
			if (var != null)
				setVar(id, var.getValue());
			synced.add(id);
		}
		return values[id];
	}
	
	public int getVarBit(int id) {
		VarbitDef defs = VarbitDef.get(id);
		return getVar(defs.baseVar) >> defs.startBit & BIT_MASKS[defs.endBit - defs.startBit];
	}
	
	public boolean bitFlagged(int id, int bit) {
		return (getVar(id) & (1 << bit)) != 0;
	}

	public void clearSynced() {
		synced.clear();
	}
}
