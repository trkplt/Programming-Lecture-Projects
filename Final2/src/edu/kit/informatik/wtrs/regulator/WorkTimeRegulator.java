package edu.kit.informatik.wtrs.regulator;

import edu.kit.informatik.wtrs.time.*;
import edu.kit.informatik.wtrs.ui.ErrorMessage;
import edu.kit.informatik.wtrs.ui.Main;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

public abstract class WorkTimeRegulator {

    //in minutes
    private static final int MAX_WORK_TIME_WITHOUT_PAUSE = 360;
    //in minutes
    private static final int MAX_WORK_TIME_WITHOUT_EXTENDED_PAUSE = 560;
    //in minutes
    private static final int MIN_PAUSE_TIME = 0;
    //in minutes
    private static final int MIN_OBLIGATORY_PAUSE_TIME = 30;
    //in minutes
    private static final int MIN_EXTENDED_PAUSE_TIME = 45;

    private final TreeSet<WorkTime> workTimes;
    private final TreeSet<RestTime> restTimes;
    private final TreeSet<RestDay> restDays;

    protected WorkTimeRegulator() {
        this.workTimes = new TreeSet<>();
        this.restTimes = new TreeSet<>();
        this.restDays = new TreeSet<>();
    }

    private static ErrorMessage checkPauseValidity(WorkTime workTime) {
        Collection<Integer> workDurations = workTime.workTimeBlocksInMinutes();
        for (Integer duration : workDurations) {
            if (duration > MAX_WORK_TIME_WITHOUT_PAUSE) {
                return ErrorMessage.PAUSE_NEEDED;
            }
        }

        int minNeededPause;
        int pureWorkDuration = workTime.pureWorkInMinutes();

        if (pureWorkDuration <= MAX_WORK_TIME_WITHOUT_PAUSE) {
            minNeededPause = MIN_PAUSE_TIME;
        } else if (pureWorkDuration <= MAX_WORK_TIME_WITHOUT_EXTENDED_PAUSE) {
            minNeededPause = MIN_OBLIGATORY_PAUSE_TIME;
        } else {
            minNeededPause = MIN_EXTENDED_PAUSE_TIME;
        }

        if (workTime.pauseInMinutes() < minNeededPause) {
            return ErrorMessage.MIN_PAUSE_NOT_PROVIDED;
        }
        return null;
    }

    /*
    protected double averageWorkTimeIn(WorkTime workTime, int duration, boolean isDurationWeeks) {
        if (duration == Main.COMPARE_NEUTRAL) {
            return Main.COMPARE_NEUTRAL;
        }

        //exclusive
        Date onePeriodBorder = workTime.periodBorder(duration, isDurationWeeks);

        //exclusive
        Date periodStart;

        ///exclusive
        Date periodEnd;

        if (duration < Main.COMPARE_NEUTRAL) {
            periodStart = onePeriodBorder;
            periodEnd = workTime.getStartDate().nextDate();
        } else {
            periodStart = workTime.getStartDate().previousDate();
            periodEnd = onePeriodBorder;
        }

        Iterator<WorkTime> iterator = this.workTimes.iterator();
        int workTimeSum = Main.COUNTER_START_ZERO;
        int workDayCounter = Main.COUNTER_START_ZERO;

        while (iterator.hasNext()) {
            WorkTime currentWorkTime = iterator.next();
            Date currentWorkStartDate = currentWorkTime.getStartDate();

            int startComparison = currentWorkStartDate.compareTo(periodStart);
            if (startComparison <= Main.COMPARE_NEUTRAL) {
                continue;
            }

            int endComparison = currentWorkStartDate.compareTo(periodEnd);
            if (endComparison >= Main.COMPARE_NEUTRAL) {
                break;
            }

            workTimeSum += currentWorkTime.pureWorkInMinutesBefore(periodEnd);
            ++workDayCounter;
        }

        workTimeSum += workTime.pureWorkInMinutesBefore(periodEnd);
        ++workDayCounter;

        return (((double) workTimeSum) / ((double) workDayCounter));
    }
     */
    private static double averageIn(TreeSet<? extends Interval> setToExamine, WorkTime workTime, Interval currentIntervalToAdd,
                                    int duration, boolean isDurationWeeks) {
        if (duration == Main.COMPARE_NEUTRAL) {
            return Main.COMPARE_NEUTRAL;
        }

        //exclusive
        Date onePeriodBorder = workTime.periodBorder(duration, isDurationWeeks);

        //exclusive
        Date periodStart;

        ///exclusive
        Date periodEnd;

        if (duration < Main.COMPARE_NEUTRAL) {
            periodStart = onePeriodBorder;
            periodEnd = workTime.getStartDate().nextDate();
        } else {
            periodStart = workTime.getStartDate().previousDate();
            periodEnd = onePeriodBorder;
        }

        Iterator<? extends Interval> iterator = setToExamine.iterator();
        int intervalTimeSum = Main.COUNTER_START_ZERO;
        int intervalCounter = Main.COUNTER_START_ZERO;

        while (iterator.hasNext()) {
            Interval currentInterval = iterator.next();
            Date currentWorkStartDate = currentInterval.getStartDate();

            int startComparison = currentWorkStartDate.compareTo(periodStart);
            if (startComparison <= Main.COMPARE_NEUTRAL) {
                continue;
            }

            int endComparison = currentWorkStartDate.compareTo(periodEnd);
            if (endComparison >= Main.COMPARE_NEUTRAL) {
                break;
            }

            intervalTimeSum += currentInterval.pureDurationInMinutesBefore(periodEnd);
            ++intervalCounter;
        }

        //TODO: DEFTERDEKİ SON NOTA BAK VE GEREKİRSE DÜZELT
        intervalTimeSum += currentIntervalToAdd.pureDurationInMinutesBefore(periodEnd);
        ++intervalCounter;

        return (((double) intervalTimeSum) / ((double) intervalCounter));
    }

