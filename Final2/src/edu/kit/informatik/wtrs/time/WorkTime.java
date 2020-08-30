package edu.kit.informatik.wtrs.time;

import java.util.ArrayList;
import java.util.Collection;

public class WorkTime extends Interval implements Comparable<Interval> {

    private final PauseTime pause;

    //TODO: CHECK VALIDITY AND ACCESS MODIFIER
    public WorkTime(ExactTime workTimeStart, ExactTime workTimeEnd, ExactTime pauseStart, ExactTime pauseEnd) {
        super(workTimeStart, workTimeEnd);
        this.pause = new PauseTime(pauseStart, pauseEnd);
    }

    //TODO: ACCESS MODIFIER
    WorkTime(ExactTime workTimeStart, ExactTime workTimeEnd) {
        this(workTimeStart, workTimeEnd, null, null);
    }

    public long pauseInMinutes() {
        return this.pause.duration().toMinutes();
    }

    public long pureWorkInMinutes() {
        return Duration.differenceBetween(this.duration(), this.pause.duration()).toMinutes();
    }

    @Override
    public long pureDurationInMinutesBefore(Date date) {
        return this.minutesBefore(date) - this.pause.minutesBefore(date);
    }

    public Collection<Long> workTimeBlocksInMinutes() {
        Collection<Long> durations = new ArrayList<>();

        durations.add(this.getStart().durationTo(this.pause.getStart()).toMinutes());
        durations.add(this.pause.getEnd().durationTo(this.getEnd()).toMinutes());
        return durations;
    }

    public Date periodBorder(int duration, boolean isDurationWeeks) {
        return this.getStartDate().periodBorder(duration, isDurationWeeks);
    }
}
