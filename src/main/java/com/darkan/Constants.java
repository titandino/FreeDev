package com.darkan;

import com.darkan.api.world.WorldObject;
import com.darkan.api.world.WorldTile;
import com.darkan.cache.def.maps.Region;
import com.darkan.cache.def.objects.ObjectDef;

public class Constants {
	
	public static final String[] SKILL_NAME = { "Attack", "Defence", "Strength", "Constitution", "Ranged", "Prayer", "Magic", "Cooking", "Woodcutting", "Fletching", "Fishing", "Firemaking", "Crafting", "Smithing", "Mining", "Herblore", "Agility",
			"Thieving", "Slayer", "Farming", "Runecrafting", "Hunter", "Construction", "Summoning", "Dungeoneering", "Divination", "Invention", "Archaeology" };

	
	public static void main(String[] args) {
//		Region region = Region.getRegion(new WorldTile(2230, 9116).getRegionId());
//		for (WorldObject obj : region.getObjectList())
//			System.out.println(obj);
		
		System.out.println(ObjectDef.get(111376));
	}
}
