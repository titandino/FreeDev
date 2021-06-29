package com.darkan.cache;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class Archive {
		public int id;
		public int name;
		public int crc = 0;
		public int version = 0;
		public byte[] whirlpool = null;
	    public int uncompressedSize = 0;
	    public int compressedSize = 0;
	    public int hash = 0;

	    public boolean loaded = false;
	    public boolean requiresUpdate = false;
	    public Map<Integer, ArchiveFile> files = new HashMap<>();
	    
	    public Archive(int id) {
	    	this(id, 0);
	    }
	    
	    public Archive(int id, int name) {
	    	this.id = id;
	    	this.name = name;
	    }

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
	        int numChunks = buffer.get() & 0xff;

	        int size = files.size();
	        int[][] chunkSizes = new int[numChunks][size];
	        int[] sizes = new int[size];
	        int[] offsets = new int[size];
	        int[] ids = files.keySet().stream().mapToInt(Integer::intValue).toArray();

	        buffer.position(buffer.limit() - 1 - numChunks * (size * 4));
	        for (int chunk = 0;chunk < numChunks;chunk++) {
	            int chunkSize = 0;
	            for (int file = 0;file < size;file++) {
	                int delta = buffer.getInt();
	                chunkSize += delta;

	                chunkSizes[chunk][file] = chunkSize;
	                sizes[file] += chunkSize;
	            }
	        }

	        for (int file = 0;file < size;file++) {
	            files.get(ids[file]).data = new byte[sizes[file]];
	        }

	        buffer.position(0);

	        for (int chunk = 0;chunk < numChunks;chunk++) {
	        	for (int file = 0;file < size;file++) {
	                int chunkSize = chunkSizes[chunk][file];

	                int offset = offsets[file];
	                buffer.get(files.get(ids[file]).data, offset, chunkSize);
	                offsets[file] += chunkSize;
	            }
	        }
	    }

	    public ByteBuffer encode() {
	        int size = 0;
	        for (ArchiveFile file : files.values())
	        	size += file.data.length;
	        ByteBuffer buffer = ByteBuffer.allocate(1 + size + files.size() * 4);
	        if (files.size() == 1) {
	            return ByteBuffer.wrap(files.values().stream().findFirst().isPresent() ? files.values().stream().findFirst().get().data : new byte[0]);
	        } else {
	            int last = 0;
	            for (ArchiveFile file : files.values()) {
	            	if (last > file.id) 
	            		throw new IllegalStateException("Out of order " + last + ", " + file.id);
	                buffer.put(file.data);
	                last = file.id;
	            }
	            ArchiveFile[] filesArray = (ArchiveFile[]) files.values().stream().sorted().toArray();
	            for (int i = 0;i < filesArray.length;i++) {
	                ArchiveFile file = filesArray[i];
	                int fileSize = file.data.length;
	                int previousSize = i == 0 ? 0 : filesArray[i - 1].data.length;
	                buffer.putInt(fileSize - previousSize);
	            }
	            buffer.put((byte) 1);
	        }
	        buffer.flip();
	        return buffer;
	    }
	}
