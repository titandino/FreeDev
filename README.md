# KrakenAPI
The scripting API for Kraken, including a basic example plugin.

```Java
package kraken.plugin;

import kraken.plugin.api.*;

/**
 * An example plugin.
 */
public class ExamplePlugin extends AbstractPlugin {

    @Override
    public boolean onLoaded(PluginContext pluginContext) {
        pluginContext.setName("Example");
        return true;
    }

    @Override
    public int onLoop() {
        Player self = Players.self();
        if (!self.isMoving()) {
            SceneObject object = SceneObjects.closest((obj) -> obj.getName().equals("Bank Booth"));
            if (object != null) {
                Vector3i pos = object.getGlobalPosition();
                Actions.menu(Actions.MENU_EXECUTE_OBJECT2, object.getId(), pos.getX(), pos.getY(), 1);
            }
        }

        return 1800;
    }

    @Override
    public void onPaint() {
        ImGui.label("Example Paint");
    }

}

```