package edu.kit.informatik.wtrs.time;

import edu.kit.informatik.wtrs.ui.ErrorMessage;
import edu.kit.informatik.wtrs.ui.Main;

public class Time implements Comparable<Time> {

    private static final int MIN_HOUR = 0;
    private static final int MAX_HOUR = 23;
    private static final int MIN_MINUTE = 0;
    private static final int MAX_MINUTE = 59;
    private static final int FIRST_DOUBLE_DIGIT_HOUR_MINUTE = 10;
    private static final String FILLER = "0";
    private static final String TIME_SEPARATOR = ":";
    private static final String HOUR_PATTERN = "(0[0-9]|1[0-9]|2[0-3])";
    private static final String TWENTY_FOUR_HOUR_PATTERN = "(0[0-9]|1[0-9]|2[0-4])";
    private static final String MINUTE_PATTERN = "(0[0-9]|1[0-9]|2[0-9]|3[0-9]|4[0-9]|5[0-9])";

    public static final String PATTERN = HOUR_PATTERN + TIME_SEPARATOR + MINUTE_PATTERN;
    public static final String TWENTY_FOUR_PATTERN = TWENTY_FOUR_HOUR_PATTERN + TIME_SEPARATOR + MINUTE_PATTERN;

    protected static final int CALCULATION_CORRECTIVE_MARGIN = 1;

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

        for (int hour = first.hour + CALCULATION_CORRECTIVE_MARGIN; hour < second.hour; hour++) {
            minutes += MAX_MINUTE + CALCULATION_CORRECTIVE_MARGIN;
        }

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

    protected Duration getDurationTo() {

    }

    @Override
    public String toString() {
        String hourPrecede = this.hour < FIRST_DOUBLE_DIGIT_HOUR_MINUTE ? FILLER : Main.EMPTY_STRING;
        String minutePrecede = this.minute < FIRST_DOUBLE_DIGIT_HOUR_MINUTE ? FILLER : Main.EMPTY_STRING;
        return hourPrecede + this.hour + TIME_SEPARATOR + minutePrecede + this.minute;
    }

    @Override
    public int compareTo(Time otherTime) {
        int comparison;

        if (this == otherTime) {
            comparison = Main.COMPARE_NEUTRAL;
        } else if (this.getHour() < otherTime.getHour()) {
            comparison = Main.COMPARE_PRIOR;
        } else if (this.getHour() > otherTime.getHour()) {
            comparison = Main.COMPARE_LATTER;
        } else if (this.getMinute() < otherTime.getMinute()) {
            comparison = Main.COMPARE_PRIOR;
        } else if (this.getMinute() > otherTime.getMinute()) {
            comparison = Main.COMPARE_LATTER;
        } else {
            comparison = Main.COMPARE_NEUTRAL;
        }
        return comparison;
    }
}
