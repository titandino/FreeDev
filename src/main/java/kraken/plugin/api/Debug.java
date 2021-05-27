package kraken.plugin.api;

/**
 * Provides various debugging utilities.
 */
public class Debug {

    private Debug() { }

    /**
     * Logs a message to the internal bot console.
     */
    public static native void log(String s);
}
