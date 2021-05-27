package kraken.plugin.api;

/**
 * A player within the game world.
 */
public class Player extends Entity {

    /**
     * Do not make instances of this.
     */
    Player() { }

    /**
     * Determines if this player is currently moving.
     * @return If this player is currently moving.
     */
    public native boolean isMoving();

    /**
     * Retrieves the fill of a status bar (0-1.)
     * @return The fill of a status bar.
     */
    public native float getStatusBarFill(int id);

}
