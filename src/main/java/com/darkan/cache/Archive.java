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
	
	@Override
	public String toString() {
		return "[" + id + ", " + crc + ", " + version + ", " + uncompressedSize + ", " + compressedSize + ", " + hash + ", " + files.size() + "]";
	}

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

		int first = buffer.get() & 0xff;
		if (first != 1) {
			System.err.println("Invalid first byte (Expected 1): " + first);
			return;
		}

		int size = files.size();
		int[] ids = files.keySet().stream().mapToInt(Integer::intValue).toArray();
		int[] offsets = new int[size+1];
		for (int i = 0;i < size+1;i++)
			offsets[i] = buffer.getInt() & 0xffffff;
		
		for (int i = 0;i < ids.length;i++) {
			byte[] data = new byte[offsets[i+1] - offsets[i]];
			buffer.get(data);
			files.get(ids[i]).data = data;
		}
	}
}
