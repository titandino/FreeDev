package com.darkan.cache.compression;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZIP {
	public static byte[] decompress(byte[] compressed) throws IOException {
        return new GZIPInputStream(new ByteArrayInputStream(compressed)).readAllBytes();
    }

	public static byte[] compress(byte[] uncompressed) throws IOException {
    	ByteArrayInputStream input = new ByteArrayInputStream(uncompressed);
    	ByteArrayOutputStream output = new ByteArrayOutputStream();
    	GZIPOutputStream bzip2 = new GZIPOutputStream(output);
        byte[] block = new byte[4096];
        while (true) {
            int len = input.read(block);
            if (len == -1) 
            	break;

            bzip2.write(block, 0, len);
        }
	    byte[] buffer = output.toByteArray();
        bzip2.close();
        return buffer;
    }
}
