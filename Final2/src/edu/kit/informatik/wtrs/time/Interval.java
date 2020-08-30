package edu.kit.informatik.wtrs.time;

import edu.kit.informatik.wtrs.ui.Main;

import java.util.Collection;

public abstract class Interval implements Comparable<Interval> {

    private final ExactTime start;
    private final ExactTime end;

    //TODO: CHECK VALIDITY AND ACCESS MODIFIER
    public Interval(ExactTime start, ExactTime end) {
        this.start = start;
        this.end = end;
    }

    public abstract long pureDurationInMinutesBefore(Date date);

    public ExactTime getStart() {
        return this.start;
    }

    public ExactTime getEnd() {
        return this.end;
    }

    public Duration duration() {
        return this.start.durationTo(end);
    }

    protected long minutesBefore(Date date) {
        if (this.start.getDate().compareTo(date) >= Main.COMPARE_NEUTRAL) {
            return Main.COMPARE_NEUTRAL;
        }

        if (this.end.getDate().compareTo(date) >= Main.COMPARE_NEUTRAL) {
            return this.start.durationTo(ExactTime.firstMomentOf(date)).toMinutes();
        } else {
            return this.duration().toMinutes();
        }
    }

    public Date getStartDate() {
        return this.start.getDate();
    }

    public Collection<Date> dates() {
        return this.start.getDate().datesTo(this.end.getDate());
    }

    //TODO: ACCESS MODIFIER
    public boolean intersects(ExactTime exactTime) {
        return this.start.compareTo(exactTime) <= Main.COMPARE_NEUTRAL
                && this.end.compareTo(exactTime) >= Main.COMPARE_NEUTRAL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Interval other = (Interval) o;
        return this.intersects(other.start) || this.intersects(other.end)
                || other.intersects(this.start) || other.intersects(this.end);
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int hashCode = 1;

        hashCode = prime * hashCode + this.start.hashCode();
        hashCode = prime * hashCode + this.end.hashCode();
        return hashCode;
    }

    @Override
    public int compareTo(Interval other) {
        if (this.equals(other)) {
            return 0;
        }

        int comparison;
        int hourComp = Integer.compare(this.start.getHour(), other.start.getHour());

        if (hourComp != Main.COMPARE_NEUTRAL) {
            comparison = hourComp;
        } else {
            comparison = Integer.compare(this.start.getMinute(), other.start.getMinute());
        }
        return comparison;
    }
}
