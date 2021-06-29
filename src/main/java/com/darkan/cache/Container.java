package com.darkan.cache;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.darkan.cache.compression.BZIP2;
import com.darkan.cache.compression.Compression;
import com.darkan.cache.compression.GZIP;
import com.darkan.cache.compression.LZMA;

class Container {

	public byte[] data;
	public Compression compression = Compression.LZMA;
	public int version = -1;
	
	public Container(byte[] data, Compression compression, int version) {
		this.data = data;
		this.compression = compression;
		this.version = version;
	}

	public static Container decode(ByteBuffer data) {
		try {
			if (!data.hasRemaining())
				throw new IllegalArgumentException("Provided non-readable (empty?) buffer");
	
			Compression compression = Compression.forId(data.get());
			int size = data.getInt();
	
			int decompressedSize = compression == Compression.NONE ? 0 : data.getInt();
			byte[] compressed = new byte[size];
			if (data.remaining() < compressed.length) {
				System.out.println("Buffer underflow: " + data.remaining() + " -> " + compressed.length + " -> " + decompressedSize);
			}
			data.get(compressed);
			int version = data.remaining() >= 2 ? data.getShort() & 0xffff : -1;
	
			byte[] decompressed;
			switch (compression) {
			case BZIP2:
				decompressed = BZIP2.decompress(compressed);
				break;
			case GZIP:
				decompressed = GZIP.decompress(compressed);
				break;
			case LZMA:
				decompressed = LZMA.decompress(compressed, decompressedSize);
				break;
			default:
				decompressed = compressed;
				break;
			}
			return new Container(decompressed, compression, version);
		} catch(IOException e) {
			System.err.println("Error decompressing container data...");
			e.printStackTrace();
			return null;
		}
	}

	public static ByteBuffer wrap(ByteBuffer data) {
		ByteBuffer buf = ByteBuffer.allocate(5 + data.remaining());
		buf.put((byte) Compression.NONE.ordinal());
		buf.putInt(data.remaining());
		buf.put(data);
		buf.flip();
		return buf;
	}

	public static ByteBuffer wrap(byte[] data) {
		ByteBuffer buf = ByteBuffer.allocate(5 + data.length);
		buf.put((byte) Compression.NONE.ordinal());
		buf.putInt(data.length);
		buf.put(data);
		buf.flip();
		return buf;
	}

	public ByteBuffer compress() {
		try {
			byte[] compressed;
			switch (compression) {
			case BZIP2:
				compressed = BZIP2.compress(data);
				break;
			case GZIP:
				compressed = GZIP.compress(data);
				break;
			case LZMA:
				compressed = LZMA.compress(data);
				break;
			default:
				compressed = data;
				break;
			}
			ByteBuffer buffer = ByteBuffer.allocate(compressed.length + 1 + 4 + (compression != Compression.NONE ? 4 : 0) + (version != -1 ? 2 : 0));
	        buffer.put((byte) compression.ordinal());
	        buffer.putInt(compressed.length);
	        if (compression != Compression.NONE)
	            buffer.putInt(data.length);
	        buffer.put(compressed);
	        if (version != -1)
	            buffer.putShort((short) version);
	        buffer.flip();
	
	        return buffer;
		} catch (IOException e) {
			System.err.println("Error compressing container data...");
			e.printStackTrace();
			return null;
		}
    }
}