package edu.kit.informatik.wtrs.regulator;

import edu.kit.informatik.wtrs.time.ExactTime;
import edu.kit.informatik.wtrs.time.WorkTime;
import edu.kit.informatik.wtrs.ui.ErrorMessage;

import java.util.TreeSet;

public abstract class WorkTimeRegulator {

    private final TreeSet<WorkTime> workTimes;

    protected WorkTimeRegulator() {
        this.workTimes = new TreeSet<>();
    }

    public abstract ErrorMessage addNewWorkTime(WorkTime workTime);

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
