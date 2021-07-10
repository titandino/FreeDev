package com.darkan.kraken.entity;

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

	public VarManager() {
		//values = new int[VarbitDef.getParser().getMaxId()];
	}
	
	public void setVar(int id, int value) {
		values[id] = value;
	}
	
	public void setVarBit(int id, int value) {
//		VarbitDef defs = VarbitDef.get(id);
//		int mask = BIT_MASKS[defs.endBit - defs.startBit];
//		if (value < 0 || value > mask) {
//			value = 0;
//		}
//		mask <<= defs.startBit;
//		int varpValue = (values[defs.baseVar] & (mask ^ 0xffffffff) | value << defs.startBit & mask);
//		if (varpValue != values[defs.baseVar]) {
//			setVar(defs.baseVar, varpValue);
//		}
	}

	public int getVar(int id) {
		return values[id];
	}
	
	public int getVarBit(int id) {
//		VarbitDef defs = VarbitDef.get(id);
//		return values[defs.baseVar] >> defs.startBit & BIT_MASKS[defs.endBit - defs.startBit];
		return -1;
	}
	
	public boolean bitFlagged(int id, int bit) {
		return (values[id] & (1 << bit)) != 0;
	}
}
