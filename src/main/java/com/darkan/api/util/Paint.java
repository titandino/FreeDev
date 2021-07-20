package com.darkan.api.util;

import java.awt.Color;

import com.darkan.api.world.WorldTile;

import kraken.plugin.api.Client;
import kraken.plugin.api.ImGui;
import kraken.plugin.api.Vector2i;
import kraken.plugin.api.Vector3;

public class Paint {
	
	public static void rect(int x, int y, int width, int height, Color color) {
		ImGui.freePoly4(new Vector2i(x, y + height), new Vector2i(x + width, y + height), new Vector2i(x + width, y), new Vector2i(x, y), color.getRGB());
	}
	
	public static boolean drawTile(WorldTile tile, Color color, boolean fill) {
		Vector3[] tileCorners = new Vector3[] { new Vector3(tile.getX(), tile.getY() + 0.5f, 0), new Vector3(tile.getX() + 0.5f, tile.getY() + 0.5f, 0), new Vector3(tile.getX() + 0.5f, tile.getY(), 0), new Vector3(tile.getX(), tile.getY(), 0) };
		Vector2i[] tileCornersProj = new Vector2i[4];
		if (Client.multiWorldToScreen(tileCorners, tileCornersProj)) {
			ImGui.freePoly4(tileCornersProj[0], tileCornersProj[1], tileCornersProj[2], tileCornersProj[3], color.getRGB());
			return true;
		}
		return false;
	}

}
