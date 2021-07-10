package kraken.plugin.api;

/**
 * An effect within the game world (glowing particles, glowing orbs, etc.)
 */
public class Effect extends Entity {

    /**
     * Do not make instances of this.
     */
    private Effect() { }

    /**
     * Retrieves the id of the effect.
     *
     * @return The id of the effect.
     */
    public native int getId();

}
