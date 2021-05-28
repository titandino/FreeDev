package kraken.plugin.api;

/**
 * A non-playable-character within the game world.
 */
public class Npc extends Entity {

    /**
     * Do not make instances of this.
     */
    Npc() { }

    /**
     * Retrieves this NPC's server index.
     *
     * @return This NPC's server index.
     */
    public native int getServerIndex();

    /**
     * Rerieves this NPC's id.
     *
     * @return This NPC's id.
     */
    public native int getId();

    /**
     * Retrieves this NPC's health.
     *
     * @return This NPC's health.
     */
    public native int getHealth();

    /**
     * Interacts with this NPC.
     */
    public void interact(int type) {
        Actions.entity(this, type);
    }
}
