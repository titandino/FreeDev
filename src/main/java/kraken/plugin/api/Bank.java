package kraken.plugin.api;

/**
 * Provides access to the local player's bank.
 */
public class Bank {

    private Bank() { }

    /**
     * Determines if the bank widget is open.
     *
     * @return If the bank widget is open.
     */
    public static native boolean isOpen();

    /**
     * Retrieves all items in the bank.
     *
     * @return All items in the bank.
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

    /**
     * Withdraws some items from the bank.
     *
     * @param filter The filter items must pass through in order to be withdrawn.
     * @param option The menu option to use for withdrawing.
     */
    public static native void withdraw(Filter<WidgetItem> filter, int option);

    /**
     * Deposits items into the bank.
     *
     * @param filter The filter items must pass through in order to be deposited.
     * @param option The menu option to use for depositing.
     */
    public static native void deposit(Filter<WidgetItem> filter, int option);

    /**
     * Determines if the bank contains an item.
     *
     * @return If the bank contains an item with the provided id.
     */
    public static boolean contains(int id) {
        return count(id) > 0;
    }

}
