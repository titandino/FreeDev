package kraken.plugin.api;

import java.util.Objects;

/**
 * A 2-dimensional area of integers.
 */
public class Area2di {

    private Vector2i begin;
    private Vector2i end;

    public Area2di() {
    }

    public Area2di(Vector2i begin, Vector2i end) {
    	if (begin.getX() > end.getX() || begin.getY() > end.getY()) {
	        this.begin = end;
	        this.end = begin;
    	} else {
    		 this.begin = begin;
 	        this.end = end;
    	}
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Area2di area2di = (Area2di) o;
        return Objects.equals(begin, area2di.begin) &&
                Objects.equals(end, area2di.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(begin, end);
    }
}
