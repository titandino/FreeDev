package com.darkan.api.entity;

import com.darkan.api.util.Utils;
import com.darkan.api.world.Interactable;
import com.darkan.api.world.WorldTile;
import com.darkan.cache.def.npcs.NPCDef;

import kraken.plugin.api.Actions;
import kraken.plugin.api.Npc;

public class NPC extends Entity implements Interactable {
	
	private Npc memNpc;
	private int id;
	
	public NPC(int id, WorldTile position) {
		super(position);
		this.id = id;
	}

	public NPC(Npc n) {
		super(n);
		this.memNpc = n;
		this.id = n.getId();
	}

	@Override
	public int getSize() {
		return getDef().size;
	}

	public int getId() {
		return id;
	}

	public NPCDef getDef() {
		return NPCDef.get(id, MyPlayer.getVars());
	}

	@Override
	public void interact(String string) {
		int op = getDef().getOpIdForName(string);
		if (op != -1)
			interact(op);
	}

	@Override
	public void interact(int option) {
		if (option < 0 || option > 5)
			return;
		Actions.menu(Actions.MENU_EXECUTE_NPC1 + option, memNpc.getServerIndex(), 0, 0, Utils.random(0, Integer.MAX_VALUE));
	}

	public String getName() {
		NPCDef def = getDef();
		if (def == null)
			return "";
		return def.name;
	}

	@Override
	public boolean hasOption(String string) {
		return getDef().containsOp(string);
	}
	
	@Override
	public String toString() {
		return "[" + id + ", " + getName() + ", " + getPosition() + "]";
	}

	@Override
	public String name() {
		return getName();
	}
}
