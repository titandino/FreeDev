package com.darkan.scripts.impl.aiomining;

import com.darkan.api.entity.MyPlayer;
import com.darkan.api.inter.Interfaces;
import com.darkan.api.util.Utils;
import com.darkan.scripts.Script;
import com.darkan.scripts.ScriptSkeleton;

@Script("AIO Mining")
public class AIOMining extends ScriptSkeleton {
	
	private OreData currentOre = OreData.COPPER;

	public AIOMining() {
		super("AIO Mining");
	}
	
	//SpotAnim 7164, 7165 = rockertunity
	
	//Ore box varbits
	//43190 = tin
	//43188 = copper
	
	/**
	 * Tin/Copper/Clay = Burthorpe mine
	 * Iron/Coal = Lumbridge Swamp southwest
	 * Mithril = Varrock southwest
	 * Adamant = Rimmington (clan camp bank) clan vex?
	 * Luminite = Dwarven mine/Anachronia (low lvl hardcore dwarven mine big nono)
	 * Runite = Mining guild
	 * Orichalcite = Mining guild
	 * Drakolith = Mining guild resource dungeon
	 * Necrite = Al Kharid resource dungeon/Uzer
	 * Phasmatite = Port phasmatys south
	 * Banite = Arctic habitat
	 * Light animica = Anachronia or Tumeken's Remnant realistically after Desert Treasure
	 * Dark animica = Empty throne room bank with arch journal
	 */

	@Override
	public boolean onStart() {
		return true;
	}

	@Override
	protected void loop() {
		if (Interfaces.getInventory().isFull()) {
			if (MyPlayer.getVars().getVarBit(currentOre.getVarbit()) < 120) {
				if (Interfaces.getInventory().clickItem("ore box", "Fill"))
					sleep(Utils.gaussian(3000, 1000));
			} else {
				
			}
			return;
		}
		
	}

	@Override
	public void paintImGui(long runtime) {
		printGenericXpGain(runtime);
	}

	@Override
	public void paintOverlay(long runtime) {
		
	}

	@Override
	public void onStop() {
		
	}
	
}
