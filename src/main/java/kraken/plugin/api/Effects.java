package kraken.plugin.api;

/**
 * A provider for effects.
 */
public class Effects {

    /**
     * Finds the closest effect matching the provided filter.
     *
     * @param filter The filter effects must pass through in order to be accepted.
     * @return The found effect, or NULL if one was not found.
     */
    public static native Effect closest(Filter<Effect> filter);

}
