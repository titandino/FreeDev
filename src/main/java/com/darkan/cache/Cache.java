package com.darkan.cache;

import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Cache {
	private static final Path PATH = Paths.get("C:\\ProgramData\\Jagex\\RuneScape\\");
	
	public boolean[] checkedReferenceTables = new boolean[255];
	public ReferenceTable[] cachedReferenceTables = new ReferenceTable[255];
	public List<IndexFile> indices = new ArrayList<>();
	
	public void init() {
        if (!Files.exists(PATH))
            Files.createDirectories(PATH);

        System.out.println("Opening SQLite cache from " + PATH);
        var count = 0;
        while (count < 255 && Files.exists(PATH.resolve("js5-"+count+".jcache"))) 
        	count++;
        System.out.println("Found " + count + " indices...");
        
        for (int i = 0;i < count;i++)
        	indices.add(new IndexFile(PATH.resolve("js5-"+i+".jcache")));
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
        ByteBuffer data = ByteBuffer.wrap(Container.decode(container).getData());
        table.decode(data);
        cachedReferenceTables[index] = table;
        return table;
    }
	
	public void createIndex(int id) {
        if (id < indices.size) {
            throw new IllegalArgumentException("index $id already exists (arr size = ${indices.size})")
        }
        if (id != indices.size) {
            throw new IllegalArgumentException("create indices one by one (got $id, start at ${indices.size})")
        }

        val tmp = indices
        this.indices = Array(id + 1) {
            if (it != id)
                return@Array tmp[it]
            new IndexFile(PATH.resolve("js5-$it.jcache"))
        }
    }

    public boolean exists(int index, int archive) {
        if (index < 0 || index >= indices.size()) throw new IndexOutOfBoundsException("index out of bounds: $index")

        return indices[index]?.exists(archive) ?: false
    }

    override fun read(index: Int, archive: Int): ByteBuffer? {
        if (index < 0 || index >= indices.size) throw IndexOutOfBoundsException("index out of bounds: $index")

        return ByteBuffer.wrap(indices[index]?.getRaw(archive) ?: return null)
    }

    override fun read(index: Int, name: String): ByteBuffer? {
        val table = getReferenceTable(index) ?: return null
        val hash = name.toFilesystemHash()
        val id = (table.archives.entries.firstOrNull { it.value.name == hash } ?: return null).key
        return read(index, id)
    }

    public ByteBuffer readReferenceTable(int index) {
        if (index < 0 || index >= indices.size()) 
        	throw new IndexOutOfBoundsException("index out of bounds: $index");
        byte[] table = indices[index].getRawTable();
        if (table == null)
        	return null;
        return ByteBuffer.wrap(table);
    }

    override fun write(index: Int, archive: Int, data: Container) {
        if (index < 0 || index >= indices.size) throw IndexOutOfBoundsException("index out of bounds: $index")

        val compressed = data.compress().array()
        val crc = CRC32()
        crc.update(compressed, 0, compressed.size - 2)
        write(index, archive, compressed, data.version, crc.value.toInt())
    }

    override fun write(index: Int, archive: Int, compressed: ByteArray, version: Int, crc: Int) {
        if (index < 0 || index >= indices.size) throw IndexOutOfBoundsException("index out of bounds: $index")

        indices[index]?.putRaw(archive, compressed, version, crc)
    }

    override fun writeReferenceTable(index: Int, data: Container) {
        if (index < 0 || index >= indices.size) throw IndexOutOfBoundsException("index out of bounds: $index")

        if (data.version == -1) data.version = 100
        val compressed = data.compress().array()
        val crc = CRC32()
        crc.update(compressed, 0, compressed.size - 2)
        writeReferenceTable(index, compressed, data.version, crc.value.toInt())
    }

    override fun writeReferenceTable(index: Int, compressed: ByteArray, version: Int, crc: Int) {
        if (index < 0 || index >= indices.size) throw IndexOutOfBoundsException("index out of bounds: $index")

        indices[index]?.putRawTable(compressed, version, crc)
    }

    override fun numIndices(): Int = indices.size
}
