package com.darkan.api.util;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.reflect.ClassPath;

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

	public static final int random(int min, int max) {
		final int n = Math.abs(max - min);
		return Math.min(min, max) + (n == 0 ? 0 : random(n));
	}
	
	public static final int random(int maxValue) {
		if (maxValue <= 0)
			return 0;
		return RANDOM.nextInt(maxValue);
	}
	
	public static List<Class<?>> getClassesWithAnnotation(String packageName, Class<? extends Annotation> annotation) throws ClassNotFoundException, IOException {
		ClassPath cp = ClassPath.from(Thread.currentThread().getContextClassLoader());
		List<Class<?>> classes = new ArrayList<Class<?>>();
		for (ClassPath.ClassInfo info : cp.getTopLevelClassesRecursive(packageName)) {
			if (!Class.forName(info.getName()).isAnnotationPresent(annotation))
				continue;
			classes.add(Class.forName(info.getName()));
		}
		return classes;
	}
}
