package kraken.plugin.api;

/**
 * An item on the ground.
 */
public class GroundItem extends Entity {

    /**
     * Retrieves the id of the item stack.
     *
     * @return The id of the item stack.
     */
    public native int getId();

    /**
     * Retrieves the amount of item in the item stack.
     *
     * @return The amount of item in the item stack.
     */
    public native int getAmount();

}
