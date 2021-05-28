# KrakenAPI
The scripting API for [Kraken](https://github.com/RSKraken), including a basic example plugin. The scripting API is currently only being tested on Java 8.

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
            SceneObject object = SceneObjects.closest((obj) -> obj.getName().toLowerCase().contains("bank booth"));
            if (object != null) {
                object.interact(Actions.MENU_EXECUTE_OBJECT2);
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

![example](https://i.imgur.com/37tAJPh.png)
