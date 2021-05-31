package com.darkan.plugins.aiodiv;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum DivConfig {
	PALE(29313, new Integer[] { 18150, 18173 }, new Integer[] { }),
	FLICKERING(29314, new Integer[] { 18151, 18174 }, new Integer[] { 18152 }),
	BRIGHT(29315, new Integer[] { 18153, 18176 }, new Integer[] { 18154 }),
	GLOWING(29316, new Integer[] { 18155, 18178 }, new Integer[] { 18156 }),
	SPARKLING(29317, new Integer[] { 18157, 18180 }, new Integer[] { 18158 }),
	GLEAMING(29318, new Integer[] { 18159, 18182 }, new Integer[] { 18160 }),
	VIBRANT(29319, new Integer[] { 18161, 18184 }, new Integer[] { 18162 }),
	LUSTROUS(29320, new Integer[] { 18163, 18186 }, new Integer[] { 18164 }),
	ELDER(31312, new Integer[] { 13614, 13616 }, new Integer[] { 13615, 13627 }),
	BRILLIANT(29321, new Integer[] { 18165, 18188 }, new Integer[] { 18166 }),
	RADIANT(29322, new Integer[] { 18167, 18190 }, new Integer[] { 18168 }),
	LUMINOUS(29323, new Integer[] { 18169, 18192 }, new Integer[] { 18170 }),
	INCANDESCENT(29324, new Integer[] { 18171, 18194 }, new Integer[] { 18172 }),
	CURSED(37941, new Integer[] { 23159, 23160 }, new Integer[] { 23161, 23162 });
	
	private int energyId;
	private Set<Integer> normalNpcs;
	private Set<Integer> enrichedNpcs;
	
	private DivConfig(int energyId, Integer[] normalNpcs, Integer[] enrichedNpcs) {
		this.energyId = energyId;
		this.normalNpcs = new HashSet<>(Arrays.asList(normalNpcs));
		this.enrichedNpcs = new HashSet<>(Arrays.asList(enrichedNpcs));
	}
	
	public int getEnergyId() {
		return energyId;
	}

	public Set<Integer> getNormalNpcs() {
		return normalNpcs;
	}

	public Set<Integer> getEnrichedNpcs() {
		return enrichedNpcs;
	}
}
