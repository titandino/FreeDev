package kraken.plugin.api;

/**
 * A provider of objects.
 */
public class SceneObjects {

    /**
     * Finds the closest object matching the provided filter.
     *
     * @param filter The filter objects must pass through in order to be accepted.
     * @return The found object, or NULL if one was not found.
     */
    public static native SceneObject closest(Filter<SceneObject> filter);

}
