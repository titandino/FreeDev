package com.darkan;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;

import com.darkan.api.entity.MyPlayer;
import com.darkan.api.entity.VarManager;
import com.darkan.api.inter.Interfaces;
import com.darkan.api.inter.chat.Chatbox;
import com.darkan.api.inter.chat.Message;
import com.darkan.api.listeners.MessageListener;
import com.darkan.api.util.DebugFrame;
import com.darkan.api.util.Logger;
import com.darkan.api.util.Utils;
import com.darkan.scripts.LoopScript;
import com.darkan.scripts.Script;
import com.darkan.thread.DataUpdateThread;
import com.darkan.thread.DataUpdateThreadFactory;

import kraken.plugin.api.ConVar;
import kraken.plugin.api.Debug;
import kraken.plugin.api.ImGui;
import kraken.plugin.api.Plugin;
import kraken.plugin.api.PluginContext;

public final class BasePlugin extends Plugin {
	
	private static ScheduledExecutorService dataUpdateExecutor = Executors.newSingleThreadScheduledExecutor(new DataUpdateThreadFactory());
	
	private List<String> orderedNames;
	private Map<String, Class<? extends LoopScript>> scriptTypes = new HashMap<>();
	private Map<Class<? extends LoopScript>, LoopScript> scripts = new HashMap<>();
	
	private List<String> prevChats = new ArrayList<>();

	public boolean onLoaded(PluginContext pluginContext) {
		pluginContext.setName("FreeDev Scripts");
		loadScripts();
		VarManager.linkVarbits();
		if (Settings.getConfig().isDebug())
			SwingUtilities.invokeLater(() -> new DebugFrame().setVisible(true));
		dataUpdateExecutor.scheduleAtFixedRate(new DataUpdateThread(), 0, 50, TimeUnit.MILLISECONDS);
		return true;
	}
    
    @SuppressWarnings("unchecked")
	private void loadScripts() {
    	try {
	    	List<Class<?>> classes = Utils.getClassesWithAnnotation("com.darkan.scripts.impl", Script.class);
			for (Class<?> clazz : classes) {
				if (!Settings.getConfig().isDebug() && clazz.getAnnotationsByType(Script.class)[0].debugOnly())
					continue;
				scriptTypes.put(clazz.getAnnotationsByType(Script.class)[0].value(), (Class<? extends LoopScript>) clazz);
			}
			orderedNames = new ArrayList<>(scriptTypes.keySet());
		   	Collections.sort(orderedNames);
			Debug.log("Parsed scripts: " + scriptTypes.keySet().toString());
    	} catch (Exception e) {
    		Debug.log("Failed to load scripts: " + e.getMessage());
    		Logger.handle(e);
    	}
	}

	public int onLoop() {
		try {
	    	for (LoopScript script : scripts.values()) {
	    		if (script != null)
	    			script.process();
	    	}
	    	List<String> currentMessages = Chatbox.getMessages();
	    	String prevFirst = prevChats.size() > 0 ? prevChats.get(0) : "null";
	    	List<Message> newMessages = new ArrayList<>();
	    	for (String chat : currentMessages) {
	    		if (chat == null)
	    			continue;
	    		if (chat.equals(prevFirst))
	    			break;
	    		Message mes = new Message(chat.replace("<br>", ""));
	    		newMessages.add(mes);
	    		System.out.println("[CHAT]: " + mes);
	    	}
	    	if (!newMessages.isEmpty()) {
		    	for (LoopScript script : scripts.values()) {
		    		if (script != null && script instanceof MessageListener) {
		    			for (Message chat : newMessages) {
		    				try {
		    					((MessageListener) script).onMessageReceived(chat);
		    				} catch (Exception e) {
		    					Logger.handle(e);
		    				}
		    			}
		    		}
		    	}
	    	}
	    	prevChats.clear();
	    	prevChats.addAll(currentMessages);
	        return 36;
		} catch (Exception e) {
    		Logger.handle(e);
    		return 0;
    	}
    }

    public void onPaint() {
    	try {
	    	ImGui.label("Please select a script:");
	    	for (String scriptName : orderedNames) {
	    		Class<? extends LoopScript> script = scriptTypes.get(scriptName);
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
	    	
	    	for (LoopScript script : scripts.values()) {
	    		if (script != null)
	    			script.onPaint();
	    	}
    	} catch (Exception e) {
    		Logger.handle(e);
    	}
    }

    public void onPaintOverlay() {
    	try {
	    	for (LoopScript script : scripts.values()) {
	    		if (script != null)
	    			script.onPaintOverlay();
	    	}   
    	} catch (Exception e) {
    		Logger.handle(e);
    	}
    }

    public void onConVarChanged(ConVar conv, int oldValue, int newValue) {
    	if (conv.getId() != 3513)
    		System.out.println("Var changed: " + conv.getId() + " from " + oldValue + " -> " + newValue);
    	MyPlayer.getVars().setVar(conv.getId(), newValue);
    }

    public void onWidgetVisibilityChanged(int id, boolean visible) {
    	System.out.println("Interface visibility: " + id + " -> " + visible);
    	Interfaces.setVisibility(id, visible);
    }
}
