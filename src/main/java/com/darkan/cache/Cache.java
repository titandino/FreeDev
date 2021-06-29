package com.darkan.cache;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.CRC32;

import com.darkan.cache.util.Utils;

public class Cache {
	private static final Path PATH = Paths.get("C:/ProgramData/Jagex/RuneScape/");

	public boolean[] checkedReferenceTables = new boolean[255];
	public ReferenceTable[] cachedReferenceTables = new ReferenceTable[255];
	public IndexFile[] indices = new IndexFile[0];

	public Cache() {
		try {
			if (!Files.exists(PATH))
				Files.createDirectories(PATH);
		} catch (IOException e) {
			System.err.println("Failed to create cache directories.");
			e.printStackTrace();
		}

		System.out.println("Opening SQLite cache from " + PATH);
		int count = 0;
		indices = new IndexFile[255];
		for (int i = 0; i < 255; i++) {
			if (Files.exists(PATH.resolve("js5-" + i + ".jcache"))) {
				indices[i] = new IndexFile(PATH.resolve("js5-" + i + ".jcache"));
				count++;
			}
		}
		System.out.println("Found " + count + " indices...");
	}
	
	public ReferenceTable getReferenceTable(int index) {
		return getReferenceTable(index, false);
	}

	public ReferenceTable getReferenceTable(int index, boolean ignoreChecked) {
		ReferenceTable cached = cachedReferenceTables[index];
		if (cached != null)
			return cached;

		if (!ignoreChecked) {
			if (checkedReferenceTables[index])
				return null;
			checkedReferenceTables[index] = true;
		}

		ReferenceTable table = new ReferenceTable(this, index);
		ByteBuffer container = readReferenceTable(index);
		if (container == null)
			return null;
		ByteBuffer data = ByteBuffer.wrap(Container.decode(container).data);
		table.decode(data);
		cachedReferenceTables[index] = table;
		return table;
	}

	public void createIndex(int id) {
		if (id < indices.length)
			throw new IllegalArgumentException("Index " + id + " already exists (curr len = " + indices.length + ")");
		if (id != indices.length)
			throw new IllegalArgumentException("Create indices one by one (got " + id + ", start at " + indices.length + ")");

		IndexFile[] tmp = indices;
		this.indices = new IndexFile[id + 1];
		for (int i = 0; i < indices.length; i++)
			this.indices[i] = i == indices.length ? new IndexFile(PATH.resolve("js5-" + i + ".jcache")) : tmp[i];
	}

	public boolean exists(int index, int archive) {
		if (index < 0 || index >= indices.length)
			throw new IndexOutOfBoundsException("Index out of bounds: " + index);

		return indices[index] == null ? false : indices[index].exists(archive);
	}

	public ByteBuffer read(int index, int archive) {
		if (index < 0 || index >= indices.length)
			throw new IndexOutOfBoundsException("Index out of bounds: " + index);

		return ByteBuffer.wrap(indices[index].getRaw(archive));
	}

	public ByteBuffer read(int index, String name) {
		ReferenceTable table = getReferenceTable(index, false);
		if (table == null)
			return null;
		int hash = Utils.toFilesystemHash(name);
		int id = -1;
		for (Archive archive : table.archives.values()) {
			if (archive.name == hash) {
				id = archive.id;
				break;
			}
		}
		if (id == -1)
			return null;
		return read(index, id);
	}

	public ByteBuffer readReferenceTable(int index) {
		if (index < 0 || index >= indices.length)
			throw new IndexOutOfBoundsException("Index out of bounds: " + index);
		byte[] table = indices[index].getRawTable();
		if (table == null)
			return null;
		return ByteBuffer.wrap(table);
	}

	public void write(int index, int archive, Container data) {
		if (index < 0 || index >= indices.length)
			throw new IndexOutOfBoundsException("Index out of bounds: " + index);

		byte[] compressed = data.compress().array();
		CRC32 crc = new CRC32();
		crc.update(compressed, 0, compressed.length - 2);
		write(index, archive, compressed, data.version, (int) crc.getValue());
	}

	public void write(int index, int archive, byte[] compressed, int version, int crc) {
		if (index < 0 || index >= indices.length)
			throw new IndexOutOfBoundsException("Index out of bounds: " + index);

		indices[index].putRaw(archive, compressed, version, crc);
	}

	public void writeReferenceTable(int index, Container data) {
		if (index < 0 || index >= indices.length)
			throw new IndexOutOfBoundsException("Index out of bounds: " + index);

		if (data.version == -1)
			data.version = 100;
		byte[] compressed = data.compress().array();
		CRC32 crc = new CRC32();
		crc.update(compressed, 0, compressed.length - 2);
		writeReferenceTable(index, compressed, data.version, (int) crc.getValue());
	}

	public void writeReferenceTable(int index, byte[] compressed, int version, int crc) {
		if (index < 0 || index >= indices.length)
			throw new IndexOutOfBoundsException("Index out of bounds: " + index);

		indices[index].putRawTable(compressed, version, crc);
	}

	public int numIndices() {
		return indices.length;
	}
}
