package edu.kit.informatik.wtrs.time;

public enum Day {

    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6),
    SUNDAY(0);

    private final int index;

    Day(int index) {
        this.index = index;
    }

    private int getIndex() {
        return this.index;
    }

    //TODO: ACCESS MODIFIER
    static Day getDay(int index) {
        for (Day day : Day.values()) {
            if (day.getIndex() == index) {
                return day;
            }
        }
        return null;
    }
}
