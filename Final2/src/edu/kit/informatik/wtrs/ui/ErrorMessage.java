package edu.kit.informatik.wtrs.ui;

public enum ErrorMessage {

    //TODO: MAGIC STRING?
    ERROR_INIT("Error, "),
    ILLEGAL_YEAR(ERROR_INIT.message + "the given year is not from interval [1000, 9999]."),
    ILLEGAL_MONTH(ERROR_INIT.message + "the given month is not from interval [1, 12]."),
    ILLEGAL_DAY(ERROR_INIT.message + "the given day is not within the boundaries of the given month!"),
    ILLEGAL_TIME(ERROR_INIT.message + "the given hour is not within the hours of a day!"),
    ILLEGAL_WORKER_ON_HOLIDAY(ERROR_INIT.message + "the given work time intersects with a holiday, "
            + "the given worker cannot work on holidays!"),
    PAUSE_NEEDED(ERROR_INIT.message + "the given work time contains interval(s) exceeding 6 hours without a pause!"),
    MIN_PAUSE_NOT_PROVIDED(ERROR_INIT.message + "the given pause time does not meet the minimum conditions!"),
    WORK_TIME_INTERSECTS_ANOTHER(ERROR_INIT.message + "the given work time intersects with an already registered "
            + "work time!"),
    MINIMUM_REST_TIME(ERROR_INIT.message + "the rest time after work of the given work time is less than 10 hours!"),
    REST_TIME_CONFLICT(ERROR_INIT.message + "extended rest times do not cover all the reduced rest times if the "
            + "given work time is assigned to the given worker!");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
