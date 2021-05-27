package kraken.plugin.api;

/**
 * A widget.
 */
public class Widget {

    // internal values, attempting to use these will break the client

    private long internal1;

    /**
     * Retrieves the type of this widget.
     *
     * @return The type of this widget.
     */
    public native int getType();

    /**
     * Retrieves the children in this widget.
     *
     * @return The children in this widget.
     */
    public native Widget[] getChildren();

    /**
     * Retrieves the text being stored in this widget.
     *
     * @return The text being stored in this widget, or NULL if the widget has no text.
     */
    public native String getText();

    /**
     * Retrieves the item being stored in this widget.
     *
     * @return The item being stored in this widget, or NULL if the widget has no item.
     */
    public native Item getItem();

    /**
     * Do not make instances of this.
     */
    Widget() { }
}
