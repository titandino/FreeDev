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

    /**
     * Retrieves a stat by id.
     *
     * @param id The id of the stat to retrieve.
     * @return The state with the provided id, or NULL if one was not found.
     */
    public static native Stat getStatById(int id);

}
