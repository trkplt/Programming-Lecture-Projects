public enum Month {

    JANUARY(1, 31),
    FEBRUARY(2, 28),
    MARCH(3, 31),
    APRIL(4, 30),
    MAY(5, 31),
    JUNE(6, 30),
    JULY(7, 31),
    AUGUST(8, 31),
    SEPTEMBER(9, 30),
    OCTOBER(10, 31),
    NOVEMBER(11, 30),
    DECEMBER(12, 31);

    private final int index;
    private final int numberOfDays;

    Month(int index, int numberOfDays) {
        this.index = index;
        this.numberOfDays = numberOfDays;
    }

    private int getIndex() {
        return this.index;
    }

    //TODO: ACCESS MODIFIER
    int getNumberOfDays(int year) {
        int realNumberOfDays = this.numberOfDays;

        if (this.equals(Month.FEBRUARY)) {
            final int normalYearAddition = 0;
            final int leapYearAddition = 1;
            realNumberOfDays += Date.isLeapYear(year) ? leapYearAddition : normalYearAddition;
        }

        return realNumberOfDays;
    }

    //TODO: ACCESS MODIFIER
    static Month getMonth(int index) {
        for (Month month : Month.values()) {
            if (month.getIndex() == index) {
                return month;
            }
        }
        return null;
    }
}
