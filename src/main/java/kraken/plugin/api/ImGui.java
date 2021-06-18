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
    public static native int intSlider(String text, int v, int min, int max);
    public static native boolean button(String text);
    public static native String input(String text, byte[] input);
    public static native int intInput(String text, int v);

}
