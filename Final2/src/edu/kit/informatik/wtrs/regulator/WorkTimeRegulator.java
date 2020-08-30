package edu.kit.informatik.wtrs.regulator;

import edu.kit.informatik.wtrs.time.*;
import edu.kit.informatik.wtrs.time.Date;
import edu.kit.informatik.wtrs.ui.ErrorMessage;
import edu.kit.informatik.wtrs.ui.Main;

import java.util.*;

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
    private static final int FOUR_WEEKS_PERIOD = 4;
    private static final int ONE_MONTH_PERIOD = 1;
    private static final int TWENTY_FOUR_WEEKS_PERIOD = 24;
    private static final int SIX_MONTHS_PERIOD = 6;
    private static final int BACKWARD_PERIOD_MULTIPLIER = -1;

    private final TreeSet<WorkTime> workTimes;
    private final TreeSet<RestTime> restTimes;
    private final TreeSet<RestDay> restDays;

    protected WorkTimeRegulator() {
        this.workTimes = new TreeSet<>();
        this.restTimes = new TreeSet<>();
        this.restDays = new TreeSet<>();
    }

    private static ErrorMessage checkPauseValidity(WorkTime workTime) {
        Collection<Long> workDurations = workTime.workTimeBlocksInMinutes();
        for (Long duration : workDurations) {
            if (duration > MAX_WORK_TIME_WITHOUT_PAUSE) {
                return ErrorMessage.PAUSE_NEEDED;
            }
        }

        int minNeededPause;
        long pureWorkDuration = workTime.pureWorkInMinutes();

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
        long intervalTimeSum = Main.COUNTER_START_ZERO;
        long intervalCounter = Main.COUNTER_START_ZERO;

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
        WorkTime nextWorkTime = /*null;
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

        */this.workTimes.higher(workTime);

        RestTime restTime;
        if (nextWorkTime == null) {
            restTime = new RestTime(workTime.getEnd(), ExactTime.lastMomentPossible(), workTime);
        } else {
            restTime = new RestTime(workTime.getEnd(), nextWorkTime.getStart().previousMoment(), workTime);
        }

        return restTime;
    }

    //calculates the average including restTime of given workTime, which is given as restTimeToAdd
    private double averageRestTimeIn(WorkTime workTime, RestTime restTimeToAdd,
                                     int duration, boolean isDurationWeeks) {
        return averageIn(this.restTimes, workTime, restTimeToAdd, duration, isDurationWeeks);
    }

    private static boolean withinGivenPeriodBackward(int period, boolean isDurationWeeks,
                                              ExactTime origin, ExactTime timeToExamine) {
        Date exclusivePeriodDate = origin.periodBorder(period * BACKWARD_PERIOD_MULTIPLIER, isDurationWeeks);
        return exclusivePeriodDate.compareTo(timeToExamine.getDate()) < Main.COMPARE_NEUTRAL
                && timeToExamine.getDate().compareTo(origin.getDate()) <= Main.COMPARE_NEUTRAL;
    }

    private static boolean withinGivenPeriodForward(int period, boolean isDurationWeeks,
                                             ExactTime origin, ExactTime timeToExamine) {
        Date exclusivePeriodDate = origin.periodBorder(period, isDurationWeeks);
        return origin.getDate().compareTo(timeToExamine.getDate()) <= Main.COMPARE_NEUTRAL
                && timeToExamine.getDate().compareTo(exclusivePeriodDate) < Main.COMPARE_NEUTRAL;
    }

    private static boolean withinGivenPeriod(int period, ExactTime origin, ExactTime timeToExamine) {
        if (origin.getDate().equals(timeToExamine.getDate())) {
            return true;
        }

        boolean isDurationWeeks = false;
        int equivalentPeriod = Main.COUNTER_START_ZERO;

        if (period == FOUR_WEEKS_PERIOD) {
            isDurationWeeks = true;
            equivalentPeriod = ONE_MONTH_PERIOD;
        } else if (period == TWENTY_FOUR_WEEKS_PERIOD) {
            isDurationWeeks = true;
            equivalentPeriod = SIX_MONTHS_PERIOD;
        } else if (period == ONE_MONTH_PERIOD) {
            equivalentPeriod = FOUR_WEEKS_PERIOD;
        } else if (period == SIX_MONTHS_PERIOD) {
            equivalentPeriod = TWENTY_FOUR_WEEKS_PERIOD;
        }

        if (timeToExamine.compareTo(origin) < Main.COMPARE_NEUTRAL) {
            return withinGivenPeriodBackward(period, isDurationWeeks, origin, timeToExamine)
                    || withinGivenPeriodBackward(equivalentPeriod, !isDurationWeeks, origin, timeToExamine);
        } else {
            return withinGivenPeriodForward(period, isDurationWeeks, origin, timeToExamine)
                    || withinGivenPeriodBackward(equivalentPeriod, !isDurationWeeks, origin, timeToExamine);
        }
    }

    private boolean newExtendedArrangementsPossible(WorkTime workTime, RestTime mandatoryToFindExtended,
                                                    int numberOfNeededArrangements, RestTime newestRestTime) {
        int counter = Main.COUNTER_START_ZERO;

        if (newestRestTime.isExtended()) {
            ++counter;
        }

        for (RestTime restTime : this.restTimes) {
            ExactTime restCauseStart = restTime.getCauseWorkTime().getStart();

            if (restTime.isExtended() && !restTime.hasBeneficiary()
                    && ((counter > Main.COUNTER_START_ZERO
                    && withinGivenPeriod(ONE_MONTH_PERIOD, workTime.getStart(), restCauseStart))
                    || (counter == Main.COUNTER_START_ZERO
                    && withinGivenPeriod(ONE_MONTH_PERIOD, mandatoryToFindExtended.getStart(), restCauseStart)))) {
                ++counter;
            }

            if (counter >= numberOfNeededArrangements) {
                return true;
            }
        }

        return false;
    }

    private boolean arrangeNewExtendedFor(RestTime beneficiary, RestTime newestRestTime) {
        if (newestRestTime.isExtended()) {
            newestRestTime.addExtendBeneficiary(beneficiary);
            return true;
        }

        RestTime extended = null;

        for (RestTime restTime : restTimes) {
            if (restTime.isExtended() && !restTime.hasBeneficiary()) {
                extended = restTime;
                break;
            }
        }

        if (extended != null) {
            extended.addExtendBeneficiary(beneficiary);
            return true;
        }

        return false;
    }

    private boolean arrangeNewExtendedFor(RestTime first, RestTime second, RestTime newestRestTime) {
        int neededExtendedCount = 2;
        List<RestTime> extendedRestTimes = new ArrayList<>();

        if (newestRestTime.isExtended()) {
            extendedRestTimes.add(newestRestTime);
        }

        for (RestTime restTime : restTimes) {
            if (restTime.isExtended() && !restTime.hasBeneficiary()) {
                extendedRestTimes.add(restTime);
            }

            if (extendedRestTimes.size() == neededExtendedCount) {
                break;
            }
        }

        if (extendedRestTimes.size() >= neededExtendedCount) {
            int firstIndex = 1;
            int secondIndex = 2;

            extendedRestTimes.get(firstIndex).addExtendBeneficiary(first);
            extendedRestTimes.get(secondIndex).addExtendBeneficiary(second);

            return true;
        }

        return false;
    }

    private ErrorMessage restTimeArrangementsPossible(WorkTime workTime, RestTime currentRestTime) {
        if (!currentRestTime.meetsMinimum()) {
            return ErrorMessage.MINIMUM_REST_TIME;
        }

        RestTime intersectedRestTime = null;
        for (Interval restTime : restTimes) {
            if (workTime.equals(restTime)) {
                intersectedRestTime = (RestTime) restTime;
                break;
            }
        }

        boolean newArrangementsPossible = false;

        RestTime intRestBeneficiary = intersectedRestTime == null ? null : intersectedRestTime.getExtendBeneficiary();
        boolean intRestHasBeneficiary = intersectedRestTime != null && intersectedRestTime.hasBeneficiary();
        RestTime newRestForIntersect = intersectedRestTime == null ? null
                : intersectedRestTime.newRestWithNewEnd(workTime.getEnd());
        boolean newRestReduced = newRestForIntersect != null && newRestForIntersect.isReduced();
        boolean newRestExtended = newRestForIntersect != null && newRestForIntersect.isExtended();
        boolean newRestNormal = !newRestReduced && !newRestExtended;
        int numberOfNeededArrangements;

        if (newRestReduced && intRestHasBeneficiary) {
            numberOfNeededArrangements = 2;
            newArrangementsPossible
                    = this.newExtendedArrangementsPossible(workTime, intRestBeneficiary,
                    numberOfNeededArrangements, currentRestTime);
        } else if (newRestReduced) {
            numberOfNeededArrangements = 1;
            newArrangementsPossible
                    = this.newExtendedArrangementsPossible(workTime, newRestForIntersect,
                    numberOfNeededArrangements, currentRestTime);
        } else if (newRestNormal && intRestHasBeneficiary) {
            numberOfNeededArrangements = 1;
            newArrangementsPossible
                    = this.newExtendedArrangementsPossible(workTime, intRestBeneficiary,
                    numberOfNeededArrangements, currentRestTime);
        } else {
            newArrangementsPossible = true;
        }

        if (newArrangementsPossible) {
            return null;
        } else {
            return ErrorMessage.REST_TIME_CONFLICT;
        }
    }

    private void arrangeRestTime(WorkTime workTime, RestTime currentRestTime) {
        RestTime intersectedRestTime = null;
        for (Interval restTime : restTimes) {
            if (workTime.equals(restTime)) {
                intersectedRestTime = (RestTime) restTime;
                break;
            }
        }

        boolean intRestHasBeneficiary = intersectedRestTime.hasBeneficiary();
        RestTime intRestBeneficiary = intersectedRestTime.getExtendBeneficiary();

        RestTime newRestForIntersect = intersectedRestTime.newRestWithNewEnd(workTime.getEnd());
        boolean newRestReduced = newRestForIntersect.isReduced();
        boolean newRestExtended = newRestForIntersect.isExtended();
        boolean newRestNormal = !newRestReduced && !newRestExtended;

        boolean arrangementsMade;

        if (newRestReduced && intRestHasBeneficiary) {
            arrangementsMade = this.arrangeNewExtendedFor(intRestBeneficiary, newRestForIntersect, currentRestTime);
        } else if (newRestReduced) {
            arrangementsMade = this.arrangeNewExtendedFor(newRestForIntersect, currentRestTime);
        } else if (newRestNormal && intRestHasBeneficiary) {
            arrangementsMade = this.arrangeNewExtendedFor(intRestBeneficiary, currentRestTime);
        } else if (newRestNormal) {
            arrangementsMade = true;
        } else {
            arrangementsMade = true;
        }

        if (arrangementsMade) {
            this.restTimes.remove(intersectedRestTime);
            this.restTimes.add(newRestForIntersect);
            this.restTimes.add(currentRestTime);
        }
    }

    private ErrorMessage arrangeRestTimeAndDay(WorkTime workTime) {
        RestTime currentRestTime = this.getRestTimeOf(workTime);
        ErrorMessage restTimeError = this.restTimeArrangementsPossible(workTime, currentRestTime);
        ErrorMessage restDayError = this.restDayArrangementsPossible();

        if (restTimeError != null) {
            return restTimeError;
        } else if (restDayError != null) {
            return restDayError;
        } else {
            this.arrangeRestTime(workTime, currentRestTime);
            this.arrangeRestDay();
        }
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

        errorMessage = this.arrangeRestTimeAndDay(workTime);
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
