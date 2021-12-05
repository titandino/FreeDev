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

public enum Compression {
	NONE((byte) 0),
	BZIP((byte) 1),
	GZIP((byte) 2),
	ZLIB((byte) 'Z', (byte) 'L', (byte) 'B');
	
	private byte[] header;
	
	private Compression(byte... header) {
		this.header = header;
	}
	
	public static Compression forData(byte[] data) {
		for (Compression c : Compression.values()) {
			boolean matches = true;
			for (int i = 0;i < c.header.length;i++) {
				if (data[i] != c.header[i])
					matches = false;
			}
			if (matches)
				return c;
		}
		return null;
	}
}
