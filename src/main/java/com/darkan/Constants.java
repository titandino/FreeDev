package com.darkan;

import com.darkan.api.inter.chat.Message;

public class Constants {
	
	public static final String[] SKILL_NAME = { "Attack", "Defence", "Strength", "Constitution", "Ranged", "Prayer", "Magic", "Cooking", "Woodcutting", "Fletching", "Fishing", "Firemaking", "Crafting", "Smithing", "Mining", "Herblore", "Agility",
			"Thieving", "Slayer", "Farming", "Runecrafting", "Hunter", "Construction", "Summoning", "Dungeoneering", "Divination", "Invention", "Archaeology" };

	public static final byte HAT = 0, CAPE = 1, AMULET = 2, WEAPON = 3, CHEST = 4, SHIELD = 5, LEGS = 7, HANDS = 9, FEET = 10, RING = 12, ARROWS = 13, AURA = 14, POCKET = 17;
	
	public static void main(String[] args) {
		String mes1 = "<col=ffffff>[<col=7fa9ff>21:33:25<col=ffffff>]</col> <img=7>News: sasukeboy123 has just achieved 120 Invention!";
		String mes2 = "<col=ffffff>[<col=7fa9ff>21:34:54<col=ffffff>]</col> <col=ffffff><img=13><col=ba061f>Hardcore Ironman</col> Maha oi:</col> <col=7fa9ff>Wow memes are cool</col>";
		System.out.println(new Message(mes1));
		System.out.println(new Message(mes2));
	}

	public static int getSkillIdForName(String name) {
		for(int i = 0; i < SKILL_NAME.length; i++) {
			if(SKILL_NAME[i].equalsIgnoreCase(name))
				return i;
		}
		return -1;
	}
}
