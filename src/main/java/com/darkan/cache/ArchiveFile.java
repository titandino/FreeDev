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
}
