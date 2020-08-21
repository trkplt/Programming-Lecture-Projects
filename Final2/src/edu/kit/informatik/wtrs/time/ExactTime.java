package edu.kit.informatik.wtrs.time;

import edu.kit.informatik.wtrs.ui.ErrorMessage;
import edu.kit.informatik.wtrs.ui.Main;

public class ExactTime implements Comparable<ExactTime> {

    private static final String DATE_TIME_SEPARATOR = "T";

    public static final String PATTERN = Date.PATTERN + DATE_TIME_SEPARATOR + Time.PATTERN;
    public static final String TWENTY_FOUR_PATTERN = Date.PATTERN + DATE_TIME_SEPARATOR + Time.TWENTY_FOUR_PATTERN;

    private final Date date;
    private final Time time;

    //TODO: ACCESS MODIFIER
    public ExactTime(int year, int month, int day, int hour, int minute) {
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

    private Duration getDurationToSameDay(ExactTime otherTime) {
        int minutes = Time.minutesBetween(this.time, otherTime.time) + Time.CALCULATION_CORRECTIVE_MARGIN;
        return new Duration(minutes);
    }

    private Duration getDurationToDifferentDay(ExactTime otherTime) {
        int days = Date.daysBetween(this.date, otherTime.date);
        int minutes =  this.time.minutesTillNewDay() + Time.CALCULATION_CORRECTIVE_MARGIN
                + days * Time.MINUTES_OF_DAY
                + otherTime.time.minutesFromDayStart();
        return new Duration(minutes);
    }

    //TODO: ACCESS MODIFIER
    public Duration getDurationTo(ExactTime otherTime) {
        if (this.date.compareTo(otherTime.date) == Main.COMPARE_NEUTRAL) {
            return this.getDurationToSameDay(otherTime);
        } else {
            return this.getDurationToDifferentDay(otherTime);
        }
    }

    @Override
    public String toString() {
        return this.date.toString() + DATE_TIME_SEPARATOR + this.time.toString();
    }

    @Override
    public int compareTo(ExactTime otherExactTime) {
        if (this.equals(otherExactTime)) {
            return Main.COMPARE_NEUTRAL;
        }

        int comparison;
        int dateComp = this.date.compareTo(otherExactTime.date);

        if (dateComp != Main.COMPARE_NEUTRAL) {
            comparison = dateComp;
        } else {
            comparison = this.time.compareTo(otherExactTime.time);
        }
        return comparison;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExactTime other = (ExactTime) o;
        return this.date.equals(other.date) && this.time.equals(other.time);
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int hashCode = 1;

        hashCode = prime * hashCode + this.date.hashCode();
        hashCode = prime * hashCode + this.time.hashCode();
        return hashCode;
    }
}
