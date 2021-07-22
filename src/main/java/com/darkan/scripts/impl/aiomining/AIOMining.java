package com.darkan.scripts.impl.aiomining;

import com.darkan.api.accessors.SpotAnims;
import com.darkan.api.accessors.WorldObjects;
import com.darkan.api.entity.MyPlayer;
import com.darkan.api.inter.Interfaces;
import com.darkan.api.pathing.action.TraversalAction;
import com.darkan.api.util.Utils;
import com.darkan.api.world.SpotAnim;
import com.darkan.api.world.WorldObject;
import com.darkan.scripts.Script;
import com.darkan.scripts.ScriptSkeleton;

@Script("AIO Mining")
public class AIOMining extends ScriptSkeleton {
	
	private OreData currentOre = OreData.Copper;
	private TraversalAction path;

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
		if (Interfaces.getInventory().freeSlots() < Utils.random(2, 6)) {
			if (MyPlayer.getVars().getVarBit(currentOre.getVarbit()) < 120) {
				if (Interfaces.getInventory().clickItemReg("ore box", "Fill"))
					sleep(Utils.gaussian(2000, 1000));
				setState("Filling ore box...");
			} else {
				WorldObject furnace = WorldObjects.getClosest(obj -> obj.hasOption("Deposit-All (Into Metal Bank)"));
				if (furnace == null) {
					if (path == null)
						path = new TraversalAction(() -> WorldObjects.getClosest(obj -> obj.hasOption("Deposit-All (Into Metal Bank)")) != null, currentOre.getToBank());
					path.process();
					setState("Walking to bank...");
				} else {
					path = null;
					if (furnace.interact("Deposit-All (Into Metal Bank)"))
						sleepWhile(3000, 20000, () -> Interfaces.getInventory().freeSlots() < 8);
					currentOre = currentOre == OreData.Copper ? OreData.Tin : OreData.Copper;
					setState("Depositing into metal bank...");
				}
			}
		} else {
			SpotAnim rt = SpotAnims.getClosest(sa -> sa.getId() == 7164 || sa.getId() == 7165);
			
			WorldObject rock = WorldObjects.getClosestTo(rt != null ? rt.getPosition() : MyPlayer.getPosition(), obj -> obj.hasOption("Mine") && obj.getName().contains(currentOre.name()));
			
			if (rock == null) {
				if (path == null)
					path = new TraversalAction(() -> WorldObjects.getClosestReachable(obj -> obj.hasOption("Mine") && obj.getName().contains(currentOre.name())) != null, currentOre.getFromBank());
				path.process();
				setState("Walking to rocks...");
			} else {
				path = null;
				if (rock.interact("Mine"))
					sleepWhile(3000, Utils.gaussian(9000, 8000), () -> SpotAnims.getClosest(sa -> sa.getId() == 7164 || sa.getId() == 7164) == null && (MyPlayer.get().isMoving() || Interfaces.getInventory().freeSlots() > Utils.random(2, 6)));
				setState("Mining...");
			}
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
