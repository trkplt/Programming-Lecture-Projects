package edu.kit.informatik.wtrs.time;

import edu.kit.informatik.wtrs.ui.ErrorMessage;
import edu.kit.informatik.wtrs.ui.Main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class Date implements Comparable<Date> {

    private static final int LEAP_YEAR_REMAINDER = 0;
    private static final int LEAP_YEAR_DIVISOR_FOUR = 4;
    private static final int LEAP_YEAR_DIVISOR_FOUR_HUNDRED = 400;
    private static final int LEAP_YEAR_ILLEGAL_DIVISOR_HUNDRED = 100;
    private static final int DAY_INDEX_HELPER_FOURTEEN = 14;
    private static final int DAYS_OF_YEAR = 365;
    private static final int DAYS_OF_LEAP_YEAR = 366;
    private static final int MIN_YEAR = 1000;
    private static final int MIN_MONTH = 1;
    private static final int MIN_DAY = 1;
    private static final int MAX_DAY_OF_WEEK = 7;
    private static final int MAX_DAY_OF_MONTH = 31;
    private static final int FIRST_DOUBLE_DIGIT_MONTH_DAY = 10;
    private static final int LEAP_DAY_INDEX = 29;
    private static final int LEAP_MONTH_INDEX = 2;
    private static final String FILLER = "0";
    private static final String DATE_SEPARATOR = "-";
    private static final String YEAR_PATTERN = "(1[0-9]{3}|2[0-9]{3}|3[0-9]{3}|4[0-9]{3}|5[0-9]{3}|6[0-9]{3}|7[0-9]{3}"
            + "|8[0-9]{3}|9[0-9]{3})";
    private static final String MONTH_PATTERN = "(0[1-9]|1[0-2])";
    private static final String DAY_PATTERN = "(0[1-9]|1[0-9]|2[0-9]|3[0-1])";

    protected static final int MAX_MONTH = 12;
    protected static final int MAX_YEAR = 9999;
    public static final String PATTERN = YEAR_PATTERN + DATE_SEPARATOR + MONTH_PATTERN + DATE_SEPARATOR + DAY_PATTERN;

    private final Day dayOfWeek;
    private final Month monthOfYear;
    private final int day;
    private final int month;
    private final int year;
    private final ErrorMessage errorMessage;

    public Date(final int year, final int month, final int day) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.dayOfWeek = Day.getDay(Date.getDayIndex(year, month, day));
        this.monthOfYear = Month.getMonth(month);

        //TODO: YEAR, MONTH AND ROUGHLY DAY INDEX CHECKS IN COMMAND
        //TODO: UPDATE!! ALL CHECKS BETTER? IN COMMAND, PERIOD FINDER ENTERS INVALID DAYS
        /*if (year < Date.MIN_YEAR || year > Date.MAX_YEAR) {
            this.errorMessage = ErrorMessage.ILLEGAL_YEAR;
        } else if (month < Date.MIN_MONTH || month > Date.MAX_MONTH) {
            this.errorMessage = ErrorMessage.ILLEGAL_MONTH;
        } else */ if (day < Date.MIN_DAY || day > this.monthOfYear.getNumberOfDays(year)) {
            this.errorMessage = ErrorMessage.ILLEGAL_DAY;
        } else {
            this.errorMessage = null;
        }
    }

    private static int getDayIndex(int year, int month, int day) {
        int yZero = year - ((DAY_INDEX_HELPER_FOURTEEN - month) / MAX_MONTH);
        int x = yZero + (yZero / LEAP_YEAR_DIVISOR_FOUR) - (yZero / LEAP_YEAR_ILLEGAL_DIVISOR_HUNDRED)
                + (yZero / LEAP_YEAR_DIVISOR_FOUR_HUNDRED);
        int mZero = month + (MAX_MONTH * ((DAY_INDEX_HELPER_FOURTEEN - month) / MAX_MONTH)) - 2;
        int dZero = (day + x + ((MAX_DAY_OF_MONTH * mZero) / MAX_MONTH)) % MAX_DAY_OF_WEEK;
        return dZero;
    }

    protected int getDay() {
        return this.day;
    }

    protected int getMonth() {
        return this.month;
    }

    protected int getYear() {
        return this.year;
    }

    private Month getMonthOfYear() {
        return this.monthOfYear;
    }

    protected static boolean isLeapYear(int year) {
        boolean divisibleByFour = year % LEAP_YEAR_DIVISOR_FOUR == LEAP_YEAR_REMAINDER;
        boolean divisibleByFourHundred = year % LEAP_YEAR_DIVISOR_FOUR_HUNDRED == LEAP_YEAR_REMAINDER;
        boolean divisibleByHundred = year % LEAP_YEAR_ILLEGAL_DIVISOR_HUNDRED == LEAP_YEAR_REMAINDER;

        return divisibleByFourHundred || (divisibleByFour && !divisibleByHundred);
    }

    private static int daysInYear(int year) {
        return isLeapYear(year) ? DAYS_OF_LEAP_YEAR : DAYS_OF_YEAR;
    }

    private static int daysBetweenSameYear(Date first, Date second) {
        return daysInYear(first.year)
                - (first.daysPrecedingInYear() + Time.CALCULATION_CORRECTIVE_MARGIN
                + second.daysProceedingInYear() + Time.CALCULATION_CORRECTIVE_MARGIN);
    }

    private static int daysBetweenDifferentYears(Date first, Date second) {
        int numberOfDays = Main.COUNTER_START_ZERO;

        for (int year = first.year + Time.CALCULATION_CORRECTIVE_MARGIN; year < second.year; year++) {
            numberOfDays += daysInYear(year);
        }

        return first.daysProceedingInYear() + numberOfDays + second.daysPrecedingInYear();
    }

    protected static int daysBetween(Date first, Date second) {
        if (first.compareTo(second) > Main.COMPARE_NEUTRAL) {
            return daysBetween(second, first);
        }

        if (first.year == second.year) {
            return daysBetweenSameYear(first, second);
        } else {
            return daysBetweenDifferentYears(first, second);
        }
    }

    protected static Date lastDatePossible() {
        return new Date(MAX_YEAR, MAX_MONTH, Month.getMonth(MAX_MONTH).getNumberOfDays(MAX_YEAR));
    }

    private int daysPrecedingInYear() {
        int days = Main.COUNTER_START_ZERO;
        for (int month = MIN_MONTH; month < this.month; month++) {
            days += Month.getMonth(month).getNumberOfDays(this.year);
        }
        return days + this.day - Time.CALCULATION_CORRECTIVE_MARGIN;
    }

    private int daysProceedingInYear() {
        int days = Main.COUNTER_START_ZERO;
        for (int month = MAX_MONTH; month > this.month; month--) {
            days += Month.getMonth(month).getNumberOfDays(this.year);
        }
        return days + this.monthOfYear.getNumberOfDays(this.year) - this.day;
    }

    public Date previousDate() {
        int newDay = this.day;
        int newMonth = this.month;
        int newYear = this.year;
        --newDay;

        if (newDay < MIN_DAY) {
            --newMonth;
        }

        if (newMonth < MIN_MONTH) {
            newMonth = MAX_MONTH;
            --newYear;
        }

        if (newYear < MIN_YEAR) {
            return null;
        }

        if (newDay < MIN_DAY) {
            newDay = Month.getMonth(newMonth).getNumberOfDays(newYear);
        }

        return new Date(newYear, newMonth, newDay);
    }

    public Date nextDate() {
        int newDay = this.day;
        int newMonth = this.month;
        int newYear = this.year;
        ++newDay;

        if (newDay > this.monthOfYear.getNumberOfDays(newYear)) {
            newDay = MIN_DAY;
            newMonth++;
        }

        if (newMonth > MAX_MONTH) {
            newMonth = MIN_MONTH;
            newYear++;
        }

        if (newYear > MAX_YEAR) {
            return null;
        }

        return new Date(newYear, newMonth, newDay);
    }

    protected boolean isValid() {
        return this.errorMessage == null;
    }

    protected ErrorMessage getErrorMessage() {
        return this.errorMessage;
    }

    protected Collection<Date> datesTo(Date other) {
        if (this.compareTo(other) > Main.COMPARE_NEUTRAL) {
            return other.datesTo(this);
        }

        List<Date> dates = new ArrayList<>();
        dates.add(this);

        if (this.equals(other)) {
            return dates;
        }

        Date newDate = this.nextDate();

        if (newDate == null) {
            return dates;
        }

        while (!newDate.equals(other)) {
            dates.add(newDate);
            newDate = newDate.nextDate();

            if (newDate == null) {
                return dates;
            }
        }

        dates.add(newDate);
        return dates;
    }

    private static Date periodBorderMonthsForwardOut(int year, int month, int day) {
        boolean isDurationWeeks = false;
        int newDuration = Main.COUNTER_START_ZERO;

        int newDay = day;
        int newMonth = month - MAX_MONTH;
        int newYear = year;

        if (newYear < MAX_YEAR) {
            ++newYear;
        } else {
            newYear = MAX_YEAR;
            newMonth = MAX_MONTH;
            newDay = Month.getMonth(newMonth).getNumberOfDays(newYear);
            return new Date(newYear, newMonth, newDay);
        }

        return new Date(newYear, newMonth, newDay).periodBorder(newDuration, isDurationWeeks);
    }

    private static Date periodBorderMonthsBackwardOut(int year, int month, int day) {
        boolean isDurationWeeks = false;
        int newDuration = Main.COUNTER_START_ZERO;

        int newDay = day;
        int newMonth = MAX_MONTH - month;
        int newYear = year;

        if (newYear > MIN_YEAR) {
            --newYear;
        } else {
            newYear = MIN_YEAR;
            newMonth = MIN_MONTH;
            newDay = MIN_DAY;
            return new Date(newYear, newMonth, newDay);
        }

        return new Date(newYear, newMonth, newDay).periodBorder(newDuration, isDurationWeeks);
    }

    protected Date periodBorder(int duration, boolean isDurationWeeks) {
        if (isDurationWeeks) {
            return periodBorderWeeks(duration);
        }

        //from now on the duration is in months
        int newDay = this.day;
        int newMonth = this.month + duration;
        int newYear = this.year;

        if (newMonth > MAX_MONTH) {
            return periodBorderMonthsForwardOut(newYear, newMonth, newDay);
        } else if (newMonth < MIN_MONTH) {
            return periodBorderMonthsBackwardOut(newYear, newMonth, newDay);
        }

        int maxDaysOfMonth = Month.getMonth(newMonth).getNumberOfDays(newYear);
        newDay = Math.min(newDay, maxDaysOfMonth);

        //return new Date(newYear, newMonth, newDay).nextDate(); ??? mesela 31 Temmuz fristi 31 Ağustosu da dahil ediyor alttaki gibi olunca
        //nextDate sadece forward olursa doğru, backward ise previousDate lazım
        return new Date(newYear, newMonth, newDay);
    }

    private Date periodBorderWeeksForwardOut(int year, int month, int day, int maxDayOfMonth) {
        boolean isDurationWeeks = true;
        int newDuration = Main.COUNTER_START_ZERO;

        int newDay = day - maxDayOfMonth;
        int newMonth = month + Time.CALCULATION_CORRECTIVE_MARGIN;
        newMonth = newMonth > MAX_MONTH ? MIN_MONTH : newMonth;
        int newYear = newMonth == MIN_MONTH ? year + Time.CALCULATION_CORRECTIVE_MARGIN : year;

        if (newYear > MAX_YEAR) {
            newYear = MAX_YEAR;
            newMonth = MAX_MONTH;
            newDay = Month.getMonth(newMonth).getNumberOfDays(newYear);
            return new Date(newYear, newMonth, newDay);
        }

        return new Date(newYear, newMonth, newDay).periodBorder(newDuration, isDurationWeeks);
    }

    private Date periodBorderWeeksBackwardOut(int year, int month, int day) {
        boolean isDurationWeeks = true;
        int newDuration = Main.COUNTER_START_ZERO;

        int newMonth = month - Time.CALCULATION_CORRECTIVE_MARGIN;
        int newYear = year;

        if (newMonth < MIN_MONTH) {
            newMonth = MAX_MONTH;
            --newYear;
        }

        if (newYear < MIN_YEAR) {
            return new Date(MIN_YEAR, MIN_MONTH, MIN_DAY);
        }

        int maxDayOfMonth = Month.getMonth(newMonth).getNumberOfDays(newYear);
        int newDay = maxDayOfMonth + day;

        return new Date(newYear, newMonth, newDay).periodBorder(newDuration, isDurationWeeks);
    }

    private Date periodBorderWeeks(int weeks) {
        int newDay = this.day + (weeks * MAX_DAY_OF_WEEK);
        int newMonth = this.month;
        int newYear = this.year;
        int maxDayOfMonth = this.monthOfYear.getNumberOfDays(newYear);

        if (newDay > maxDayOfMonth) {
            return periodBorderWeeksForwardOut(newYear, newMonth, newDay, maxDayOfMonth);
        } else if (newDay < MIN_DAY) {
            return periodBorderWeeksBackwardOut(newYear, newMonth, newDay);
        }

        newDay = Math.min(newDay, maxDayOfMonth);

        //TODO: PREVIOUS NEXT İLE DÜZELT
        //return new Date(newYear, newMonth, newDay).nextDate(); ??? mesela 31 Temmuz fristi 31 Ağustosu da dahil ediyor alttaki gibi olunca
        //nextDate sadece forward olursa doğru, backward ise previousDate lazım
        return new Date(newYear, newMonth, newDay);
    }

    @Override
    public String toString() {
        String monthPrecede = this.month < FIRST_DOUBLE_DIGIT_MONTH_DAY ? FILLER : Main.EMPTY_STRING;
        String dayPrecede = this.day < FIRST_DOUBLE_DIGIT_MONTH_DAY ? FILLER : Main.EMPTY_STRING;
        return this.year + DATE_SEPARATOR + monthPrecede + this.month + DATE_SEPARATOR + dayPrecede + this.day;
    }

    @Override
    public int compareTo(Date otherDate) {
        if (this.equals(otherDate)) {
            return Main.COMPARE_NEUTRAL;
        }

        int comparison;
        int yearComp = Integer.compare(this.year, otherDate.year);
        int monthComp = Integer.compare(this.month, otherDate.month);

        if (yearComp != Main.COMPARE_NEUTRAL) {
            comparison = yearComp;
        } else if (monthComp != Main.COMPARE_NEUTRAL) {
            comparison = monthComp;
        } else {
            comparison = Integer.compare(this.day, otherDate.day);
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
        Date other = (Date) o;
        return this.day == other.day && this.month == other.month && this.year == other.year;
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, month, year);
    }
}
