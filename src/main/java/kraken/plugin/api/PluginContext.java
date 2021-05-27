package kraken.plugin.api;

public class PluginContext {

    // internal values, attempting to use these will break the client

    private long internal1;

    /**
     * Do not make instances of this.
     */
    private PluginContext() { }

    /**
     * Sets the name of the plugin.
     *
     * @param name The new name to set.
     */
    public native void setName(String name);

    /**
     * Sets the SDK version that this plugin uses.
     *
     * @param version The SDK version that this plugin uses.
     */
    public native void setSdkVersion(int version);

}
