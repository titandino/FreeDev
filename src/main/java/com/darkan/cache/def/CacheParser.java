package com.darkan.cache.def;

import java.nio.ByteBuffer;
import java.util.Map;

import com.darkan.cache.Archive;
import com.darkan.cache.ArchiveFile;
import com.darkan.cache.Cache;
import com.darkan.cache.Index;
import com.darkan.cache.ReferenceTable;
import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;

public abstract class CacheParser<T> {
	private Map<Integer, T> CACHED = new Int2ObjectAVLTreeMap<>();
	
	public abstract Index getIndex();
	public abstract int getArchiveId(int id);
	public abstract int getFileId(int id);
	public abstract T decode(ByteBuffer buffer);

	public int getMaxId(Cache cache) {
		ReferenceTable table = cache.getReferenceTable(getIndex());
		if (table == null)
			return 0;
		int maxArchive = table.highestEntry() - 1;
		int files = table.archives.get(maxArchive).files.keySet().stream().reduce((first, second) -> second).orElse(null);
		return maxArchive * 256 + files;
	}

	public Map<Integer, T> list(Cache cache) {
		for (int i = 0; i < getMaxId(cache) + 1; i++) {
			T def = get(cache, i);
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
		ArchiveFile file = archive.files.get(getFileId(id));
		if (file == null)
			return null;

		ByteBuffer buffer = ByteBuffer.wrap(file.data);
		T def = decode(buffer);
		CACHED.put(id, def);
		return def;
	}
}
