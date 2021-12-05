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
package com.darkan.cache.def;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import com.darkan.api.util.Utils;
import com.darkan.cache.Archive;
import com.darkan.cache.ArchiveFile;
import com.darkan.cache.Cache;
import com.darkan.cache.Index;
import com.darkan.cache.ReferenceTable;

public abstract class CacheParser<T> {
	private Map<Integer, T> CACHED = new HashMap<>();
	
	public abstract Index getIndex();
	public abstract int getArchiveBitSize();
	public abstract T decode(int id, ByteBuffer buffer);
	
	public int getArchiveId(int id) {
		return Utils.archiveId(id, getArchiveBitSize());
	}

	public int getFileId(int id) {
		return Utils.fileId(id, getArchiveBitSize());
	}

	public int getMaxId() {
		ReferenceTable table = Cache.get().getReferenceTable(getIndex());
		if (table == null)
			return 0;
		int maxArchive = table.highestEntry() - 1;
		int files = table.archives.get(maxArchive).files.keySet().stream().reduce((first, second) -> second).orElse(null);
		return maxArchive * ((int) Math.pow(2.0, getArchiveBitSize())) + files;
	}

	public Map<Integer, T> list() {
		for (int i = 0; i < getMaxId() + 1; i++) {
			T def = get(Cache.get(), i);
			if (def == null)
				continue;
			CACHED.put(i, def);
		}
		return CACHED;
	}

	public T get(Cache cache, int id) {
		if (CACHED.get(id) != null)
			return CACHED.get(id);
		Archive archive = cache.getArchive(getIndex(), getArchiveId(id));
		if (archive == null)
			return null;
		ArchiveFile file = archive.files.get(getFileId(id));
		if (file == null)
			return null;

		ByteBuffer buffer = ByteBuffer.wrap(file.data);
		T def = decode(id, buffer);
		CACHED.put(id, def);
		return def;
	}
}
