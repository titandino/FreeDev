package com.darkan;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.darkan.kraken.inter.Interfaces;
import com.darkan.scripts.ScriptSkeleton;
import com.darkan.scripts.aiorunespan.AIORunespan;
import com.darkan.scripts.aiowisp.AIOWispGathering;

import kraken.plugin.AbstractPlugin;
import kraken.plugin.api.ConVar;
import kraken.plugin.api.Debug;
import kraken.plugin.api.ImGui;
import kraken.plugin.api.PluginContext;

public final class BasePlugin extends AbstractPlugin {
	
	private Map<String, Class<? extends ScriptSkeleton>> scriptTypes = new HashMap<>();
	private Map<Class<? extends ScriptSkeleton>, ScriptSkeleton> scripts = new HashMap<>();

    public boolean onLoaded(PluginContext pluginContext) {
    	pluginContext.setName("Darkan Scripts");
    	loadScripts();
        return true;
    }
    
    private void loadScripts() {
    	scriptTypes.put("AIO Energy Gatherer", AIOWispGathering.class);
    	scriptTypes.put("AIO Runespan", AIORunespan.class);
//    	try {
//	    	List<Class<?>> classes = Utils.getClassesWithAnnotation("", Script.class);
//	    	Debug.log(classes.toString());
//			for (Class<?> clazz : classes)
//				scriptTypes.put(clazz.getAnnotationsByType(Script.class)[0].value(), (Class<? extends ScriptSkeleton>) clazz);
//    	} catch (Exception e) {
//    		Debug.log("Failed to load scripts: " + e.getMessage());
//    		e.printStackTrace();
//    	}
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