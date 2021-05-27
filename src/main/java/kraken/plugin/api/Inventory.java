package kraken.plugin.api;

/**
 * Provides access to the local player's inventory.
 */
public class Inventory {

    private Inventory() { }
    
    /**
     * Retrieves all items in the inventory.
     *
     * @return All items in the inventory.
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
