package kraken.plugin.api;

/**
 * A provider of players.
 */
public class Players {

    private Players() { }

    /**
     * Retrieves the local player.
     *
     * @return The local player, or NULL if there is no local player.
     */
    public static native Player self();

    /**
     * Finds the closest player matching the provided filter.
     *
     * @param filter The filter players must pass through in order to be accepted.
     * @return The found player, or NULL if one was not found.
     */
    public static native Player closest(Filter<Player> filter);

}
