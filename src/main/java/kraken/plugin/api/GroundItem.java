package kraken.plugin.api;

import java.util.Objects;

/**
 * An item on the ground.
 */
public class GroundItem extends Entity {

    // internal values, attempting to use these will break the client

    private int internal10;

    /**
     * Do not make instances of this.
     */
    private GroundItem() { }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GroundItem that = (GroundItem) o;
        return internal10 == that.internal10;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), internal10);
    }
}
