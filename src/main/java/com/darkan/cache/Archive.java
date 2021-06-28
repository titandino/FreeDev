package com.darkan.cache;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

class Archive {
		public int id;
		public int name;
		public int crc = 0;
		public int version = 0;
		public byte[] whirlpool = null;
	    public int uncompressedSize = 0;
	    public int compressedSize = 0;
	    public int hash = 0;

	    private boolean loaded = false;
	    public boolean requiresUpdate = false;
	    public Map<Integer, ArchiveFile> files = new HashMap<>();

	    public void putFile(int id, byte[] data) {
	        requiresUpdate = true;
	        files.put(id, new ArchiveFile(id, data, 0));
	    }

	    public void putFile(ArchiveFile file) {
	        requiresUpdate = true;
	        files.put(id, file);
	    }

	    public void decode(ByteBuffer buffer) {
	        loaded = true;
	        if (files.size() == 1) {
	        	for (ArchiveFile file : files.values())
	        		file.data = buffer.array();
	            return;
	        }

	        buffer.position(buffer.limit() - 1);
	        int numChunks = buffer.get().toInt() & 0xff;

	        int size = files.size();
	        int[][] chunkSizes = new int[numChunks][size];
	        int sizes = IntArray(size);
	        val offsets = IntArray(size);
	        val ids = files.keys.sorted().toIntArray();

	        buffer.position(buffer.limit() - 1 - numChunks * (size * 4));
	        for (chunk in 0 until numChunks) {
	            var chunkSize = 0
	            for (file in 0 until size) {
	                val delta = buffer.int
	                chunkSize += delta

	                chunkSizes[chunk][file] = chunkSize
	                sizes[file] += chunkSize
	            }
	        }

	        for (file in 0 until size) {
	            files[ids[file]]!!.data = ByteArray(sizes[file])
	        }

	        buffer.position(0)

	        for (chunk in 0 until numChunks) {
	            for (file in 0 until size) {
	                val chunkSize = chunkSizes[chunk][file]

	                val offset = offsets[file]
	                buffer.get(files[ids[file]]!!.data, offset, chunkSize)
	                offsets[file] += chunkSize
	            }
	        }
	    }

	    fun encode(): ByteBuffer {
	        var size = 0
	        files.values.forEach { size += it.data.size }
	        val buffer = ByteBuffer.allocate(1 + size + files.size * 4)
	        if (files.size == 1) {
	            return ByteBuffer.wrap(files[files.firstKey()]?.data ?: ByteArray(0))
	        } else {
	            var last = 0
	            files.forEach {
	                if (last > it.key) throw IllegalStateException("out of order $last, ${it.key}")
	                buffer.put(it.value.data)
	                last = it.key
	            }
	            val filesArray = files.values.toTypedArray()
	            for (i in filesArray.indices) {
	                val file = filesArray[i]
	                val fileSize = file.data.size
	                val previousSize = if (i == 0) 0 else filesArray[i - 1].data.size
	                buffer.putInt(fileSize - previousSize)
	            }
	            buffer.put(1)
	        }
	        buffer.flip()
	        return buffer
	    }
	}
