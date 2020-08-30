package edu.kit.informatik.wtrs.time;

import edu.kit.informatik.wtrs.ui.Main;

public class PauseTime extends Interval implements Comparable<Interval> {

    protected PauseTime(ExactTime start, ExactTime end) {
        super(start, end);
    }

    @Override
    public int pureDurationInMinutesBefore(Date date) {
        return this.minutesBefore(date);
    }
}
