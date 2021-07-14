package com.darkan.scripts.impl;

import com.darkan.api.accessors.NPCs;
import com.darkan.api.accessors.WorldObjects;
import com.darkan.api.entity.NPC;
import com.darkan.api.inter.Interfaces;
import com.darkan.api.util.Utils;
import com.darkan.api.world.WorldObject;
import com.darkan.api.world.WorldTile;
import com.darkan.scripts.Script;
import com.darkan.scripts.ScriptSkeleton;

import kraken.plugin.api.ImGui;
import kraken.plugin.api.Player;
import kraken.plugin.api.Bank;

@Script("AIO Woodcut")
public class AIOWoodcut extends ScriptSkeleton {
    
    int selectedTree = 0;
    boolean fletch = false;
    boolean firemake = false;
    boolean drop = true;
    boolean bank = false;
	
	public AIOWoodcut() {
		super("AIO Woodcut", 250);
	}
	
	@Override
	public boolean onStart(Player self) {
		return true;
	}
	
	@Override
	public void loop(Player self) {
		if (self.isMoving())
			return;
		if (!Interfaces.getInventory().isFull() && WorldObjects.interactClosestReachable("Chop", object -> object.getName().equals(getTree()))) {
	        sleepWhile(Integer.MAX_VALUE, () -> self.isAnimationPlaying() && !Interfaces.getInventory().isFull());
	        return;
		}
		if (fletch && Interfaces.getInventory().isFull()) {
		    //do fletching logic
		    sleepWhile(Integer.MAX_VALUE, () -> self.isAnimationPlaying());
		    return;
		}
		if (firemake && Interfaces.getInventory().isFull()) {
		    boolean firemaking = true;
		    while (firemaking) {
		        if (!Interfaces.getInventory().containsAnyReg("logs"))
		            firemaking = false;
		        //do firemaking logic
		    }
		}
		
		if (drop) {
		    Interfaces.getInventory().clickItemReg("logs", "drop");
		    Interfaces.getInventory().clickItemReg("(u)", "drop");
		    sleepWhile(Integer.MAX_VALUE, () -> Interfaces.getInventory().containsAnyReg("logs") || Interfaces.getInventory().containsAnyReg("(u)"));
		}
		
		if (bank && Interfaces.getInventory().isFull() && !Bank.isOpen()) {
	        Interfaces.getBankInventory().clickItemReg("logs", "drop");
	        Interfaces.getBankInventory().clickItemReg("(u)", "drop");
	        sleepWhile(Integer.MAX_VALUE, () -> Interfaces.getBankInventory().containsAnyReg("logs") || Interfaces.getBankInventory().containsAnyReg("(u)"));
		}
		
		if (bank && Interfaces.getInventory().isFull() && !Bank.isOpen()) {
		    openBank(self);
		    sleepWhile(Integer.MAX_VALUE, () -> self.isMoving());
		}

	}

	@Override
	public void paintOverlay(long runtime) {
		
	}

	@Override
	public void paintImGui(long runtime) {
	    ImGui.label("Description: This script can be used to \n woodcut anywhere on RS. \n Supports fletching, firemaking, dropping, and banking \n assuming requirements are met. \n Script Will default to powerchopping as needed.");

	    ImGui.label("Current tree selected: " + getTree());
        ImGui.label("\n\n");
	    
	    ImGui.label("0 - Normal trees");
	    ImGui.label("1 - Oak trees");
	    ImGui.label("2 - Willow trees");
	    ImGui.label("3 - Maple trees");
	    ImGui.label("4 - Acacia trees");
	    ImGui.label("5 - Yew trees");
	    ImGui.label("6 - Magic trees");
	    ImGui.label("7 - Elder trees");
	    //ImGui.label("8 - Crystal trees");
	    selectedTree = ImGui.intSlider("Select a tree: ", selectedTree, 0, 7);
	    
	    ImGui.label("\n\n");
	    
	    fletch = ImGui.checkbox("Fletch logs", fletch && !firemake);
	    firemake = ImGui.checkbox("Firemake logs", firemake && (!fletch && !bank));
	    drop = ImGui.checkbox("Drop logs", (drop && (!firemake && !bank)) || (!firemake && !bank));
	    bank = ImGui.checkbox("Bank", bank && (!firemake && !drop));
	    
	    ImGui.label("\n\n");
	    
		printGenericXpGain(runtime);
	}

	@Override
	public void onStop() {
		
	}
	
	private String getTree() {
	    return switch (selectedTree) {
	    case 0 -> "Tree";
	    case 1 -> "Oak Tree";
        case 2 -> "Willow tree";
        case 3 -> "Maple tree";
        case 4 -> "Acacia tree";
        case 5 -> "Yew tree";
        case 6 -> "Magic tree";
        case 7 -> "Elder tree";
        default -> throw new IllegalArgumentException("Unexpected value " + selectedTree + " is not mapped to a valid tree.");
	    };
	}
	
	private boolean openBank(Player self) {
	    WorldObject nearestBank = WorldObjects.getClosest(object -> object.hasOption("Bank"));
	    NPC nearestBanker = NPCs.getClosest(object -> object.hasOption("Bank"));
	    
	    int objectDistance = -1, npcDistance = -1;
	    
	    if (nearestBank != null)
	        objectDistance = Utils.getRouteDistanceTo(new WorldTile(self.getGlobalPosition()), nearestBank);
	    if (nearestBanker != null)
	        npcDistance = Utils.getRouteDistanceTo(new WorldTile(self.getGlobalPosition()), nearestBanker);
	    
	    if (objectDistance != -1 && npcDistance != -1)
	        return (objectDistance < npcDistance ? nearestBank.interact("Bank") : nearestBanker.interact("Bank"));
	    else if (objectDistance != -1)
	        return nearestBank.interact("Bank");
	    else if (npcDistance != -1)
	        return nearestBanker.interact("Bank");
	    
	    return false;
	}
}
