package com.darkan.scripts.impl;

import com.darkan.api.accessors.NPCs;
import com.darkan.api.accessors.WorldObjects;
import com.darkan.api.entity.MyPlayer;
import com.darkan.api.inter.Interfaces;
import com.darkan.api.inter.chat.Message;
import com.darkan.api.scripting.MessageListener;
import com.darkan.api.util.Timer;
import com.darkan.scripts.Script;
import com.darkan.scripts.ScriptSkeleton;

import kraken.plugin.api.Client;
import kraken.plugin.api.ImGui;
import kraken.plugin.api.Time;

@Script("AIO Wisp Gatherer")
public class AIOWispGathering extends ScriptSkeleton implements MessageListener {

	/**
	 * TODO
	 * Chronicle absorption prayer support
	 */
		
	private int startXp;
	private int startEnergy;
	private boolean captureChronicles = true;
	private Timer chronicleTimer = new Timer();
	private Timer notYoursTimer = new Timer();
	
	public AIOWispGathering() {
		super("AIO Wisp Gathering", 300);
	}
	
	@Override
	public boolean onStart() {
		startXp = Client.getStatById(Client.DIVINATION).getXp();
		startEnergy = Interfaces.getInventory().countReg(" energy");
		return true;
	}
	
	@Override
	public void loop() {
		if (Interfaces.getInventory().isFull() && WorldObjects.interactClosestReachable("Convert memories")) {
			setState("Converting memories...");
			sleepWhile(3500, 51526, () -> Interfaces.getInventory().containsAnyReg(" memory"));
		} else if (!Interfaces.getInventory().isFull()) {
			if (captureChronicles && chronicleTimer.time() < 10000 && notYoursTimer.time() > 30000 && NPCs.interactClosest("Chronicle fragment", "Capture")) {
				sleepWhile(3100, 73513, () -> notYoursTimer.time() > 30000 && MyPlayer.get().isMoving() && !Interfaces.getInventory().isFull());
				return;
			}
			setState("Finding closest wisp...");
			if (NPCs.interactClosestReachable("Harvest", npc -> npc.getName().contains("Enriched"))) {
				setState("Harvesting closest enriched wisp...");
				sleepWhile(3100, 73513, () -> MyPlayer.get().isAnimationPlaying() && !Interfaces.getInventory().isFull());
			} else if (NPCs.interactClosestReachable("Harvest")) {
				setState("Harvesting closest wisp...");
				sleepWhile(3100, 73513, () -> NPCs.getClosest(n -> n.getName().contains("Enriched")) == null && MyPlayer.get().isAnimationPlaying() && !Interfaces.getInventory().isFull());
			}
		}
	}
	
	@Override
	public void onMessageReceived(Message message) {
		if (message.isGame() && message.getText().contains("escapes from the spring!"))
			chronicleTimer.click();
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
		captureChronicles = ImGui.checkbox("Capture chronicle fragments", captureChronicles);
		ImGui.label("Energy p/h: " + Time.perHour(runtime, Interfaces.getInventory().countReg(" energy") - startEnergy));
		ImGui.label("XP p/h: " + Time.perHour(runtime, Client.getStatById(Client.DIVINATION).getXp() - startXp));
	}

	@Override
	public void onStop() {
		
	}
}
