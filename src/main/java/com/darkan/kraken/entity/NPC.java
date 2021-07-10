package com.darkan.kraken.entity;

import com.darkan.cache.banditupsetversion.Cache;
import com.darkan.cache.banditupsetversion.dto.NPCInfo;
import com.darkan.kraken.util.Util;
import com.darkan.kraken.world.Interactable;
import com.darkan.kraken.world.WorldTile;

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

	public NPCInfo getDef() {
		return Cache.getNPC(id);
	}

	@Override
	public void interact(String string) {
		int op = getDef().getOption(string);
		if (op != -1)
			interact(op);
	}

	@Override
	public void interact(int option) {
		if (option < 0 || option > 5)
			return;
		Actions.menu(Actions.MENU_EXECUTE_NPC1 + option, memNpc.getServerIndex(), 0, 0, Util.random(0, Integer.MAX_VALUE));
	}

	public String getName() {
		return getDef().name;
	}

	@Override
	public boolean hasOption(String string) {
		return getDef().hasOption(string);
	}
	
	@Override
	public String toString() {
		return "[" + id + ", " + getName() + ", " + getPosition() + "]";
	}
}
