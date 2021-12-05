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
package com.darkan.api.util;

import com.darkan.api.world.WorldTile;

import kraken.plugin.api.Vector3i;

public class TileListArea extends Area {

	private WorldTile[] tiles;
	
	public TileListArea(WorldTile... tiles) {
		super(null, null);
		this.tiles = tiles;
	}
	
	public TileListArea(int... coords) {
		super(null, null);
		tiles = new WorldTile[coords.length/2];
		for (int i = 0;i < coords.length;i += 2)
			tiles[i/2] = new WorldTile(coords[i], coords[i+1]);
	}
	
	@Override
	public boolean inside(WorldTile tile) {
		for (WorldTile t : tiles)
			if (t.isAt(tile.getX(), tile.getY()))
				return true;
		return false;
	}
	
	@Override
	public boolean inside(Vector3i tile) {
		for (WorldTile t : tiles)
			if (t.isAt(tile.getX(), tile.getY()))
				return true;
		return false;
	}
}
