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
        ImGui.label("State= " + Client.getState());
        ImGui.label("Loading= " + Client.isLoading());
        ImGui.label("ConVar= " + Client.getConVarById(3913));
        ImGui.label("Mining= " + Client.getStatById(Client.MINING));

        Player self = Players.self();
        if (self != null) {
            ImGui.label("Self");
            ImGui.label(" -> Name= " + self.getName());
            ImGui.label(" -> Animation= " + self.getAnimationId());
            ImGui.label(" -> Moving= " + self.isMoving());
            ImGui.label(" -> GlobalPos= " + self.getGlobalPosition());
            ImGui.label(" -> ScenePos= " + self.getScenePosition());
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

        WidgetGroup bankWidget = Widgets.getGroupById(517);
        if (bankWidget != null) {
            ImGui.label("Bank");
            ImGui.label(" -> Group= " + bankWidget.getId());
            ImGui.label(" -> Widgets= " + bankWidget.getId());

            Widget[] widgets = bankWidget.getWidgets();
            for (Widget w : widgets) {
                ImGui.label("  -> Type= " + w.getType());
            }
        }

        ImGui.label("Inventory");
        for (WidgetItem item : Inventory.getItems()) {
            ImGui.label(" -> " + item);
        }
    }

}
