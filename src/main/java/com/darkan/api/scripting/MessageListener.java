package com.darkan.api.scripting;

import com.darkan.api.inter.chat.Message;

public interface MessageListener {

	void onMessageReceived(Message message);
}
