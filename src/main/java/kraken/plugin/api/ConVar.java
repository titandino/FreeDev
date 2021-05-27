package kraken.plugin.api;

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

}

