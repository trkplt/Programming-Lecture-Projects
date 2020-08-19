package edu.kit.informatik.wtrs.ui;

public enum ErrorMessage {

    //TODO: MAGIC STRING?
    ERROR_INIT("Error, "),
    ILLEGAL_YEAR(ERROR_INIT.getMessage() + "the given year is not from interval [1000, 9999]."),
    ILLEGAL_MONTH(ERROR_INIT.getMessage() + "the given month is not from interval [1, 12]."),
    ILLEGAL_DAY(ERROR_INIT.getMessage() + "the given day is not within the boundaries of the given month!"),
    ILLEGAL_TIME(ERROR_INIT.getMessage() + "the given hour is not within the hours of a day!");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
