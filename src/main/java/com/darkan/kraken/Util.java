package com.darkan.kraken;

import java.security.SecureRandom;
import java.util.Random;

public class Util {
	
	private static final Random RANDOM = new SecureRandom();
	
	public static int gaussian(int mean, int variance) {
		return (int) (RANDOM.nextGaussian() * Math.sqrt(variance) + mean);
	}

}
