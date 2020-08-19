package edu.kit.informatik.wtrs.time;

import edu.kit.informatik.wtrs.ui.ErrorMessage;

public class ExactTime implements Comparable<ExactTime> {

    private static final String DATE_TIME_SEPARATOR = "T";

    public static final String PATTERN = Date.PATTERN + DATE_TIME_SEPARATOR + Time.PATTERN;
    public static final String TWENTY_FOUR_PATTERN = Date.PATTERN + DATE_TIME_SEPARATOR + Time.TWENTY_FOUR_PATTERN;

    private final Date date;
    private final Time time;

    ExactTime(int year, int month, int day, int hour, int minute) {
        this.date = new Date(year, month, day);
        this.time = new Time(hour, minute);
    }

    private Date getDate() {
        return this.date;
    }

    private Time getTime() {
        return this.time;
    }

    //TODO: ACCESS MODIFIER
    boolean isValid() {
        return this.date.isValid() && this.time.isValid();
    }

    //TODO: ACCESS MODIFIER
    boolean isDateValid() {
        return this.date.isValid();
    }

    //TODO: ACCESS MODIFIER
    boolean isTimeValid() {
        return this.time.isValid();
    }

    //TODO: ACCESS MODIFIER
    ErrorMessage getDateErrorMessage() {
        return this.date.getErrorMessage();
    }

    //TODO: ACCESS MODIFIER
    ErrorMessage getTimeErrorMessage() {
        return this.time.getErrorMessage();
    }

    //TODO: ACCESS MODIFIER
    Duration getDurationTo(ExactTime otherTime) {

    }

    @Override
    public String toString() {
        return this.date.toString() + DATE_TIME_SEPARATOR + this.time.toString();
    }

    @Override
    public int compareTo(ExactTime otherExactTime) {
        int former = -1;
        int neutral = 0;
        int latter = 1;
        int comparison;

        if (this == otherExactTime) {
            return neutral;
        }

        int dateComparison = this.getDate().compareTo(otherExactTime.getDate());
        int timeComparison = this.getTime().compareTo(otherExactTime.getTime());

        if (dateComparison < neutral) {
            comparison = former;
        } else if (dateComparison > neutral) {
            comparison = latter;
        } else if (timeComparison < neutral) {
            comparison = former;
        } else if (timeComparison > neutral) {
            comparison = latter;
        } else {
            comparison = neutral;
        }
        return comparison;
    }
}
