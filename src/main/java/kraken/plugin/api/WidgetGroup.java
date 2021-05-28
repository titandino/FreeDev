package kraken.plugin.api;

/**
 * A group of widgets.
 */
public class WidgetGroup {

    // internal values, attempting to use these will break the client

    private long internal1;

    /**
     * Retrieves the id of this group.
     *
     * @return The id of this group.
     */
    public native int getId();

    /**
     * Retrieves all widgets in this group.
     *
     * @return All widgets in this group.
     */
    public native Widget[] getWidgets();

    @Override
    public String toString() {
        return "WidgetGroup{" +
                "id=" + getId() +
                '}';
    }
}
