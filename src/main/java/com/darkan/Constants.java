package com.darkan;

import com.darkan.cache.def.items.ItemDef;

public class Constants {
	
	public static final String[] SKILL_NAME = { "Attack", "Defence", "Strength", "Constitution", "Ranged", "Prayer", "Magic", "Cooking", "Woodcutting", "Fletching", "Fishing", "Firemaking", "Crafting", "Smithing", "Mining", "Herblore", "Agility",
			"Thieving", "Slayer", "Farming", "Runecrafting", "Hunter", "Construction", "Summoning", "Dungeoneering", "Divination", "Invention", "Archaeology" };

	
	public static void main(String[] args) {
//		Region region = Region.getRegion(new WorldTile(2230, 9116).getRegionId());
//		for (WorldObject obj : region.getObjectList())
//			System.out.println(obj);
		
		ItemDef def = ItemDef.get(15333);
		System.out.println("Skill required: " + def.getCreationLevelReq() + " " + SKILL_NAME[def.getCreationSkillId()]);
		System.out.println("Awards " + def.getCreationExperience() + " experience");
		System.out.println("Materials required: " + ItemDef.get(15333).getMaterials());
	}
}
