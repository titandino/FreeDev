package kraken.plugin.api;

/**
 * A non-playable-character within the game world.
 */
public class Npc extends Entity {

    /**
     * Retrieves this NPC's health (0-1.)
     *
     * @return This NPC's health.
     */
    public native float getHealth();

    /**
     * Do not make instances of this.
     */
    Npc() { }

}
