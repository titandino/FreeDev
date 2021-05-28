package kraken.plugin.api;

import java.util.Objects;

/**
 * A variable where the state is determined by the server.
 */
public class ConVar {

    private int id;
    private int value;

    public ConVar() {
    }

    public ConVar(int id, int value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ConVar{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConVar conVar = (ConVar) o;
        return id == conVar.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

