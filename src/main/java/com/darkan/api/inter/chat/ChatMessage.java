package com.darkan.api.inter.chat;

public class ChatMessage {
	
	private String timeStamp;
	private String color;
	private String playerName;
	private String message;
	
	public ChatMessage(String whole) {
		//<col=ffffff>[<col=7fa9ff>21:33:25<col=ffffff>]</col> <img=7>News: sasukeboy123 has just achieved 120 Invention!
		//<col=ffffff>[<col=7fa9ff>21:34:54<col=ffffff>]</col> <col=ffffff><img=13><col=ba061f>Hardcore Ironman</col> Maha oi:</col> <col=7fa9ff>Wow memes are cool</col>
		//if (whole.startsWith("<col=ffffff>[<col=7fa9ff>"))
			
		
	}
	
	public String getTimeStamp() {
		return timeStamp;
	}

	public String getColor() {
		return color;
	}

	public String getPlayerName() {
		return playerName;
	}

	public String getMessage() {
		return message;
	}
}
