package com.darkan.plugins.aiodiv;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum DivConfig {
	PALE(new Integer[] { 18150, 18173 }, new Integer[] { }),
	FLICKERING(new Integer[] { 18151, 18174 }, new Integer[] { 18152 }),
	BRIGHT(new Integer[] { 18153, 18176 }, new Integer[] { 18154 }),
	GLOWING(new Integer[] { 18155, 18178 }, new Integer[] { 18156 }),
	SPARKLING(new Integer[] { 18157, 18180 }, new Integer[] { 18158 }),
	GLEAMING(new Integer[] { 18159, 18182 }, new Integer[] { 18160 }),
	VIBRANT(new Integer[] { 18161, 18184 }, new Integer[] { 18162 }),
	LUSTROUS(new Integer[] { 18163, 18186 }, new Integer[] { 18164 }),
	ELDER(new Integer[] { 13614, 13616 }, new Integer[] { 13615, 13627 }),
	BRILLIANT(new Integer[] { 18165, 18188 }, new Integer[] { 18166 }),
	RADIANT(new Integer[] { 18167, 18190 }, new Integer[] { 18168 }),
	LUMINOUS(new Integer[] { 18169, 18192 }, new Integer[] { 18170 }),
	INCANDESCENT(new Integer[] { 18171, 18194 }, new Integer[] { 38186 }),
	CURSED(new Integer[] { 23159, 23160 }, new Integer[] { 23161, 23162 });
	
	private Set<Integer> normalNpcs;
	private Set<Integer> enrichedNpcs;
	
	private DivConfig(Integer[] normalNpcs, Integer[] enrichedNpcs) {
		this.normalNpcs = new HashSet<>(Arrays.asList(normalNpcs));
		this.enrichedNpcs = new HashSet<>(Arrays.asList(enrichedNpcs));
	}

	public Set<Integer> getNormalNpcs() {
		return normalNpcs;
	}

	public Set<Integer> getEnrichedNpcs() {
		return enrichedNpcs;
	}
}
