package com.darkan.cache.def.enums;

import java.nio.ByteBuffer;
import java.util.Map;

import com.darkan.cache.Archive;
import com.darkan.cache.ArchiveFile;
import com.darkan.cache.Cache;
import com.darkan.cache.ReferenceTable;
import com.darkan.cache.ResourceType;
import com.darkan.cache.def.vars.ScriptVarType;
import com.darkan.cache.util.Utils;

import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;

public class EnumDefParser {
	private static Map<Integer, EnumDef> CACHED = new Int2ObjectAVLTreeMap<>();

	public static int getMaxId(Cache cache) {
		ReferenceTable table = cache.getReferenceTable(17);
		if (table == null)
			return 0;
		int maxArchive = table.highestEntry() - 1;
		int files = table.archives.get(maxArchive).files.keySet().stream().reduce((first, second) -> second).orElse(null);
		return maxArchive * 256 + files;
	}

	public static Map<Integer, EnumDef> list(Cache cache) {
		for (int i = 0; i < getMaxId(cache) + 1; i++) {
			EnumDef def = get(cache, i);
			if (def == null)
				continue;
			CACHED.put(i, def);
		}
		return CACHED;
	}

	public static EnumDef get(Cache cache, int id) {
		if (CACHED.get(id) != null)
			return CACHED.get(id);
		ReferenceTable table = cache.getReferenceTable(17);
		Archive archive = table.loadArchive(ResourceType.getArchive(id, 8));
		ArchiveFile file = archive.files.get(ResourceType.getFile(id, 8));

		EnumDef def = new EnumDef();
		ByteBuffer buffer = ByteBuffer.wrap(file.data);
		while (buffer.hasRemaining()) {
			int opcode = buffer.get() & 0xff;
			if (opcode == 0) {
				CACHED.put(id, def);
				return def;
			}

			switch (opcode) {
			case 1:
				def.keyType = ScriptVarType.getByChar(Utils.cp1252ToChar(buffer.get()));
				break;
			case 2:
				def.valueType = ScriptVarType.getByChar(Utils.cp1252ToChar(buffer.get()));
				break;
			case 3:
				def.defaultString = Utils.getString(buffer);
				break;
			case 4:
				def.defaultInt = buffer.getInt();
				break;
			case 5:
			case 6:
			case 7:
			case 8:
				boolean stringValues = opcode == 5 || opcode == 7;
				if (opcode == 7 || opcode == 8)
					Utils.skip(buffer, 2);
				int size = buffer.getShort() & 0xffff;
				for (int i = 0; i < size; i++) {
					int key = (opcode == 5 || opcode == 6) ? buffer.getInt() : (buffer.getShort() & 0xffff);
					Object value = stringValues ? Utils.getString(buffer) : buffer.getInt();
					def.values.put(key, value);
				}

				break;
			case 101:
				int type = Utils.getSmallSmartInt(buffer);
				def.keyType = ScriptVarType.getById(type);

				break;
			case 102:
				type = Utils.getSmallSmartInt(buffer);
				def.valueType = ScriptVarType.getById(type);

				break;
			default:
				throw new IllegalArgumentException("Invalid EnumDefinition opcode " + opcode);
			}
		}
		CACHED.put(id, def);
		return def;
	}
}
