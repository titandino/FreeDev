package kraken.plugin.api;

/**
 * A provider of NPCs.
 */
public class Npcs {

    private Npcs() { }

    /**
     * Finds the closest NPC matching the provided filter.
     *
     * @param filter The filter NPCs must pass through in order to be accepted.
     * @return The found NPC, or NULL if one was not found.
     */
    public static native Npc closest(Filter<Npc> filter);

}
