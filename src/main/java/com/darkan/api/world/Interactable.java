package com.darkan.api.world;

public interface Interactable {
	boolean hasOption(String string);
	boolean interact(int option);
	boolean interact(String action);
}
