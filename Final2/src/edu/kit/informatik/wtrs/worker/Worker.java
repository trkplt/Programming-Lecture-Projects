package edu.kit.informatik.wtrs.worker;

import edu.kit.informatik.wtrs.regulator.WorkTimeRegulator;
import edu.kit.informatik.wtrs.time.Date;
import edu.kit.informatik.wtrs.time.ExactTime;
import edu.kit.informatik.wtrs.time.WorkTime;
import edu.kit.informatik.wtrs.ui.ErrorMessage;

public class Worker {

    private static int idCounter = 0;

    private final int id;
    private final String name;
    private final Date birthday;
    private final WorkerType type;
    private final WorkTimeRegulator regulator;

    //TODO: VALIDITY CHECKS IN WORKTIMERECORDINGSYSTEM
    public Worker(String name, Date birthday, WorkerType type) {
        idCounter++;
        this.id = idCounter;
        this.name = name;
        this.birthday = birthday;
        this.type = type;
        this.regulator = this.type.workTimeRegulatorFactory();
    }

    public ErrorMessage addNewWorkTime(WorkTime workTime) {
        return regulator.addNewWorkTime(workTime);
    }

    public WorkTime[] workTimesToArray() {
        return this.regulator.workTimesToArray();
    }

    public boolean isAssignedAt(ExactTime time) {
        return this.regulator.isAssignedAt(time);
    }
}
