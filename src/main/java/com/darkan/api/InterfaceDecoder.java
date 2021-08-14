package com.darkan.api;

import java.io.IOException;

import com.darkan.cache.def.objects.ObjectDef;

public class InterfaceDecoder {
	
	public static void main(String[] args) throws IOException {
		int hash = 122224743;
		int interfaceId = hash >> 16;
		int slotId = (hash & 0xFF);
		int childId = hash - (interfaceId << 16);
		
		System.out.println("Interface: " + interfaceId + ", child: " + childId + ", slot: " + slotId);
		System.out.println("Encoded matches: " + (interfaceId << 16 | childId) + " - " + hash);
		
		
		System.out.println(ObjectDef.get(106707));
		
		
		
		
		
		double eggYolks = 94.0;
		
		System.out.println("Egg yolk: " + eggYolks + " grams");
		System.out.println("Cream: " + (eggYolks * 3.75) + " grams");
		System.out.println("Sugar: " + (eggYolks * 0.5625) + " grams");
		System.out.println("Vanilla: " + (eggYolks * 0.075) + " grams");
		System.out.println("Salt: " + (eggYolks * 0.0125) + " grams");
	}

}
