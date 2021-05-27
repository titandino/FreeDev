package kraken.plugin.api;

import java.nio.charset.StandardCharsets;

/**
 * A character.
 */
public class Entity {

    // internal values, attempting to use these will break the client

    private long internal1;
    private int internal2;
    private long internal3;

    /**
     * Do not make instances of this.
     */
    Entity() { }

    /**
     * Retrieves the name of this entity.
     *
     * @return The name of this entity.
     */
    private native byte[] getNameBinary();

    /**
     * Retrieves the name of this entity.
     *
     * @return The name of this entity.
     */
    public String getName() {
        return new String(getNameBinary(), StandardCharsets.US_ASCII);
    }

    /**
     * Retrieves this character's position within the 3d scene.
     *
     * @return This character's position within the 3d scene.
     */
    public native Vector3 getScenePosition();

    /**
     * Retrieves this character's global position within the world.
     *
     * @return This character's global position within the world.
     */
    public native Vector3i getGlobalPosition();

}
