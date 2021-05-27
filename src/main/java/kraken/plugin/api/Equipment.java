package kraken.plugin.api;

/**
 * Provides access to the local player's equipment.
 */
public class Equipment {

    private Equipment() { }

    /**
     * Retrieves all equipped items.
     *
     * @return All equipped items.
     */
    public static native WidgetItem[] getItems();

    /**
     * Counts the number of items with the provided id.
     *
     * @param id The id of the item to search for.
     * @return The number of items with the provided id.
     */
    public static native int count(int id);

    /**
     * Finds the first widget item that passes the provided filter.
     *
     * @param filter The filter that items must pass through in order to be accepted.
     * @return The first item that passed the filter.
     */
    public static native WidgetItem first(Filter<WidgetItem> filter);

}
