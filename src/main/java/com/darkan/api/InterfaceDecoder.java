package com.darkan.api;

public class InterfaceDecoder {
	
	public static void main(String[] args) {
		int hash = 89784350;
		int interfaceId = hash >> 16;
		int slotId = (hash & 0xFF);
		int childId = hash - (interfaceId << 16);
		
		System.out.println("Interface: " + interfaceId + ", child: " + childId + ", slot: " + slotId);
		System.out.println("Encoded matches: " + (interfaceId << 16 | childId) + " - " + hash);
	}

}
