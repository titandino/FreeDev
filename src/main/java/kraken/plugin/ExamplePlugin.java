package kraken.plugin;

import kraken.plugin.api.*;

import static kraken.plugin.api.Actions.MENU_EXECUTE_NPC1;
import static kraken.plugin.api.Client.MINING;
import static kraken.plugin.api.Player.ADRENALINE;

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

        return 8000;
    }

    /**
     * Prints all text in a widget recursively.
     */
    private void printWidgetText(Widget w) {
        int type = w.getType();
        if (type == Widget.TEXT) {
            String text = w.getText();
            if (text != null) {
                ImGui.label("  -> " + text);
            }
        } else if (type == Widget.CONTAINER) {
            Widget[] children = w.getChildren();
            if (children != null) {
                for (Widget child : children) {
                    if (child != null) {
                        printWidgetText(child);
                    }
                }
            }
        }
    }

    @Override
    public void onPaint() {
        ImGui.checkbox("Checkbox", true);
        ImGui.label("Label");
        ImGui.intSlider("Int Slider", 37, 1, 100);
        ImGui.intInput("Int Input", 40);
        ImGui.button("Button");

        ImGui.label("State= " + Client.getState());
        ImGui.label("Loading= " + Client.isLoading());
        ImGui.label("ConVar= " + Client.getConVarById(3913));
        ImGui.label("Mining= " + Client.getStatById(MINING));

        Player self = Players.self();
        if (self != null) {
            ImGui.label("Self");
            ImGui.label(" -> Name= " + self.getName());
            ImGui.label(" -> Animation= " + self.getAnimationId());
            ImGui.label(" -> Moving= " + self.isMoving());
            ImGui.label(" -> GlobalPos= " + self.getGlobalPosition());
            ImGui.label(" -> ScenePos= " + self.getScenePosition());
            ImGui.label(" -> Adrenaline= " + self.getStatusBarFill(ADRENALINE));
        }

        Npc firstNpc = Npcs.closest((npc) -> npc.getName() != null && !npc.getName().isEmpty());
        if (firstNpc != null) {
            ImGui.label("Npc");
            ImGui.label(" -> Name= " + firstNpc.getName());
            ImGui.label(" -> Id= " + firstNpc.getId());
            ImGui.label(" -> Health= " + firstNpc.getHealth());
            ImGui.label(" -> GlobalPos= " + firstNpc.getGlobalPosition());
            ImGui.label(" -> ScenePos= " + firstNpc.getScenePosition());
        }

        SceneObject firstObj = SceneObjects.closest((obj) -> obj.getName() != null && !obj.getName().isEmpty());
        if (firstObj != null) {
            ImGui.label("Obj");
            ImGui.label(" -> Name= " + firstObj.getName());
            ImGui.label(" -> Id= " + firstObj.getId());
            ImGui.label(" -> GlobalPos= " + firstObj.getGlobalPosition());
            ImGui.label(" -> ScenePos= " + firstObj.getScenePosition());
        }

        GroundItem firstGround = GroundItems.closest((obj) -> true);
        if (firstGround != null) {
            ImGui.label("GroundItem");
            ImGui.label(" -> Id= " + firstGround.getId());
            ImGui.label(" -> GlobalPos= " + firstGround.getGlobalPosition());
            ImGui.label(" -> ScenePos= " + firstGround.getScenePosition());
        }

        Effect effect = Effects.closest((obj) -> true);
        if (effect != null) {
            ImGui.label("Effect");
            ImGui.label(" -> Id= " + effect.getId());
            ImGui.label(" -> GlobalPos= " + effect.getGlobalPosition());
            ImGui.label(" -> ScenePos= " + effect.getScenePosition());
        }

        WidgetGroup bankWidget = Widgets.getGroupById(517);
        if (bankWidget != null) {
            ImGui.label("Bank");
            ImGui.label(" -> Group= " + bankWidget.getId());

            Widget[] widgets = bankWidget.getWidgets();
            for (Widget w : widgets) {
                ImGui.label("  -> Type= " + w.getType());
                printWidgetText(w);
            }
        }

        ImGui.label("Inventory");
        for (WidgetItem item : Inventory.getItems()) {
            ImGui.label(" -> " + item);
        }
    }

}
