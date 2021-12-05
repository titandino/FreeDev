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
package com.darkan.cache.def.vars.impl.varbits;

import com.darkan.cache.Cache;
import com.darkan.cache.def.vars.VarDomain;

public class VarbitDef {
	
	private static VarbitDefParser PARSER = new VarbitDefParser();
	
	public static VarbitDef get(int id) {
		return PARSER.get(Cache.get(), id);
	}

	public static VarbitDefParser getParser() {
		return PARSER;
	}
	
	public int id;
	public byte unk;
	public int baseVar;
	public int startBit;
	public int endBit;
	public VarDomain domain;
	
	@Override
	public String toString() {
		return "[" + id + " base: " + baseVar + " (" + startBit + " -> " + endBit + ") domain: (" + domain + ")]";
	}
}
