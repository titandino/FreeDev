package kraken.plugin.api;

/**
 * A player within the game world.
 */
public class Player extends Entity {

    public static final int ADRENALINE = 5;

    /**
     * Do not make instances of this.
     */
    private Player() { }

    /**
     * Retrieves this player's server index.
     *
     * @return This player's server index.
     */
    public native int getServerIndex();

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

    /**
     * Retrieves the id of the playing animation.
     *
     * @return The id of the playing animation, or -1 if no animation is playing.
     */
    public native int getAnimationId();

    /**
     * Determines if this player has an animation playing.
     *
     * @return If this player has an animation playing.
     */
    public native boolean isAnimationPlaying();

    @Override
    public String toString() {
        return "Player{" +
                "serverIndex=" + getServerIndex() +
                '}';
    }
}
