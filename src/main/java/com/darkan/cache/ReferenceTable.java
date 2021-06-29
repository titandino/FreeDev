package com.darkan.cache;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.CRC32;

import com.darkan.cache.compression.Compression;
import com.darkan.cache.util.Utils;
import com.darkan.cache.util.Whirlpool;

public class ReferenceTable {
	public Cache cache;
	public int version = 0;
	public int format = 7;
	public int mask = 0;
	public int index;
	public Map<Integer, Archive> archives = new HashMap<>();

	public ReferenceTable(Cache cache, int index) {
		this.cache = cache;
		this.index = index;
	}

	public void update() {
		List<Archive> toUpdate = new ArrayList<>();
		for (Archive archive : archives.values()) {
			if (archive.requiresUpdate)
				toUpdate.add(archive);
		}
		for (Archive archive : toUpdate) {
			archive.version++;
			archive.requiresUpdate = false;
			Container container = new Container(archive.encode().array(), Compression.LZMA, archive.version);
			byte[] compressed = container.compress().array();
			CRC32 crc = new CRC32();
			crc.update(compressed, 0, compressed.length - 2);
			archive.crc = (int) crc.getValue();
			cache.write(index, archive.id, compressed, archive.version, archive.crc);

			if ((mask & 0x2) != 0)
				archive.whirlpool = Whirlpool.getHash(compressed, 0, compressed.length - 2);

			System.out.println("Updating archive $index:${archive.id}");
		}

		if (!toUpdate.isEmpty())
			bumpVersion();
	}

	public void bumpVersion() {
		version++;
		Container data = new Container(encode().array(), Compression.GZIP, -1);
		byte[] compressed = data.compress().array();
		CRC32 crc = new CRC32();
		crc.update(compressed);
		cache.writeReferenceTable(index, compressed, version, (int) crc.getValue());

		System.out.println("Updating reference table of index " + index);
	}

	public void writeFormatInt(ByteBuffer buffer, int value) {
		if (format >= 7) {
			Utils.putSmartInt(buffer, value);
		} else {
			buffer.putShort((short) value);
		}
	}

	public int readFormatInt(ByteBuffer buffer) {
		if (format >= 7)
			return Utils.getSmartInt(buffer);
		else
			return buffer.getShort() & 0xffff;
	}

	public ByteBuffer encode() {
		ByteBuffer buffer = ByteBuffer.allocate(4_000_000); // TODO Is this ever enough? Or not?
		buffer.put((byte) format);
		if (format >= 6)
			buffer.putInt(version);
		buffer.put((byte) mask); // TODO Generate hash based on files

		boolean hasNames = (mask & 0x1) != 0;
		boolean hasWhirlpools = (mask & 0x2) != 0;
		boolean hasSizes = (mask & 0x4) != 0;
		boolean hasHashes = (mask & 0x8) != 0;

		int[] archiveIds = archives.keySet().stream().sorted().mapToInt(Integer::intValue).toArray();

		writeFormatInt(buffer, archives.size());
		for (int i = 0; i < archiveIds.length; i++)
			writeFormatInt(buffer, archiveIds[i] - ((i == 0) ? 0 : archiveIds[i - 1]));

		if (hasNames) {
			for (int i : archiveIds)
				buffer.putInt(archives.get(i).name);
		}

		for (int i : archiveIds)
			buffer.putInt(archives.get(i).crc);

		if (hasHashes) {
			for (int i : archiveIds)
				buffer.putInt(archives.get(i).hash);
		}

		if (hasWhirlpools) {
			for (int i : archiveIds)
				buffer.put(archives.get(i).whirlpool);
		}

		if (hasSizes) {
			for (int i : archiveIds) {
				Archive archive = archives.get(i);
				if (archive == null)
					throw new IllegalStateException("Invalid archive id " + i);

				buffer.putInt(archive.compressedSize);
				buffer.putInt(archive.uncompressedSize);
			}
		}

		for (int i : archiveIds)
			buffer.putInt(archives.get(i).version);

		int[][] archiveFileIds = new int[archives.size()][];
		for (int i = 0; i < archiveFileIds.length; i++)
			archiveFileIds[i] = archives.get(archiveIds[i]).files.keySet().stream().sorted().mapToInt(Integer::intValue).toArray();

		for (int i : archiveIds)
			writeFormatInt(buffer, archives.get(i).files.size());

		for (int i = 0; i < archiveIds.length; i++) {
			int[] fileIds = archiveFileIds[i];
			for (int j = 0; j < fileIds.length; j++)
				writeFormatInt(buffer, fileIds[j] - ((j == 0) ? 0 : fileIds[j - 1]));
		}

		if (hasNames) {
			for (int i = 0; i < archiveIds.length; i++) {
				Archive archive = archives.get(archiveIds[i]);
				if (archive == null)
					throw new IllegalStateException("invalid archive id " + archiveIds[i]);
				int[] fileIds = archiveFileIds[i];
				for (int j = 0; j < fileIds.length; j++)
					buffer.putInt(archive.files.get(fileIds[j]).name);
			}
		}

		buffer.flip();
		return buffer;
	}

