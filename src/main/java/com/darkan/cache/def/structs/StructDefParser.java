package com.darkan.cache.def.structs;

import java.nio.ByteBuffer;

import com.darkan.cache.Index;
import com.darkan.cache.def.CacheParser;
import com.darkan.cache.util.Utils;

public class StructDefParser extends CacheParser<StructDef> {

	@Override
	public Index getIndex() {
		return Index.STRUCT_DEF;
	}
	
	@Override
	public int getArchiveId(int id) {
		return Utils.archiveId(id, 5);
	}

	@Override
	public int getFileId(int id) {
		return Utils.fileId(id, 5);
	}

	@Override
	public StructDef decode(ByteBuffer buffer) {
		StructDef def = new StructDef();
		while (buffer.hasRemaining()) {
			int opcode = buffer.get() & 0xff;
			if (opcode == 0)
				break;

			switch (opcode) {
			case 249:
				def.params.parse(buffer);
				break;
			default:
				throw new IllegalArgumentException("Invalid StructDefinition opcode " + opcode);
			}
		}
		return def;
	}
}
