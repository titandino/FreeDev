package kraken.plugin.api;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Provides various debugging utilities.
 */
public class Debug {

    private Debug() { }

    /**
     * Logs a message to the internal bot console.
     */
    public static native void log(String s);

    /**
     * Prints a stack trace to the bot console.
     */
    public static void printStackTrace(String cause, Throwable t) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        t.printStackTrace(new PrintStream(bos));
        String whole = new String(bos.toByteArray());

        Debug.log("Error occurred during " + cause);
        for (String s : whole.split("\n")) {
            Debug.log(s);
        }
    }

}
