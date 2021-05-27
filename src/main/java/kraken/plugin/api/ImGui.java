package kraken.plugin.api;

/**
 * Provides bindings to imgui.
 */
public class ImGui {

    private ImGui() { }

    public static native boolean beginChild(String id);
    public static native void endChild();
    public static native void label(String label);
    public static native boolean checkbox(String text, boolean initial);

}
