package edu.kit.informatik;

/**
 * This class represents a real life date.
 * @author Tarik Polat
 * @version 1.0.0
 */
public class Date {

    /*
    Day, month and year are final because the birthday of the passengers shall not change.
    Other dates can be changed by creating a new date with the needed parameters and replaced with the existing date.
    */
    private final int day;
    private final int month;
    private final int year;

    /**
     * Constructs a date object with the given parameters.
     * @param day the day
     * @param month the month
     * @param year the year
     */
    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    /**
     * To get the day of the date. Because only the day may be needed.
     * @return the day of the date
     */
    public int getDay() {
        return day;
    }

    /**
     * To get the month of the date. Because only the month may be needed.
     * @return the month of the date
     */
    public int getMonth() {
        return month;
    }

    /**
     * To get the year of the date. Because only the month may be needed.
     * @return the year of the date
     */
    public int getYear() {
        return year;
    }

    /**
     * To get a string representation of the address. Contains day, month and year.
     * All three information are included because all three are needed in general.
     * @return the string representation of the address
     */
    public String toString() {
        return day + " " + month + " " + year;
    }
}
