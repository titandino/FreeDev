package com.darkan.cache.compression;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import lzma.sdk.lzma.Decoder;
import lzma.streams.LzmaOutputStream;

public class LZMA {
	private static final Decoder LZMA_DECODER = new Decoder();
	private static final RSLZMAEncoder ENCODER = new RSLZMAEncoder();

	public static byte[] decompress(byte[] compressed, int size) throws IOException {
		byte[] properties = new byte[5];
		byte[] body = new byte[compressed.length - 5];

		System.arraycopy(compressed, 0, properties, 0, 5);
		System.arraycopy(compressed, 5, body, 0, compressed.length - 5);

		synchronized (LZMA_DECODER) {
			if (!LZMA_DECODER.setDecoderProperties(properties))
				throw new IllegalStateException("Invalid LZMA decoder properties: " + Arrays.toString(properties));

			ByteArrayInputStream inStrem = new ByteArrayInputStream(body);
			ByteArrayOutputStream outStream = new ByteArrayOutputStream(size);
			LZMA_DECODER.code(inStrem, outStream, size);
			return outStream.toByteArray();
		}
	}

	public static byte[] compress(byte[] uncompressed) throws IOException {
		synchronized (ENCODER) {
			ByteArrayInputStream input = new ByteArrayInputStream(uncompressed);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			LzmaOutputStream lzma = new LzmaOutputStream(baos, new RSLZMAEncoder());
			byte[] block = new byte[4096];
			while (true) {
				int len = input.read(block);
				if (len == -1)
					break;

				lzma.write(block, 0, len);
			}
			return baos.toByteArray();
		}
	}
}
