package edu.kit.informatik.wtrs.time;

public class RestDay extends Interval implements Comparable<Interval> {

    private final WorkTime causeWorkTime;

    protected RestDay(ExactTime start, ExactTime end, WorkTime causeWorkTime) {
        super(start, end);
        this.causeWorkTime = causeWorkTime;
    }
}
