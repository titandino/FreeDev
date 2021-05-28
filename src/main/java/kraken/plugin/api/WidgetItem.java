package kraken.plugin.api;

public class WidgetItem extends Item{

    private int slot;

    public WidgetItem() {
    }

    public WidgetItem(int id, int amount, int slot) {
        super(id, amount);
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    @Override
    public String toString() {
        return "WidgetItem{" +
                "id=" + getId() +
                ", amount= " + getAmount() +
                ", slot=" + slot +
                '}';
    }
}