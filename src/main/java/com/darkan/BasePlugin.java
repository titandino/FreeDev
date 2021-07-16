package com.darkan;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

import com.darkan.api.accessors.NPCs;
import com.darkan.api.accessors.WorldObjects;
import com.darkan.api.entity.MyPlayer;
import com.darkan.api.entity.VarManager;
import com.darkan.api.inter.Interfaces;
import com.darkan.api.inter.chat.Chatbox;
import com.darkan.api.scripting.MessageListener;
import com.darkan.api.util.DebugFrame;
import com.darkan.api.util.Logger;
import com.darkan.api.util.Utils;
import com.darkan.scripts.Script;
import com.darkan.scripts.ScriptSkeleton;

import kraken.plugin.AbstractPlugin;
import kraken.plugin.api.ConVar;
import kraken.plugin.api.Debug;
import kraken.plugin.api.ImGui;
import kraken.plugin.api.PluginContext;

public final class BasePlugin extends AbstractPlugin {
	
	private List<String> orderedNames;
	private Map<String, Class<? extends ScriptSkeleton>> scriptTypes = new HashMap<>();
	private Map<Class<? extends ScriptSkeleton>, ScriptSkeleton> scripts = new HashMap<>();
	
	private List<String> prevChats = new ArrayList<>();

    public boolean onLoaded(PluginContext pluginContext) {
    	pluginContext.setName("FreeDev Scripts");
    	loadScripts();
    	VarManager.linkVarbits();
    	SwingUtilities.invokeLater(() -> new DebugFrame().setVisible(true));
        return true;
    }
    
    @SuppressWarnings("unchecked")
	private void loadScripts() {
    	try {
	    	List<Class<?>> classes = Utils.getClassesWithAnnotation("com.darkan.scripts.impl", Script.class);
			for (Class<?> clazz : classes)
				scriptTypes.put(clazz.getAnnotationsByType(Script.class)[0].value(), (Class<? extends ScriptSkeleton>) clazz);
			orderedNames = new ArrayList<>(scriptTypes.keySet());
		   	Collections.sort(orderedNames);
			Debug.log("Parsed scripts: " + scriptTypes.keySet().toString());
    	} catch (Exception e) {
    		Debug.log("Failed to load scripts: " + e.getMessage());
    		Logger.handle(e);
    	}
	}

	public int onLoop() {
    	for (ScriptSkeleton script : scripts.values()) {
    		if (script != null)
    			script.process();
    	}
    	String prevFirst = prevChats.size() > 0 ? prevChats.get(0) : "null";
    	List<String> newChats = new ArrayList<>();
    	for (String chat : Chatbox.getMessages()) {
    		if (chat.equals(prevFirst))
    			break;
    		newChats.add(chat);
    		System.out.println("New chat message: " + chat);
    	}
    	if (!newChats.isEmpty()) {
	    	for (ScriptSkeleton script : scripts.values()) {
	    		if (script != null && script instanceof MessageListener) {
	    			for (String chat : newChats)
	    				((MessageListener) script).onMessageReceived(chat);
	    		}
	    	}
    	}
    	prevChats.clear();
    	prevChats.addAll(Chatbox.getMessages());
    	Chatbox.update();
		WorldObjects.update();
		NPCs.update();
        return 36;
    }

    public void onPaint() {
    	ImGui.label("Please select a script:");
    	for (String scriptName : orderedNames) {
    		Class<? extends ScriptSkeleton> script = scriptTypes.get(scriptName);
    		boolean currRunning = scripts.get(script) != null;
    		boolean running = ImGui.checkbox(scriptName, currRunning);
    		if (currRunning && !running) {
    			scripts.get(script).stop();
    			scripts.remove(script);
    		} else if (!currRunning && running) {
    			try {
					scripts.put(script, script.getDeclaredConstructor().newInstance());
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
					Debug.log("Error constructing script: " + script.getSimpleName());
					e.printStackTrace();
				}
    		}
    	}
    	
    	for (ScriptSkeleton script : scripts.values()) {
    		if (script != null)
    			script.onPaint();
    	}    	
    }

    public void onPaintOverlay() {
    	for (ScriptSkeleton script : scripts.values()) {
    		if (script != null)
    			script.onPaintOverlay();
    	}    	
    }

    public void onConVarChanged(ConVar conv, int oldValue, int newValue) {
    	//MyPlayer.getVars().checkVarUpdates();
    	Debug.log("Var changed: " + conv.getId() + " from " + oldValue + " -> " + newValue);
    	MyPlayer.getVars().setVar(conv.getId(), newValue);
    }

    public void onWidgetVisibilityChanged(int id, boolean visible) {
    	Debug.log("Interface visibility: " + id + " -> " + visible);
    	Interfaces.setVisibility(id, visible);
    }
}
