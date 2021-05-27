package kraken.plugin.api;

/**
 * An object within the game world.
 */
public class SceneObject extends Entity {

    /**
     * Do not make instances of this.
     */
    SceneObject() { }

    /**
     * Retrieves the id of this object.
     *
     * @return The id of this object.
     */
    public native int getId();

}
