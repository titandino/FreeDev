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
import java.io.IOException;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

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
}
