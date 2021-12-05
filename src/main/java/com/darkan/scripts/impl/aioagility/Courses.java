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
package com.darkan.scripts.impl.aioagility;

import com.darkan.api.util.Area;
import com.darkan.api.util.TileListArea;
import com.darkan.api.world.WorldObject;
import com.darkan.api.world.WorldTile;

public class Courses {
	
	public static final AgilityNode[] BURTHORPE = {
		new AgilityNode(66894, new Area(2924, 3572, 2922, 3579)),
		new AgilityNode(66912, new Area(2916, 3577, 2913, 3579)),
		new AgilityNode(66909, new TileListArea(2910, 3575, 2910, 3576, 2910, 3577, 2910, 3578, 2910, 3579, 2911, 3579, 2911, 3578, 2911, 3577, 2912, 3577, 2912, 3578, 2912, 3579)),
		new AgilityNode(66902, new TileListArea(2910, 3571, 2910, 3570, 2910, 3569, 2911, 3569, 2912, 3569, 2913, 3569)),
		new AgilityNode(66904, new Area(2911, 3571, 2913, 3571)),
		new AgilityNode(new WorldObject(66897, new WorldTile(2914, 3575)), new Area(2911, 3575, 2914, 3576)),
		new AgilityNode(66910, new Area(2920, 3574, 2921, 3576))
	};

}
