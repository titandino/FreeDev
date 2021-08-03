package com.darkan.api;

import java.io.IOException;

public class InterfaceDecoder {
	
	public static void main(String[] args) throws IOException {
		int hash = 93716496;
		int interfaceId = hash >> 16;
		int slotId = (hash & 0xFF);
		int childId = hash - (interfaceId << 16);
		
		System.out.println("Interface: " + interfaceId + ", child: " + childId + ", slot: " + slotId);
		System.out.println("Encoded matches: " + (interfaceId << 16 | childId) + " - " + hash);
		
		String h = "Deposit-All";
		int op = switch(h) {
         case "Deposit-1" -> 2;
         case "Deposit-5" -> 3;
         case "Deposit-10" -> 4;
         case "Deposit-50" -> 5;
         case "Deposit-X" -> 6;
         case "Deposit-All" -> 7;
         default -> 1;
     };
     
     System.out.println(op);
	}

}
