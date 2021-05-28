package kraken.plugin.api;

import java.util.Objects;

/**
 * A generic item.
 */
public class Item {

    private int id;
    private int amount;

    public Item() {
    }

    public Item(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id &&
                amount == item.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount);
    }
}
