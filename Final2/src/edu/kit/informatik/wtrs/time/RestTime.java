package edu.kit.informatik.wtrs.time;

public class RestTime extends Interval implements Comparable<Interval> {

    private final WorkTime causeWorkTime;
    private WorkTime extendBeneficiary;

    public RestTime(ExactTime start, ExactTime end, WorkTime causeWorkTime, WorkTime extendBeneficiary) {
        super(start, end);
        this.causeWorkTime = causeWorkTime;
        this.extendBeneficiary = extendBeneficiary;
    }

    public RestTime(ExactTime start, ExactTime end, WorkTime causeWorkTime) {
        this(start, end, causeWorkTime, null);
    }

    public void addExtendBeneficiary(WorkTime workTime) {
        this.extendBeneficiary = workTime;
    }

    @Override
    public int pureDurationInMinutesBefore(Date date) {
        return this.minutesBefore(date);
    }
}
