package kraken.plugin.api;

/**
 * A stat
 */
public class Stat {

    private int index;
    private int current;
    private int max;

    public Stat() {
    }

    public Stat(int index, int current, int max) {
        this.index = index;
        this.current = current;
        this.max = max;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

}
