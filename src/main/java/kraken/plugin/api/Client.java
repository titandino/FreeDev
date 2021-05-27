package kraken.plugin.api;

/**
 * Provides access to various client state.
 */
public class Client {

    public static final int AT_LOGIN = 10;
    public static final int IN_LOBBY = 20;
    public static final int IN_GAME = 30;

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
     * @return The stat with the provided id, or NULL if one was not found.
     */
    public static native Stat getStatById(int id);

    /**
     * Retrieves a ConVar by id.
     *
     * @param id The id of the ConVar to search for.
     * @return The found ConVar with the provided id, or NULL if one was not found.
     */
    public static native ConVar getConVarById(int id);
}
