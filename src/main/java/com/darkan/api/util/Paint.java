package com.darkan.api.util;

import java.awt.Color;

import kraken.plugin.api.ImGui;
import kraken.plugin.api.Vector2i;

public class Paint {
	
	public static void rect(int x, int y, int width, int height, Color color) {
		ImGui.freePoly4(new Vector2i(x, y + height), new Vector2i(x + width, y + height), new Vector2i(x + width, y), new Vector2i(x, y), color.getRGB());
	}

}
