package com.darkan.scripts.impl;

import com.darkan.api.accessors.NPCs;
import com.darkan.api.accessors.WorldObjects;
import com.darkan.api.entity.MyPlayer;
import com.darkan.api.inter.Interfaces;
import com.darkan.api.inter.chat.Message;
import com.darkan.api.listeners.MessageListener;
import com.darkan.api.util.Timer;
import com.darkan.scripts.LoopScript;
import com.darkan.scripts.Script;

import kraken.plugin.api.ImGui;
import kraken.plugin.api.Time;

/**
 * This script will gather from divination wisps and convert the memories into either energy or experience
 * depending on the player's settings.
 * 
 * It is heavily commented to be used as an example of a simple looping script.
 * @author Trent
 */
@Script("AIO Wisp Gatherer") //Script tag is required for the script parser to add the script
public class AIOWispGathering extends LoopScript implements MessageListener {

	/**
	 * TODO
	 * Chronicle absorption prayer support
	 */
	
	private int startEnergy;					//Define the energy the player starts with
	private boolean captureChronicles = true;	//Boolean to store whether chronicles should be captured
	private Timer chronicleTimer = new Timer();	//Timer to check if the player has received a chronicle recently
	private Timer notYoursTimer = new Timer();	//Checking if the chronicle is not yours so ironmen don't sit there capturing
	
	public AIOWispGathering() {
		super("AIO Wisp Gathering", 300); 		//Set the script name to AIO Wisp Gathering that processes every 300 milliseconds
	}
	
	/**
	 * onStart gets called whenever the script tries to start. If it returns false, it will keep
	 * calling onStart until onStart returns true. So if the player isn't standing in the right spot
	 * for something, you could tell the user they need to be there for the script to start, etc.
	 */
	@Override
	public boolean onStart() {
		startEnergy = Interfaces.getInventory().countReg(" energy"); //Count the amount of energy items in the player's inventory.
		return true;
	}
	
	@Override
	public void loop() {
		/**
		 * If the player's inventory is full and they have successfully finds and interacts with the closest reachable object
		 * that contains the click option "Convert memories", then do nothing while their inventory still
		 * has memories in it. Sleep will continue for a minimum of 3 seconds and a maximum of 51.5 seconds.
		 */
		if (Interfaces.getInventory().isFull() && WorldObjects.interactClosestReachable("Convert memories")) {
			setState("Converting memories...");
			sleepWhile(3500, 51526, () -> Interfaces.getInventory().containsAnyReg(" memory"));
		/**
		 * Otherwise, if the player's inventory is not full
		 */
		} else if (!Interfaces.getInventory().isFull()) {
			/**
			 * If there is a chronicle fragment nearby and they have successfully tried to "Capture" it, then
			 * sleep while they are still moving or until they recieve a chatbox message saying the chronicle
			 * isn't theirs.
			 */
			if (canCaptureFragment() && NPCs.interactClosest("Chronicle fragment", "Capture")) {
				sleepWhile(3100, 73513, () -> notYoursTimer.time() > 30000 && MyPlayer.get().isMoving() && !Interfaces.getInventory().isFull());
				return; //Return from the loop so that nothing below here gets executed if they're capturing a chronicle.
			}
			
			/**
			 * If the player isn't capturing a chronicle fragment, continue down here to look for closest wisp.
			 * 
			 * If the player can find and interacts with an enriched wisp/spring, then wait until a chronicle fragment spawns,
			 * or aren't animating anymore, or until their inventory is full.
			 */
			setState("Finding closest wisp...");
			if (NPCs.interactClosestReachable("Harvest", npc -> npc.getName().contains("Enriched"))) {
				setState("Harvesting closest enriched wisp...");
				sleepWhile(3100, 73513, () -> !canCaptureFragment() && MyPlayer.get().isAnimationPlaying() && !Interfaces.getInventory().isFull());
			/**
			 * If there isn't an enriched wisp nearby, find and interact with the closest normal wisp by searching for the closest
			 * NPC that contains a "Harvest" option. If the player interacts with it successfully, then wait until an enriched
			 * wisp spawns, chronicle fragment spawns, player is animating, or player's inventory fills up.
			 */
			} else if (NPCs.interactClosestReachable("Harvest")) {
				setState("Harvesting closest wisp...");
				sleepWhile(3100, 73513, () -> !canCaptureFragment() && NPCs.getClosest(n -> n.getName().contains("Enriched")) == null && MyPlayer.get().isAnimationPlaying() && !Interfaces.getInventory().isFull());
			}
		}
	}
	
	/**
	 * Logic for checking if there is a chronicle fragment available to capture.
	 * 
	 * Summarized, it is as follows:
	 * If the capture chronicle setting is selected in the GUI, the player has not received a "not your chronicle fragment" message
	 * within the last 30 seconds, the player has received a chronicle escaping message within the last 10 seconds, the inventory
	 * is not full, and there is a "Chronicle fragment" npc nearby. Then return true. If any of those constraints fails, then
	 * do not try to capture a chronicle fragment.
	 */
	public boolean canCaptureFragment() {
		return captureChronicles && notYoursTimer.time() > 30000 && chronicleTimer.time() < 10000 && !Interfaces.getInventory().isFull() && NPCs.getClosest(n -> n.getName().contains("Chronicle fragment")) != null;
	}
	
	/**
	 * Message recieved comes from "implements MessageListener" at the top of the script.
	 * It allows the script to listen for chatbox messages and react to them accordingly.
	 */
	@Override
	public void onMessageReceived(Message message) {
		if (message.isGame() && message.getText().contains("escapes from the spring!"))
			chronicleTimer.click(); //Click the chronicle timer when a chronicle escapes from the spring so the script knows it belongs to the player
		if (message.isGame() && (message.getText().toLowerCase().contains("ironman") || message.getText().toLowerCase().contains("ironmen") || message.getText().toLowerCase().contains("ironwoman")))
			notYoursTimer.click();
	}

	@Override
	public void paintOverlay(long runtime) {
//		Paint.rect(0, 0, 300, 150, Color.GREEN);
//		Paint.rect(5, 5, 290, 140, Color.DARK_GRAY);
//		ImGui.freeText("Energy p/h: " + Time.perHour(runtime, Interfaces.getInventory().countReg(" energy") - startEnergy), new Vector2i(15, 20), Color.GREEN.getRGB());
	}

	@Override
	public void paintImGui(long runtime) {
		//Set whether the player should capture chronicle fragments based on the GUI.
		captureChronicles = ImGui.checkbox("Capture chronicle fragments", captureChronicles);
		//Print out the energy the player is gathering per hour
		ImGui.label("Energy p/h: " + Time.perHour(runtime, Interfaces.getInventory().countReg(" energy") - startEnergy));
		//Print out the experience the player has gained in all stats automatically calculated
		printGenericXpGain(runtime);
	}

	@Override
	public void onStop() {
		
	}
}
