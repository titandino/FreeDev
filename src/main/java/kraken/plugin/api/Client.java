package kraken.plugin.api;

/**
 * Provides access to various client state.
 */
public class Client {

    private Client() { }

    /**
     * Retrieves the state that the client is currently in.
     *
     * @return The state that the client is currently in.
     */
    public static native int getState();

    /**
     * Determines if the client is in a loading state.
     *
     * @return If the client is in a loading state.
     */
    public static native boolean isLoading();

}
