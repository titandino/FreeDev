package kraken.plugin.api;

/**
 * Provides bindings to imgui.
 */
public class ImGui {

    private ImGui() { }

    /**
     * Begins a child group.
     */
    public static native boolean beginChild(String id);

    /**
     * Ends a child group.
     */
    public static native void endChild();

    /**
     * Draws a label in the current UI context. This label will be positioned automatically.
     */
    public static native void label(String label);

    /**
     * Draws a checkbox in the current UI context. This checkbox will be positioned automatically.
     */
    public static native boolean checkbox(String text, boolean initial);

    /**
     * Draws a slider in the current UI context. This slider will be positioned automatically.
     */
    public static native int intSlider(String text, int v, int min, int max);

    /**
     * Draws a button in the current UI context. THis button will be positioned automatically.
     */
    public static native boolean button(String text);

    /**
     * Draws a text input box in the current UI context. This input box will be positioned automatically.
     */
    public static native void input(String text, byte[] input);

    /**
     * Draws a text input box in the current UI context. This input box will be positioned automatically.
     */
    public static native int intInput(String text, int v);

    /**
     * Draws some text onto the screen.
     */
    public static native void freeText(String text, Vector2i pos, int color);

    /**
     * Draws a line onto the screen.
     */
    public static native void freeLine(Vector2i a, Vector2i b, int color);

}