    protected abstract ErrorMessage checkAverageWorkTime(WorkTime workTime);

    /*
    private ErrorMessage checkHolidaysSundays(WorkTime workTime) {
        Collection<Date> dates = workTime.dates();

        for (Date date : dates) {
            if (WorkTimeRecordingSystem.isHoliday(date)) {
                return ErrorMessage.ILLEGAL_WORKER_ON_HOLIDAY;
            }
        }
        return null;
    }
     */
    protected abstract ErrorMessage checkHolidaysSundays(WorkTime workTime);

    protected double averageWorkTimeIn(WorkTime workTime, int duration, boolean isDurationWeeks) {
        return averageIn(this.workTimes, workTime, workTime, duration, isDurationWeeks);
    }

    private RestTime getRestTimeOf(WorkTime workTime) {
        WorkTime nextWorkTime = null;
        Iterator<WorkTime> iterator = this.workTimes.iterator();

        while (iterator.hasNext()) {
            WorkTime currentWorkTime = iterator.next();

            if (workTime.equals(currentWorkTime)) {
                return null;
            } else if (workTime.compareTo(currentWorkTime) < Main.COMPARE_NEUTRAL) {
                nextWorkTime = currentWorkTime;
                break;
            }
        }

        RestTime restTime;
        if (nextWorkTime == null) {
            restTime = new RestTime(workTime.getEnd(), ExactTime.lastMomentPossible(), workTime);
        } else {
            restTime = new RestTime(workTime.getEnd(), nextWorkTime.getStart().previousMoment(), workTime);
        }

        return restTime;
    }

    private double averageRestTimeIn(WorkTime workTime, int duration, boolean isDurationWeeks) {
        RestTime restTimeToAdd = this.getRestTimeOf(workTime);
        return averageIn(this.restTimes, workTime, restTimeToAdd, duration, isDurationWeeks);
    }

    private ErrorMessage checkRestTimeValidity() {

    }

    public ErrorMessage addNewWorkTime(WorkTime workTime) {
        ErrorMessage errorMessage = ErrorMessage.WORK_TIME_INTERSECTS_ANOTHER;
        if (this.workTimes.contains(workTime)) {
            return errorMessage;
        }

        errorMessage = this.checkHolidaysSundays(workTime);
        if (errorMessage != null) {
            return errorMessage;
        }

        errorMessage = checkPauseValidity(workTime);
        if (errorMessage != null) {
            return errorMessage;
        }

        errorMessage = this.checkRestTimeValidity();
        if (errorMessage != null) {
            return errorMessage;
        }
    }

    public boolean isAssignedAt(ExactTime time) {
        for (WorkTime workTime : this.workTimes) {
            if (workTime.intersects(time)) {
                return true;
            }
        }
        return false;
    }

    public WorkTime[] workTimesToArray() {
        WorkTime[] tempArray = new WorkTime[0];
        WorkTime[] workTimesArray = this.workTimes.toArray(tempArray);

        return workTimesArray.length != 0 ? workTimesArray : null;
    }
}
