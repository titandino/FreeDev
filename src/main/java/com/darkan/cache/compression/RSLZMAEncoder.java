package com.darkan.cache.compression;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import lzma.sdk.lzma.Encoder;
import lzma.streams.LzmaEncoderWrapper;

public class RSLZMAEncoder extends LzmaEncoderWrapper {
	private Encoder encoder = new Encoder();
	
	public RSLZMAEncoder() {
		super(null);
	}

	@Override
	public void code(final InputStream in, final OutputStream out) throws IOException {
		encoder.writeCoderProperties(out);
        encoder.code(in, out, -1, -1, null);
	}
}
