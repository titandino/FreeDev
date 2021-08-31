package com.darkan.scripts.impl;

import java.security.SecureRandom;

import com.darkan.api.accessors.NPCs;
import com.darkan.api.accessors.WorldObjects;
import com.darkan.api.entity.MyPlayer;
import com.darkan.api.entity.NPC;
import com.darkan.api.inter.Interfaces;
import com.darkan.api.util.Utils;
import com.darkan.api.world.WorldObject;
import com.darkan.api.world.WorldTile;
import com.darkan.scripts.LoopScript;
import com.darkan.scripts.Script;

import kraken.plugin.api.Bank;
import kraken.plugin.api.ImGui;
import kraken.plugin.api.Move;
import kraken.plugin.api.Vector2i;

@Script("AIO Woodcut")
public class AIOWoodcut extends LoopScript {
    
    int selectedTree = 0;
    boolean drop = false;
    boolean bank = true;
    boolean fullInventory = true;
    boolean paused = true;
    WorldTile startTile = null;
    SecureRandom random = new SecureRandom();
	
	public AIOWoodcut() {
		super("AIO Woodcut", 200);
	}
	
	@Override
	public boolean onStart() {
	    startTile = MyPlayer.getPosition();
		return true;
	}
	
	@Override
	public void loop() {
	    if (paused)
	        return;
		if (MyPlayer.get().isMoving() || MyPlayer.get().isAnimationPlaying())
			return;
		
		if (Interfaces.getInventory().isFull())
		    fullInventory = true;
		else if (!Interfaces.getInventory().containsAnyReg("(?i)logs"))
		    fullInventory = false;
		
		if (!fullInventory) {
    		if ((getTimeSinceLastMoving() > 1200) && Utils.getDistanceTo(MyPlayer.getPosition(), startTile) > 24) {
    	        setState("Running back to starting area");
    		    Move.to(new Vector2i(startTile.getX() + (random.nextInt(10) - 5), startTile.getY() + (random.nextInt(10) - 5)));
    		    return;
    		}
            if (WorldObjects.interactClosestReachable("Chop", object -> object.getName().equals(getTree()) && object.hasOption("Chop"))) {
                setState("Cutting " + getTree());
                sleepWhile(Integer.MAX_VALUE, () -> MyPlayer.get().isAnimationPlaying());
                return;
            }
    		if (WorldObjects.interactClosestReachable("Chop down", object -> object.getName().equals(getTree()) && object.hasOption("Chop down"))) {
    		    setState("Cutting " + getTree());
    		    sleepWhile(Integer.MAX_VALUE, () -> MyPlayer.get().isAnimationPlaying());
    	        return;
    		}
    		if (WorldObjects.interactClosestReachable("Cut down", object -> object.getName().equals(getTree()) && object.hasOption("Cut down"))) {
    		    setState("Cutting " + getTree());
    		    sleepWhile(Integer.MAX_VALUE, () -> MyPlayer.get().isAnimationPlaying());
    	        return;
    		}
		}

		if (fullInventory) {
    		if (drop) {
    		    setState("Dropping items");
    		    Interfaces.getInventory().clickItemReg("(?i)logs", "Drop");
    	        sleep(Utils.gaussian(100, 300));
    		    return;
    		}
    		if (bank && Interfaces.getInventory().isFull() && Bank.isOpen()) {
    		    setState("Banking items");
    	        Interfaces.getBankInventory().clickItemReg("(?i)logs", "Deposit-All");
    	        sleep(Utils.gaussian(1200, 1200));
    	        return;
    		}
    		if (bank && Interfaces.getInventory().isFull() && !Bank.isOpen()) {
    		    setState("Running to bank");
    		    if (!openBank()) {
    		        System.out.println("Failed to open bank");
    		        bank = false;
    		        return;
    		    }
    		    sleepWhile(Integer.MAX_VALUE, () -> !Bank.isOpen());
    		    return;
    		}
		}
	}

	@Override
	public void paintOverlay(long runtime) {
		
	}

	@Override
	public void paintImGui(long runtime) {
	    ImGui.label("Current tree selected: " + getTree());
        ImGui.label("\n\n");
	    
	    ImGui.label("0 - Normal trees");
	    ImGui.label("1 - Oak trees");
	    ImGui.label("2 - Willow trees");
	    ImGui.label("3 - Maple trees");
	    ImGui.label("4 - Acadia trees");
	    ImGui.label("5 - Yew trees");
	    ImGui.label("6 - Ivy");
	    ImGui.label("7 - Magic trees");
	    ImGui.label("8 - Elder trees");
	    selectedTree = ImGui.intSlider("Select a tree: ", selectedTree, 0, 8);
	    
	    ImGui.label("\n\n");
	    
	    paused = ImGui.checkbox("Script paused", paused);
	    drop = ImGui.checkbox("Drop logs", drop || !bank);
	    bank = ImGui.checkbox("Bank", bank && !drop);
	    
	    ImGui.label("\n\n");
	    
		printGenericXpGain(runtime);
	}

	@Override
	public void onStop() {
		
	}
	
	private String getTree() {
	    return switch (selectedTree) {
	    case 0 -> "Tree";
	    case 1 -> "Oak";
        case 2 -> "Willow";
        case 3 -> "Maple Tree";
        case 4 -> "Acadia tree";
        case 5 -> "Yew";
        case 6 -> "Ivy";
        case 7 -> "Magic tree";
        case 8 -> "Elder tree";
        default -> throw new IllegalArgumentException("Unexpected value " + selectedTree + " is not mapped to a valid tree.");
	    };
	}
	
	private boolean openBank() {
	    WorldObject nearestBank = WorldObjects.getClosest(object -> object.hasOption("Bank") || object.getName().equals("Bank booth") || (object.getName().equals("Bank chest")));
	    NPC nearestBanker = NPCs.getClosest(object -> object.hasOption("Bank"));
	    
	    int objectDistance = -1, npcDistance = -1;
	    
	    if (nearestBank != null)
	        objectDistance = Utils.getRouteDistanceTo(MyPlayer.getPosition(), nearestBank);
	    if (nearestBanker != null)
	        npcDistance = Utils.getRouteDistanceTo(MyPlayer.getPosition(), nearestBanker);
	    
	    System.out.println("objDist: " + objectDistance);
	    System.out.println("npcDist: " + npcDistance);
	    
	    if (objectDistance != -1 && npcDistance != -1)
	        return (objectDistance < npcDistance ? (nearestBank.hasOption("Bank") ? nearestBank.interact("Bank") : nearestBank.interact("Use")) : nearestBanker.interact("Bank"));
	    else if (objectDistance != -1)
	        return nearestBank.hasOption("Bank") ? nearestBank.interact("Bank") : nearestBank.interact("Use");
	    else if (npcDistance != -1)
	        return nearestBanker.interact("Bank");
	    return false;
	}
}
