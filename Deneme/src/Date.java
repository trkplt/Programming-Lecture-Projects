public class Date implements Comparable<Date> {

    private static final int COUNTER_START_ZERO = 0;
    private static final int LEAP_YEAR_REMAINDER = 0;
    private static final int LEAP_YEAR_DIVISOR_FOUR = 4;
    private static final int LEAP_YEAR_DIVISOR_FOUR_HUNDRED = 400;
    private static final int LEAP_YEAR_ILLEGAL_DIVISOR_HUNDRED = 100;
    private static final int DAY_INDEX_HELPER_FOURTEEN = 14;
    private static final int DAYS_OF_YEAR = 365;
    private static final int DAYS_OF_LEAP_YEAR = 366;
    private static final int MIN_YEAR = 1000;
    private static final int MAX_YEAR = 9999;
    private static final int MIN_MONTH = 1;
    private static final int MAX_MONTH = 12;
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

    public static final String PATTERN = YEAR_PATTERN + DATE_SEPARATOR + MONTH_PATTERN + DATE_SEPARATOR + DAY_PATTERN;

    private final Month monthOfYear;
    private final int day;
    private final int month;
    private final int year;

    public Date(final int year, final int month, final int day) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.monthOfYear = Month.getMonth(month);
    }

    private static int getDayIndex(int year, int month, int day) {
        int yZero = year - ((DAY_INDEX_HELPER_FOURTEEN - month) / MAX_MONTH);
        int x = yZero + (yZero / LEAP_YEAR_DIVISOR_FOUR) - (yZero / LEAP_YEAR_ILLEGAL_DIVISOR_HUNDRED)
                + (yZero / LEAP_YEAR_DIVISOR_FOUR_HUNDRED);
        int mZero = month + (MAX_MONTH * ((DAY_INDEX_HELPER_FOURTEEN - month) / MAX_MONTH)) - 2;
        int dZero = (day + x + ((MAX_DAY_OF_MONTH * mZero) / MAX_MONTH)) % MAX_DAY_OF_WEEK;
        return dZero;
    }

    private int getDay() {
        return this.day;
    }

    private int getMonth() {
        return this.month;
    }

    private int getYear() {
        return this.year;
    }

    private Month getMonthOfYear() {
        return this.monthOfYear;
    }

    private boolean isLeapDayForward() {
        return this.getMonth() <= LEAP_MONTH_INDEX && this.getDay() < LEAP_DAY_INDEX;
    }

    //TODO: CORRECT?
    private int getCompleteYearsTo(Date otherDate) {
        int neutral = 0;
        if (this.compareTo(otherDate) > neutral) {
            return otherDate.getCompleteYearsTo(this);
        }

        int difference = otherDate.year - this.year;
        int lowerDifference = 1;

        if (difference == neutral) {
            return difference;
        }

        if (this.month > otherDate.month) {
            difference -= lowerDifference;
        } else if (this.month == otherDate.month && this.day > otherDate.day) {
            difference -= lowerDifference;
        }
        return difference;
    }

    private int getDayIndexInYear() {
        int numberOfDays = COUNTER_START_ZERO;
        for (int i = MIN_MONTH; i < this.month; i++) {
            numberOfDays += Month.getMonth(i).getNumberOfDays(this.year);
        }
        return numberOfDays + this.day;
    }

    private int getRemainderDaysInYear() {
        int numberOfDays = COUNTER_START_ZERO;
        for (int i = MAX_MONTH; i > this.month; i--) {
            numberOfDays += Month.getMonth(i).getNumberOfDays(this.year);
        }
        return numberOfDays + this.monthOfYear.getNumberOfDays(this.year) - this.day;
    }

    protected static boolean isLeapYear(int year) {
        boolean divisibleByFour = year % LEAP_YEAR_DIVISOR_FOUR == LEAP_YEAR_REMAINDER;
        boolean divisibleByFourHundred = year % LEAP_YEAR_DIVISOR_FOUR_HUNDRED == LEAP_YEAR_REMAINDER;
        boolean divisibleByHundred = year % LEAP_YEAR_ILLEGAL_DIVISOR_HUNDRED == LEAP_YEAR_REMAINDER;

        return divisibleByFourHundred || (divisibleByFour && !divisibleByHundred);
    }

    protected int getDaysTo(Date otherDate) {
        int neutral = 0;
        int difference = COUNTER_START_ZERO;

        if (this.compareTo(otherDate) > neutral) {
            return otherDate.getDaysTo(this);
        }

        int thisDayIndex = this.getDayIndexInYear();
        int otherDayIndex = otherDate.getDayIndexInYear();
        int thisRemainderDays = this.getRemainderDaysInYear();
        int otherRemainderDays = otherDate.getRemainderDaysInYear();

        int daysOfYear = isLeapYear(this.year) && this.isLeapDayForward() ? DAYS_OF_LEAP_YEAR : DAYS_OF_YEAR;

        for (int year = this.year; year < this.year + this.getCompleteYearsTo(otherDate); year++) {
            System.out.println(difference + " 1");
            difference += daysOfYear;
            System.out.println(difference + " 2");
        }

        if (thisDayIndex < otherDayIndex) {
            System.out.println(difference + " 3");
            difference += daysOfYear - (thisDayIndex + otherRemainderDays);
            System.out.println(difference + " 4");
        } else if (thisDayIndex > otherDayIndex) {
            System.out.println(difference + " 5");
            difference += thisRemainderDays + otherDayIndex;
            System.out.println(difference + " 6");
        }
        return difference;
    }

    @Override
    public String toString() {
        String monthPrecede = this.month < FIRST_DOUBLE_DIGIT_MONTH_DAY ? FILLER : Main.EMPTY_STRING;
        String dayPrecede = this.day < FIRST_DOUBLE_DIGIT_MONTH_DAY ? FILLER : Main.EMPTY_STRING;
        return this.year + DATE_SEPARATOR + monthPrecede + this.month + DATE_SEPARATOR + dayPrecede + this.day;
    }

    @Override
    public int compareTo(Date otherDate) {
        int former = -1;
        int neutral = 0;
        int latter = 1;
        int comparison;

        if (this == otherDate) {
            comparison = neutral;
        } else if (this.getYear() < otherDate.getYear()) {
            comparison = former;
        } else if (this.getYear() > otherDate.getYear()) {
            comparison = latter;
        } else if (this.getMonth() < otherDate.getMonth()) {
            comparison = former;
        } else if (this.getMonth() > otherDate.getMonth()) {
            comparison = latter;
        } else if (this.getDay() < otherDate.getDay()) {
            comparison = former;
        } else if (this.getDay() > otherDate.getDay()) {
            comparison = latter;
        } else {
            comparison = neutral;
        }
        return comparison;
    }
}
