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
package com.darkan.cache.def.params;

import java.nio.ByteBuffer;
import java.util.Arrays;

import com.darkan.api.util.Utils;
import com.darkan.cache.Index;
import com.darkan.cache.def.CacheParser;
import com.darkan.cache.def.vars.ScriptVarType;

public class ParamDefParser extends CacheParser<ParamDef> {

	@Override
	public Index getIndex() {
		return Index.CONFIG;
	}
	
	@Override
	public int getArchiveBitSize() {
		return 1;
	}
	
	@Override
	public int getArchiveId(int id) {
		return 11;
	}

	@Override
	public int getFileId(int id) {
		return id;
	}

	@Override
	public ParamDef decode(int id, ByteBuffer buffer) {
		ParamDef def = new ParamDef();
		def.id = id;
		while (buffer.hasRemaining()) {
			int opcode = buffer.get() & 0xff;
			if (opcode == 0)
				break;
			if (opcode == 101) {
				def.typeId = Utils.getUnsignedSmart(buffer);
				def.type = ScriptVarType.getById(def.typeId);
			} else if (opcode == 2) {
				def.defaultInt = buffer.getInt();
			} else if (opcode == 4) {
				def.autoDisable = false;
			} else if (opcode == 5) {
				def.defaultString = Utils.getString(buffer);
			} else {
				throw new IllegalArgumentException("Invalid ParamDefinition opcode " + opcode + " -> " + Arrays.toString(buffer.array()));
			}
		}
		return def;
	}
}
