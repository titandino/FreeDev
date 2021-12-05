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

public class ArchiveFile {
	public int id;
	public byte[] data = new byte[0];
	public int name = 0;

	public ArchiveFile(int id, byte[] data, int name) {
		this.id = id;
		this.data = data;
		this.name = name;
	}

	public ArchiveFile(int id, byte[] data) {
		this(id, data, 0);
	}

	public ArchiveFile(int id) {
		this(id, new byte[0]);
	}
}
