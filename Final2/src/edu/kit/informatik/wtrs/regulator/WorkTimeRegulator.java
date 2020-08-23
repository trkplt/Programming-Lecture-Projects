package edu.kit.informatik.wtrs.regulator;

import edu.kit.informatik.wtrs.time.ExactTime;
import edu.kit.informatik.wtrs.time.WorkTime;
import edu.kit.informatik.wtrs.ui.ErrorMessage;

import java.util.TreeSet;

public abstract class WorkTimeRegulator {

    private final TreeSet<WorkTime> workTimes;
    private final TreeSet<WorkTime> assignedRestTimes;

    protected WorkTimeRegulator() {
        this.workTimes = new TreeSet<>();
        this.assignedRestTimes = new TreeSet<>();
    }

    public abstract ErrorMessage addNewWorkTime(WorkTime workTime);

    protected abstract ErrorMessage checkAverageWorkTime(WorkTime workTime);



    private ErrorMessage checkPauseValidity() {

    }

    private void removeRestTime(WorkTime restTime) {

    }

    //hours: min. 11h ya da min. 12h'dan hangisi olduğunu söyleyecek
    private ErrorMessage findAndPlaceRestTime(WorkTime workTime, int hours) {

    }

    private ErrorMessage checkRestTime(WorkTime workTime) {

    }

    protected ErrorMessage checkAverageWorkTimeIn(WorkTime workTime, int days) {

    }



    public boolean isAssignedAt(ExactTime time) {
        for (WorkTime workTime : this.workTimes) {
            if (workTime.contains(time)) {
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
