package com.darkan.kraken;

public class InterfaceDecoder {
	
	public static void main(String[] args) {
		int hash = 96534535;
		int interfaceId = hash >> 16;
		int slotId = (hash & 0xFF);
		int childId = hash - (interfaceId << 16);
		
		System.out.println("Interface: " + interfaceId + ", child: " + childId + ", slot: " + slotId);
	}

}
