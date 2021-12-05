// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
//
//  Copyright © 2021 Trenton Kress
//  This file is part of project: FreeDev
//
package com.darkan.cache.compression;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import com.jcraft.jzlib.InflaterInputStream;

public class ZLIB {

	public static byte[] decompress(byte[] compressed) throws IOException {
		InflaterInputStream in = new InflaterInputStream(new ByteArrayInputStream(Arrays.copyOfRange(compressed, 8, compressed.length)));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		shovelInToOut(in, out);
		byte[] decompressed = out.toByteArray();
		in.close();
		out.close();
		return decompressed;
	}

	private static void shovelInToOut(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1000];
		int len;
		while ((len = in.read(buffer)) > 0) {
			out.write(buffer, 0, len);
		}
	}
}
