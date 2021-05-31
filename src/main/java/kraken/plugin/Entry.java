package kraken.plugin;

import com.darkan.plugins.aiodiv.AIODiv;

import kraken.plugin.api.*;

/**
 * This acts as a simple wrapper for plugins, so that everything doesn't
 * have to be static.
 */
public class Entry {

    private static final AbstractPlugin plugin = new AIODiv();

    public static boolean onLoaded(PluginContext pluginContext) {
        return plugin.onLoaded(pluginContext);
    }

    public static int onLoop() {
        return plugin.onLoop();
    }

    public static void onPaint() {
        plugin.onPaint();
    }

}
