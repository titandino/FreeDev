package com.darkan;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.darkan.api.inter.Interfaces;
import com.darkan.api.util.Util;
import com.darkan.cache.banditupsetversion.Cache;
import com.darkan.scripts.Script;
import com.darkan.scripts.ScriptSkeleton;
import kraken.plugin.AbstractPlugin;
import kraken.plugin.api.ConVar;
import kraken.plugin.api.Debug;
import kraken.plugin.api.ImGui;
import kraken.plugin.api.PluginContext;

public final class BasePlugin extends AbstractPlugin {
	
	private Map<String, Class<? extends ScriptSkeleton>> scriptTypes = new HashMap<>();
	private Map<Class<? extends ScriptSkeleton>, ScriptSkeleton> scripts = new HashMap<>();

    public boolean onLoaded(PluginContext pluginContext) {
    	pluginContext.setName("FreeDev Scripts");
    	loadScripts();
    	Cache.loadCache();
        return true;
    }
    
    @SuppressWarnings("unchecked")
	private void loadScripts() {
    	try {
	    	List<Class<?>> classes = Util.getClassesWithAnnotation("com.darkan.scripts", Script.class);
	    	Debug.log(classes.toString());
			for (Class<?> clazz : classes)
				scriptTypes.put(clazz.getAnnotationsByType(Script.class)[0].value(), (Class<? extends ScriptSkeleton>) clazz);
    	} catch (Exception e) {
    		Debug.log("Failed to load scripts: " + e.getMessage());
    		e.printStackTrace();
    	}
	}

	public int onLoop() {
    	for (ScriptSkeleton script : scripts.values()) {
    		if (script != null)
    			script.process();
    	}
        return 17;
    }

    public void onPaint() {
    	ImGui.label("Please select a script:");
    	for (String scriptName : scriptTypes.keySet()) {
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
    	Debug.log("Var changed: " + conv.getId() + " from " + oldValue + " -> " + newValue);
    }

    public void onWidgetVisibilityChanged(int id, boolean visible) {
    	Debug.log("Interface visibility: " + id + " -> " + visible);
    	Interfaces.setVisibility(id, visible);
    }
}
