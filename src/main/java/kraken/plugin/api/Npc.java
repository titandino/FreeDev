package kraken.plugin.api;

/**
 * A non-playable-character within the game world.
 */
public class Npc extends Entity {

    /**
     * Retrieves this NPC's server index.
     *
     * @return This NPC's server index.
     */
    public native int getServerIndex();

    /**
     * Retrieves this NPC's health.
     *
     * @return This NPC's health.
     */
    public native int getHealth();

    /**
     * Do not make instances of this.
     */
    Npc() { }

}
