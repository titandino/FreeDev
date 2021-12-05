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

import kraken.plugin.api.Widgets;

public class Interface {
	
	private int id;
	
	public Interface(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public IFComponent[] getComponents() {
		IFComponent[] components = new IFComponent[Widgets.getGroupById(id).getWidgets().length];
		for (int i = 0;i < components.length;i++)
			components[i] = new IFComponent(id, i);
		return components;
	}

	public boolean isOpen() {
		return Widgets.getGroupById(id) != null && getComponents() != null;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("Interface: " + id + "\r\n");
		s.append("Components: ");
		for (IFComponent component : getComponents()) {
			try {
				s.append(component.toString());
			} catch (Exception e) {
				
			}
		}
		return s.toString();
	}
}
