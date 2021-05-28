package kraken.plugin.api;

import java.util.Objects;

/**
 * A group of widgets.
 */
public class WidgetGroup {

    // internal values, attempting to use these will break the client

    private long internal1;

    /**
     * Do not make instances of this.
     */
    private WidgetGroup() { }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WidgetGroup that = (WidgetGroup) o;
        return internal1 == that.internal1;
    }

    @Override
    public int hashCode() {
        return Objects.hash(internal1);
    }
}
