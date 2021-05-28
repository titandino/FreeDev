package kraken.plugin.api;

/**
 * A 2-dimensional area of integers.
 */
public class Area2di {

    private Vector2i begin;
    private Vector2i end;

    public Area2di() {
    }

    public Area2di(Vector2i begin, Vector2i end) {
        this.begin = begin;
        this.end = end;
    }

    /**
     * Determines if the provided vector is within this area.
     */
    public boolean contains(Vector2i v)
    {
        return v.getX() >= begin.getX() &&
                v.getX() <= end.getX() &&
                v.getY() >= begin.getY() &&
                v.getY() <= end.getY();
    }
}
