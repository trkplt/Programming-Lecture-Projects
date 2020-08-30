package edu.kit.informatik.wtrs.time;

public class RestTime extends Interval implements Comparable<Interval> {

    //in minutes
    public static final int MIN_REDUCED_REST_TIME = 600;
    //in minutes
    private static final int MIN_REST_TIME = 660;
    //in minutes
    private static final int MIN_EXTENDED_REST_TIME = 720;

    private final WorkTime causeWorkTime;
    private RestTime extendBeneficiary;

    public RestTime(ExactTime start, ExactTime end, WorkTime causeWorkTime, RestTime extendBeneficiary) {
        super(start, end);
        this.causeWorkTime = causeWorkTime;
        this.extendBeneficiary = extendBeneficiary;
    }

    public RestTime(ExactTime start, ExactTime end, WorkTime causeWorkTime) {
        this(start, end, causeWorkTime, null);
    }

    public WorkTime getCauseWorkTime() {
        return this.causeWorkTime;
    }

    public boolean meetsMinimum() {
        return this.duration().toMinutes() >= MIN_REDUCED_REST_TIME;
    }

    public boolean isReduced() {
        return this.meetsMinimum() && this.duration().toMinutes() < MIN_REST_TIME;
    }

    public boolean isExtended() {
        return this.duration().toMinutes() >= MIN_EXTENDED_REST_TIME;
    }

    public boolean hasBeneficiary() {
        return extendBeneficiary == null;
    }

    public RestTime newRestWithNewEnd(ExactTime end) {
        RestTime newRestTime = new RestTime(this.getStart(), end, this.causeWorkTime);

        if (this.hasBeneficiary() && newRestTime.isExtended()) {
            newRestTime.addExtendBeneficiary(this.extendBeneficiary);
        }

        return newRestTime;
    }

    public RestTime getExtendBeneficiary() {
        return this.extendBeneficiary;
    }

    public void addExtendBeneficiary(RestTime restTime) {
        this.extendBeneficiary = restTime;
    }

    @Override
    public long pureDurationInMinutesBefore(Date date) {
        return this.minutesBefore(date);
    }
}
