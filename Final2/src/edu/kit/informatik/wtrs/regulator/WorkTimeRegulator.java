package edu.kit.informatik.wtrs.regulator;

import edu.kit.informatik.wtrs.time.WorkTime;
import edu.kit.informatik.wtrs.time.WorkTimeComparator;

import java.util.TreeSet;

public class WorkTimeRegulator {

    private final TreeSet<WorkTime> workTimes;
    private final DayChecker dayChecker;
    private final PauseChecker pauseChecker;
    private final AfterWorkChecker afterWorkChecker;
    private final WorkingHoursChecker workingHoursChecker;

    public WorkTimeRegulator(DayChecker dayChecker, PauseChecker pauseChecker, AfterWorkChecker afterWorkChecker,
                             WorkingHoursChecker workingHoursChecker) {
        this.workTimes = new TreeSet<>(new WorkTimeComparator());
        this.dayChecker = dayChecker;
        this.pauseChecker = pauseChecker;
        this.afterWorkChecker = afterWorkChecker;
        this.workingHoursChecker = workingHoursChecker;
    }
}
