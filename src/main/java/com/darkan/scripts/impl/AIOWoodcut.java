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
import com.darkan.scripts.Script;
import com.darkan.scripts.ScriptSkeleton;

import kraken.plugin.api.ImGui;
import kraken.plugin.api.Move;
import kraken.plugin.api.Vector2i;
import kraken.plugin.api.Bank;

@Script("AIO Woodcut")
public class AIOWoodcut extends ScriptSkeleton {
    
    int selectedTree = 0;
    boolean fletch = false;
    boolean firemake = false;
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
		else if (!Interfaces.getInventory().containsAnyReg("(?i)logs") && !Interfaces.getInventory().containsAnyReg("(u)")) //Leave the inventory as "full" until the player has finished fletching/firemaking/banking
		    fullInventory = false;
		
		if (!fullInventory) {
    		if ((getTimeSinceLastMoving() > 1200) && Utils.getDistanceTo(MyPlayer.getPosition(), startTile) > 24) {
    	        setState("Running back to starting area");
    		    Move.to(new Vector2i(startTile.getX() + (random.nextInt(10) - 5), startTile.getY() + (random.nextInt(10) - 5)));
    		    return;
    		}
    		if (WorldObjects.interactClosestReachable("Chop down", object -> object.getName().equals(getTree()) && object.hasOption("Chop down"))) {
    		    setState("Cutting " + getTree());
    		    sleepWhile(Integer.MAX_VALUE, () -> MyPlayer.get().isAnimationPlaying());
    	        return;
    		}
		}

		if (fullInventory) {
	        if (fletch && Interfaces.getInventory().isFull()) {
	            setState("Fletching");
	            //do fletching logic
	            sleepWhile(Integer.MAX_VALUE, () -> MyPlayer.get().isAnimationPlaying());
	            return;
	        }
    		if (firemake && Interfaces.getInventory().freeSlots() <= 1) {
    		    setState("Firemaking");
    		    WorldObject fire = WorldObjects.getClosest(object -> object.getName() == "Fire" && object.hasOption("Use"));//do firemaking logic
    		    if (fire == null) {
    		        Interfaces.getInventory().clickItemReg("(?i)logs", "Light");
    		        return;
    		    }
    		    //fire.interact("Use");
    		    //Interface: 1179, child: 17, slot: 17
                sleepWhile(Integer.MAX_VALUE, () -> MyPlayer.get().isAnimationPlaying());
    		    return;
    		}
    		if (drop) {
    		    setState("Dropping items");
    		    Interfaces.getInventory().clickItemReg("(?i)logs", "Drop");
    		    Interfaces.getInventory().clickItemReg("(u)", "Drop");
    	        sleep(Utils.gaussian(100, 300));
    		    return;
    		}
    		if (bank && Interfaces.getInventory().isFull() && Bank.isOpen()) {
    		    setState("Banking items");
    	        Interfaces.getBankInventory().clickItemReg("(?i)logs", "Deposit-All");
    	        Interfaces.getBankInventory().clickItemReg("(u)", "Deposit-All");
    	        return;
    		}
    		if (bank && Interfaces.getInventory().isFull() && !Bank.isOpen()) {
    		    setState("Running to bank");
    		    if (!openBank()) {
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
	    ImGui.label("6 - Magic trees");
	    ImGui.label("7 - Elder trees");
	    selectedTree = ImGui.intSlider("Select a tree: ", selectedTree, 0, 7);
	    
	    ImGui.label("\n\n");
	    
	    paused = ImGui.checkbox("Script paused", paused);
	    //fletch = ImGui.checkbox("Fletch logs", fletch && !firemake);
	    //firemake = ImGui.checkbox("Firemake logs", firemake && (!fletch && !bank));
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
	    case 1 -> "Oak";
        case 2 -> "Willow";
        case 3 -> "Maple Tree";
        case 4 -> "Acadia tree";
        case 5 -> "Yew";
        case 6 -> "Magic tree";
        case 7 -> "Elder tree";
        default -> throw new IllegalArgumentException("Unexpected value " + selectedTree + " is not mapped to a valid tree.");
	    };
	}
	
	private boolean openBank() {
	    WorldObject nearestBank = WorldObjects.getClosest(object -> object.hasOption("Bank") || object.getName().equals("Bank booth") || (object.getName().equals("Bank chest") || object.getName().equals("Deposit box")));
	    NPC nearestBanker = NPCs.getClosest(object -> object.hasOption("Bank"));
	    
	    int objectDistance = -1, npcDistance = -1;
	    
	    if (nearestBank != null)
	        objectDistance = Utils.getRouteDistanceTo(MyPlayer.getPosition(), nearestBank);
	    if (nearestBanker != null)
	        npcDistance = Utils.getRouteDistanceTo(MyPlayer.getPosition(), nearestBanker);
	    
	    if (objectDistance != -1 && npcDistance != -1)
	        return (objectDistance < npcDistance ? (nearestBank.hasOption("Bank") ? nearestBank.interact("Bank") : nearestBank.interact("Use")) : nearestBanker.interact("Bank"));
	    else if (objectDistance != -1)
	        return nearestBank.hasOption("Bank") ? nearestBank.interact("Bank") : nearestBank.interact("Use");
	    else if (npcDistance != -1)
	        return nearestBanker.interact("Bank");
	    return false;
	}
}
