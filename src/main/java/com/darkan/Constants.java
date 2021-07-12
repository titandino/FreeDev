package com.darkan;

public class Constants {
	
	public static final String[] SKILL_NAME = { "Attack", "Defence", "Strength", "Constitution", "Ranged", "Prayer", "Magic", "Cooking", "Woodcutting", "Fletching", "Fishing", "Firemaking", "Crafting", "Smithing", "Mining", "Herblore", "Agility",
			"Thieving", "Slayer", "Farming", "Runecrafting", "Hunter", "Construction", "Summoning", "Dungeoneering", "Divination", "Invention", "Archaeology" };

	
	public static void main(String[] args) {
		String tempDesc = "Mememe mesmimismeim seim s (100%) 2930 sdjf s";
		System.out.println(Integer.valueOf(tempDesc.substring(tempDesc.indexOf('(')+1, tempDesc.indexOf('%'))));
	}
}
