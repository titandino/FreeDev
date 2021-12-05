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
package com.darkan.scripts.impl.aiomining;

import com.darkan.api.pathing.action.node.TraversalNodeList;
import com.darkan.api.pathing.action.node.impl.ObjectNode;
import com.darkan.api.pathing.action.node.impl.PathNode;
import com.darkan.api.util.Area;
import com.darkan.api.world.WorldTile;

public enum OreData {
	/** TODO
	 * Silver = Falador west (bank in falador using shortcut object/pathnode combo)
	 * Luminite = Dwarven mine/Anachronia (low lvl hardcore dwarven mine big nono)
	 * Runite = Mining guild
	 * Orichalcite = Mining guild
	 * Drakolith = Mining guild resource dungeon
	 * Necrite = Al Kharid resource dungeon/Uzer
	 * Phasmatite = Port phasmatys south
	 * Banite = Arctic habitat
	 * Light animica = Anachronia or Tumeken's Remnant realistically after Desert Treasure
	 * Dark animica = Empty throne room bank with arch journal
	 */
	
	
	Copper(43188, new TraversalNodeList(new ObjectNode(66876, "Enter", new Area(2290, 4513, 2295, 4520))), new TraversalNodeList(new ObjectNode(67002, "Exit to", new Area(2874, 3499, 2878, 3505)))),
	Tin(43190, new TraversalNodeList(new ObjectNode(66876, "Enter", new Area(2290, 4513, 2295, 4520))), new TraversalNodeList(new ObjectNode(67002, "Exit to", new Area(2874, 3499, 2878, 3505)))),
	Iron(43192, new TraversalNodeList(new PathNode(new WorldTile(3187, 3425, 0), new WorldTile(3182, 3371, 0))), new TraversalNodeList(new PathNode(new WorldTile(3182, 3371, 0), new WorldTile(3187, 3425, 0)))),
	Coal(0000/*TODO*/, null, null),
	Mithril(0000/*TODO*/, new TraversalNodeList(new PathNode(new WorldTile(3187, 3425, 0), new WorldTile(3182, 3371, 0))), new TraversalNodeList(new PathNode(new WorldTile(3182, 3371, 0), new WorldTile(3187, 3425, 0)))),
	Adamant(0000/*TODO*/, new TraversalNodeList(new PathNode(new WorldTile(2997, 3144, 0), new WorldTile(2977, 3235, 0))), new TraversalNodeList(new PathNode(new WorldTile(2977, 3235, 0), new WorldTile(2997, 3144, 0)))),
	Gold(0000/*TODO*/, new TraversalNodeList(new PathNode(new WorldTile(2997, 3144, 0), new WorldTile(2977, 3235, 0))), new TraversalNodeList(new PathNode(new WorldTile(2977, 3235, 0), new WorldTile(2997, 3144, 0)))),
	;
	
	private int varbit;
	private TraversalNodeList fromBank;
	private TraversalNodeList toBank;
	
	private OreData(int varbit, TraversalNodeList fromBank, TraversalNodeList toBank) {
		this.varbit = varbit;
		this.fromBank = fromBank;
		this.toBank = toBank;
	}

	public int getVarbit() {
		return varbit;
	}

	public TraversalNodeList getToBank() {
		return toBank;
	}

	public TraversalNodeList getFromBank() {
		return fromBank;
	}
}
