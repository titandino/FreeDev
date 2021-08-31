package com.darkan.api;

import java.io.IOException;

public class InterfaceDecoder {
	
	public static void main(String[] args) throws IOException {
		int hash = 77922323;
		int interfaceId = hash >> 16;
		int slotId = (hash & 0xFF);
		int childId = hash - (interfaceId << 16);
		
		//Interfaces.dialContinue(1184, 15);
		//Interfaces.dialContinue(1189, 19);
		//Interfaces.dialContinue(1191, 15);
		
		System.out.println("Interface: " + interfaceId + ", child: " + childId + ", slot: " + slotId);
		System.out.println("Encoded matches: " + (interfaceId << 16 | childId) + " - " + hash);
	}

}
