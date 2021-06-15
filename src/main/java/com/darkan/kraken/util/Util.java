package com.darkan.kraken.util;

import java.security.SecureRandom;
import java.util.Random;

public class Util {
	
	private static final Random RANDOM = new SecureRandom();
	
	public static int gaussian(int mean, int variance) {
		return (int) (RANDOM.nextGaussian() * Math.sqrt(variance) + mean);
	}
	
	public static final int getDistanceI(int coordX1, int coordY1, int coordX2, int coordY2) {
		int deltaX = coordX2 - coordX1;
		int deltaY = coordY2 - coordY1;
		return ((int) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2)));
	}


}
