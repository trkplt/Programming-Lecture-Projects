package edu.kit.informatik.wtrs.time;

import edu.kit.informatik.wtrs.ui.Main;

import java.util.Objects;

public class Duration implements Comparable<Duration> {

    private final int hours;
    private final int minutes;

    //TODO: VALIDITY AND ACCESS MODIFIER
    Duration(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    //TODO: ACCESS MODIFIER
    Duration(int minutes) {
        this(minutes / (Time.MAX_MINUTE + Time.CALCULATION_CORRECTIVE_MARGIN),
                minutes % (Time.MAX_MINUTE + Time.CALCULATION_CORRECTIVE_MARGIN));
    }

    protected static Duration differenceBetween(Duration first, Duration second) {
        if (first == null && second == null) {
            return null;
        } else if (first == null) {
            return second;
        } else if (second == null) {
            return first;
        }

        if (first.compareTo(second) < Main.COMPARE_NEUTRAL) {
            return differenceBetween(second, first);
        }

        int newMinutes = first.toMinutes() - second.toMinutes();
        return new Duration(newMinutes);
    }

    public int toMinutes() {
        return this.hours * (Time.MAX_MINUTE + Time.CALCULATION_CORRECTIVE_MARGIN) + this.minutes;
    }

    @Override
    public String toString() {
        return this.hours + Time.SEPARATOR + this.minutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Duration duration = (Duration) o;
        return hours == duration.hours && minutes == duration.minutes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hours, minutes);
    }

    @Override
    public int compareTo(Duration other) {
        int comparison;
        int compHours = Integer.compare(this.hours, other.hours);

        if (compHours != Main.COMPARE_NEUTRAL) {
            comparison = compHours;
        } else {
            comparison = Integer.compare(this.minutes, other.minutes);
        }
        return comparison;
    }
}
