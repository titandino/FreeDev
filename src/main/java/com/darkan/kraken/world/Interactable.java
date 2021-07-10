package com.darkan.kraken.world;

public interface Interactable {
	boolean hasOption(String string);
	void interact(int option);
	void interact(String action);
}
