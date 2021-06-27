package kraken.plugin;

import com.darkan.plugins.pkavoider.PKAvoider;

import kraken.plugin.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * This acts as a simple wrapper for plugins, so that everything doesn't
 * have to be static.
 */
public class Entry {

    private static final AbstractPlugin plugin = new PKAvoider();

    private static void printStackTrace(String cause, Throwable t) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        t.printStackTrace(new PrintStream(bos));
        String whole = new String(bos.toByteArray());

        Debug.log("Error occurred during " + cause);
        for (String s : whole.split("\n")) {
            Debug.log(s);
        }
    }

    public static boolean onLoaded(PluginContext pluginContext) {
        try {
            return plugin.onLoaded(pluginContext);
        } catch (Throwable t) {
            printStackTrace("onLoaded", t);
            return false;
        }
    }

    public static int onLoop() {
        try {
            return plugin.onLoop();
        } catch (Throwable t) {
            printStackTrace("onLoop", t);
            return 60000;
        }
    }

    public static void onPaint() {
        try {
            plugin.onPaint();
        } catch (Throwable t) {
            printStackTrace("onPaint", t);
        }
    }

    public static void onPaintOverlay() {
        try {
            plugin.onPaintOverlay();
        } catch (Throwable t) {
            printStackTrace("onPaintOverlay", t);
        }
    }

    public static void onConVarChanged(ConVar conv, int oldValue, int newValue) {
        try {
            plugin.onConVarChanged(conv, oldValue, newValue);
        } catch (Throwable t) {
            printStackTrace("onConVarChanged", t);
        }
    }

    public static void onWidgetVisibilityChanged(int id, boolean visible) {
        try {
            plugin.onWidgetVisibilityChanged(id, visible);
        } catch (Throwable t) {
            printStackTrace("onWidgetVisibilityChanged", t);
        }
    }

}
