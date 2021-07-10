package kraken.plugin.api;

/**
 * Provides time related utilities.
 */
public class Time {

    private Time() {
    }

    public static int perHour(long runtime, int amount) {
        return (int) ((double) amount * 3600000.0d / (double) runtime);
    }

    public static String formatTime(long runtime) {
        long seconds = (runtime / 1000) % 60;
        long minutes = ((runtime / (1000 * 60)) % 60);
        long hours = ((runtime / (1000 * 60 * 60)) % 24);
        return hours + "h " + minutes + "m " + seconds + "s";
    }

}
