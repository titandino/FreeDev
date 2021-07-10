package com.darkan.api.world;

public interface Interactable {
	boolean hasOption(String string);
	void interact(int option);
	void interact(String action);
}