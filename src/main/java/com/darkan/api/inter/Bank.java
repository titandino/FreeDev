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
package com.darkan.api.inter;

public class Bank {
	
	public static boolean isOpen() {
		return kraken.plugin.api.Bank.isOpen();
	}
	
	public static boolean depositInventory() {
		return Interfaces.click(1, 517, 39, -1);
	}
	
	public static boolean depositEquipment() {
		return Interfaces.click(1, 517, 42, -1);
	}
	
	public static boolean depositBoB() {
		return Interfaces.click(1, 517, 45, -1);
	}
	
	public static boolean loadPreset(int presetNum) {
		return Interfaces.click(1, 517, 116, presetNum);
	}

}
