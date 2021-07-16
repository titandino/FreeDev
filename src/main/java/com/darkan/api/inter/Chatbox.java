package com.darkan.api.inter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Chatbox {
	
	private static boolean UPDATING = false;
	private static List<String> CHATS = new CopyOnWriteArrayList<>();
	private static IFComponent CHATBOX = new IFComponent(137, 85);
	
	public static void update() {
		if (UPDATING )
			return;
		UPDATING = true;
		new Thread(() -> {
			List<String> list = new ArrayList<>();
			for (IFSlot slot : CHATBOX.getSlots())
				list.add(slot.getText());
			CHATS.clear();
			CHATS.addAll(list);
			UPDATING = false;
		}).start();
	}
	
	public static List<String> getMessages() {
		return CHATS;
	}

}
