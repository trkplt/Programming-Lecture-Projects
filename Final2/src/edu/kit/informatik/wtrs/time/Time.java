package edu.kit.informatik.wtrs.time;

import edu.kit.informatik.wtrs.ui.ErrorMessage;
import edu.kit.informatik.wtrs.ui.Main;

import java.util.Objects;

public class Time implements Comparable<Time> {

    private static final int MIN_HOUR = 0;
    private static final int MAX_HOUR = 23;
    private static final int MIN_MINUTE = 0;
    private static final int FIRST_DOUBLE_DIGIT_HOUR_MINUTE = 10;
    private static final String FILLER = "0";
    private static final String HOUR_PATTERN = "(0[0-9]|1[0-9]|2[0-3])";
    private static final String TWENTY_FOUR_HOUR_PATTERN = "(0[0-9]|1[0-9]|2[0-4])";
    private static final String MINUTE_PATTERN = "(0[0-9]|1[0-9]|2[0-9]|3[0-9]|4[0-9]|5[0-9])";

    protected static final int CALCULATION_CORRECTIVE_MARGIN = 1;
    protected static final int MAX_MINUTE = 59;
    protected static final String SEPARATOR = ":";

    public static final String PATTERN = HOUR_PATTERN + SEPARATOR + MINUTE_PATTERN;
    public static final String TWENTY_FOUR_PATTERN = TWENTY_FOUR_HOUR_PATTERN + SEPARATOR + MINUTE_PATTERN;
    public static final int MINUTES_OF_DAY = (MAX_HOUR + CALCULATION_CORRECTIVE_MARGIN)
            * (MAX_MINUTE + CALCULATION_CORRECTIVE_MARGIN);

    private final int hour;
    private final int minute;
    private final boolean valid;
    private final ErrorMessage errorMessage;

    protected Time(int hour, int minute) {
        //TODO: VALIDITY (WITH PATTERN) CHECK IN COMMAND
        this.hour = hour;
        this.minute = minute;

        if (hour > MAX_HOUR && minute > MIN_MINUTE) {
            this.valid = false;
            this.errorMessage = ErrorMessage.ILLEGAL_TIME;
        } else {
            this.valid = true;
            this.errorMessage = null;
        }
    }

    private static int minutesBetweenSameHour(Time first, Time second) {
        return MAX_MINUTE
                - (first.minutesPrecedingInHour()
                + second.minutesProceedingInHour()
                + CALCULATION_CORRECTIVE_MARGIN);
    }

    private static int minutesBetweenDifferentHours(Time first, Time second) {
        int minutes = Main.COUNTER_START_ZERO;

        minutes += (second.hour - first.hour - CALCULATION_CORRECTIVE_MARGIN)
                * (MAX_MINUTE + CALCULATION_CORRECTIVE_MARGIN);

        return first.minutesProceedingInHour() + minutes + second.minutesPrecedingInHour();
    }

    protected static int minutesBetween(Time first, Time second) {
        if (first.compareTo(second) > Main.COMPARE_NEUTRAL) {
            return minutesBetween(second, first);
        }

        if (first.hour == second.hour) {
            return minutesBetweenSameHour(first, second);
        } else {
            return minutesBetweenDifferentHours(first, second);
        }
    }

    protected int minutesTillNewDay() {
        Time lastMinute = new Time(MAX_HOUR, MAX_MINUTE);
        int minutes = minutesBetween(this, lastMinute);

        if (!this.equals(lastMinute)) {
            minutes += CALCULATION_CORRECTIVE_MARGIN;
        }
        return minutes;
    }

    protected int minutesFromDayStart() {
        Time firstMinute = new Time(MIN_HOUR, MIN_MINUTE);
        int minutes = minutesBetween(this, firstMinute);

        if (!this.equals(firstMinute)) {
            minutes += CALCULATION_CORRECTIVE_MARGIN;
        }
        return minutes;
    }

    private int minutesPrecedingInHour() {
        return this.minute;
    }

    private int minutesProceedingInHour() {
        return MAX_MINUTE - this.minute;
    }

    private int getHour() {
        return this.hour;
    }

    private int getMinute() {
        return this.minute;
    }

    protected boolean isValid() {
        return this.valid;
    }

    protected ErrorMessage getErrorMessage() {
        return this.errorMessage;
    }

    /*protected Duration getDurationTo() {

    }*/

    @Override
    public String toString() {
        String hourPrecede = this.hour < FIRST_DOUBLE_DIGIT_HOUR_MINUTE ? FILLER : Main.EMPTY_STRING;
        String minutePrecede = this.minute < FIRST_DOUBLE_DIGIT_HOUR_MINUTE ? FILLER : Main.EMPTY_STRING;
        return hourPrecede + this.hour + SEPARATOR + minutePrecede + this.minute;
    }

    @Override
    public int compareTo(Time otherTime) {
        if (this.equals(otherTime)) {
            return Main.COMPARE_NEUTRAL;
        }

        int comparison;
        int hourComp = Integer.compare(this.hour, otherTime.hour);

        if (hourComp != Main.COMPARE_NEUTRAL) {
            comparison = hourComp;
        } else {
            comparison = Integer.compare(this.minute, otherTime.minute);
        }
        return comparison;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        Time other = (Time) o;
        return this.hour == other.hour && this.minute == other.minute;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hour, minute);
    }
}
