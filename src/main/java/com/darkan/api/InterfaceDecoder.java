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
