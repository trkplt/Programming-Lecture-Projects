package edu.kit.informatik.wtrs.time;

import edu.kit.informatik.wtrs.ui.Main;

public class WorkTime implements Comparable<WorkTime> {

    private final ExactTime workTimeStart;
    private final ExactTime workTimeEnd;
    private final ExactTime pauseStart;
    private final ExactTime pauseEnd;

    //TODO: CHECK VALIDITY AND ACCESS MODIFIER
    public WorkTime(ExactTime workTimeStart, ExactTime workTimeEnd, ExactTime pauseStart, ExactTime pauseEnd) {
        this.workTimeStart = workTimeStart;
        this.workTimeEnd = workTimeEnd;
        this.pauseStart = pauseStart;
        this.pauseEnd = pauseEnd;
    }

    //TODO: ACCESS MODIFIER
    WorkTime(ExactTime workTimeStart, ExactTime workTimeEnd) {
        this(workTimeStart, workTimeEnd, null, null);
    }

    protected Duration roughWorkDuration() {
        return this.workTimeStart.durationTo(workTimeEnd);
    }

    protected Duration pauseDuration() {
        return this.pauseStart == null || this.pauseEnd == null ? null : this.pauseStart.durationTo(this.pauseEnd);
    }

    //TODO: ACCESS MODIFIER
    public boolean contains(ExactTime exactTime) {
        return this.workTimeStart.compareTo(exactTime) <= Main.COMPARE_NEUTRAL
                && this.workTimeEnd.compareTo(exactTime) >= Main.COMPARE_NEUTRAL;
    }

    //TODO: ACCESS MODIFIER
    public Duration pureWorkTime() {
        Duration rough = this.roughWorkDuration();
        Duration pause = this.pauseDuration();
        return Duration.differenceBetween(rough, pause);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorkTime other = (WorkTime) o;
        return this.contains(other.workTimeStart) || this.contains(other.workTimeEnd)
                || other.contains(this.workTimeStart) || other.contains(this.workTimeEnd);
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int hashCode = 1;

        hashCode = prime * hashCode + this.workTimeStart.hashCode();
        hashCode = prime * hashCode + this.workTimeEnd.hashCode();
        hashCode = prime * hashCode + (this.pauseStart == null ? 0 : this.pauseStart.hashCode());
        hashCode = prime * hashCode + (this.pauseEnd == null ? 0 : this.pauseEnd.hashCode());
        return hashCode;
    }

    @Override
    public int compareTo(WorkTime other) {
        if (this.equals(other)) {
            return 0;
        }

        int comparison;
        int hourComp = Integer.compare(this.workTimeStart.getHour(), other.workTimeStart.getHour());

        if (hourComp != Main.COMPARE_NEUTRAL) {
            comparison = hourComp;
        } else {
            comparison = Integer.compare(this.workTimeStart.getMinute(), other.workTimeStart.getMinute());
        }
        return comparison;
    }
}
