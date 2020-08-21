package edu.kit.informatik.wtrs.time;

public class Duration {

    private final int hours;
    private final int minutes;

    //TODO: VALIDITY AND ACCESS MODIFIER
    Duration(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    Duration(int minutes) {
        this(minutes / (Time.MAX_MINUTE + Time.CALCULATION_CORRECTIVE_MARGIN),
                minutes % (Time.MAX_MINUTE + Time.CALCULATION_CORRECTIVE_MARGIN));
    }

    @Override
    public String toString() {
        return this.hours + Time.SEPARATOR + this.minutes;
    }
}
