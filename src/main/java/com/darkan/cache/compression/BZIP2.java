package com.darkan.cache.compression;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;

public class BZIP2 {
	public static byte[] decompress(byte[] compressed) throws IOException {
        byte[] fixed = new byte[compressed.length + 4];
        System.arraycopy(compressed, 0, fixed, 4, compressed.length);
        fixed[0] = (byte) 'B';
        fixed[1] = (byte) 'Z';
        fixed[2] = (byte) 'h';
        fixed[3] = (byte) '1';

        BZip2CompressorInputStream is = new BZip2CompressorInputStream(new ByteArrayInputStream(fixed));
		byte[] uncompressed = is.readAllBytes();
		is.close();
		return uncompressed;
    }

    public static byte[] compress(byte[] uncompressed) throws IOException {
    	ByteArrayInputStream input = new ByteArrayInputStream(uncompressed);
    	ByteArrayOutputStream output = new ByteArrayOutputStream();
    	BZip2CompressorOutputStream bzip2 = new BZip2CompressorOutputStream(output, 1);
        byte[] block = new byte[4096];
        while (true) {
            int len = input.read(block);
            if (len == -1) 
            	break;

            bzip2.write(block, 0, len);
        }
	    byte[] buffer = output.toByteArray();

        byte[] stripped = new byte[buffer.length - 4];
        System.arraycopy(buffer, 4, stripped, 0, stripped.length);
        bzip2.close();
        return stripped;
    }
}
