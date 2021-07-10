package kraken.plugin.api;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Provides various debugging utilities.
 */
public class Debug {

    private Debug() { }

    /**
     * Logs a message to the internal bot console.
     */
    public static native void log(String s);

	public static void logErr(Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		log(sw.toString());
	}
}