	public void decode(ByteBuffer buffer) {
        format = buffer.get();
        if (format < 5 || format > 7)
            throw new IllegalArgumentException("Reference table format not in range 5..7");
        version = (format >= 6) ? buffer.getInt() : 0;
        mask = buffer.get();

        boolean hasNames = (mask & 0x1) != 0;
        boolean hasWhirlpools = (mask & 0x2) != 0;
        boolean hasSizes = (mask & 0x4) != 0;
        boolean hasHashes = (mask & 0x8) != 0;

        int[] archiveIds = new int[readFormatInt(buffer)];
        for (int i = 0;i < archiveIds.length;i++) {
            int archiveId = readFormatInt(buffer) + ((i == 0) ? 0 : archiveIds[i - 1]);
            archiveIds[i] = archiveId;
            archives.put(archiveId, new Archive(archiveId));
        }

        if (hasNames) {
            for (int archiveId : archiveIds)
                archives.get(archiveId).name = buffer.getInt();
        }

        for (int archiveId : archiveIds)
            archives.get(archiveId).crc = buffer.getInt();

        if (hasHashes) {
        	for (int archiveId : archiveIds)
                archives.get(archiveId).hash = buffer.getInt();
        }

        if (hasWhirlpools) {
        	for (int archiveId : archiveIds) {
                byte[] whirlpool = new byte[64];
                buffer.get(whirlpool);
                archives.get(archiveId).whirlpool = whirlpool;
            }
        }

        if (hasSizes) {
        	for (int archiveId : archiveIds) {
                Archive archive = archives.get(archiveId);
                archive.compressedSize = buffer.getInt();
                archive.uncompressedSize = buffer.getInt();
            }
        }

        for (int archiveId : archiveIds)
            archives.get(archiveId).version = buffer.getInt();

        int[][] archiveFileIds = new int[archives.size()][];
        for (int i = 0;i < archiveFileIds.length;i++)
        	archiveFileIds[i] = new int[readFormatInt(buffer)];

        for (int i = 0;i < archiveIds.length;i++) {
            Archive archive = archives.get(archiveIds[i]);
            int[] fileIds = archiveFileIds[i];
            int fileId = 0;
            for (int j = 0;j < fileIds.length;j++) {
                fileId += readFormatInt(buffer);
                archive.files.put(fileId, new ArchiveFile(fileId));
                fileIds[j] = fileId;
            }
        }

        if (hasNames) {
        	for (int i = 0;i < archiveIds.length;i++) {
        		Archive archive = archives.get(archiveIds[i]);
        		int[] fileIds = archiveFileIds[i];
                for (int j = 0;j < fileIds.length;j++)
                    archive.files.get(fileIds[j]).name = buffer.getInt();
            }
        }
    }

	public int highestEntry() {
		if (archives.isEmpty())
			return 0;
		else
			return archives.keySet().stream().reduce((first, second) -> second).orElse(null) + 1;
	}

	public int archiveSize() {
        if ((mask & 0x4) != 0) {
            int sum = 0;
            for (Archive a : archives.values())
                sum += a.uncompressedSize;
            return sum;
        } else {
        	int sum = 0;
            for (int key : archives.keySet()) {
                ByteBuffer data = cache.read(index, key);
                Container container = Container.decode(data);
                sum += container.data.length;
            }
            return sum;
        }
    }

	public long totalCompressedSize() {
        long sum = 0L;
        for (Archive a : archives.values())
            sum += a.compressedSize;
        return sum;
    }

	public Archive loadArchive(int id) {
        Archive archive = archives.get(id);
        if (archive == null)
        	return null;
        if (archive.loaded) 
        	return archive;

        ByteBuffer raw = cache.read(index, id);
        ByteBuffer file = ByteBuffer.wrap(Container.decode(raw).data);
        archive.decode(file);

        return archive;
    }

	/**
     * If the archive theoretically exists but cannot be loaded this returns null.
     */
    public Archive loadOrCreateArchive(int id) {
        Archive archive = archives.get(id);
        if (archive == null) {
            Archive toReturn = new Archive(id);
            toReturn.requiresUpdate = true;
            return toReturn;
        }
        if (archive.loaded) 
        	return archive;

        ByteBuffer raw = cache.read(index, id);
        ByteBuffer file = ByteBuffer.wrap(Container.decode(raw).data);
        archive.decode(file);

        return archive;
    }

}