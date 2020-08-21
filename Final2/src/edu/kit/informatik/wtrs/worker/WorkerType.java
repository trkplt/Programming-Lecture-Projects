package edu.kit.informatik.wtrs.worker;

import edu.kit.informatik.wtrs.regulator.*;

public enum WorkerType {

    REGULAR() {
        @Override
        protected DayChecker getDayChecker() {
            return null;
        }

        @Override
        protected PauseChecker getPauseChecker() {
            return null;
        }

        @Override
        protected AfterWorkChecker getAfterWorkChecker() {
            return null;
        }

        @Override
        protected WorkingHoursChecker getWorkingHoursChecker() {
            return null;
        }
    };

    /*NIGHT,
    PRODUCTION,
    NIGHTPRODUCTION;*/

    protected WorkTimeRegulator getRegulator() {
        return new WorkTimeRegulator(this.getDayChecker(), this.getPauseChecker(), this.getAfterWorkChecker(),
                this.getWorkingHoursChecker());
    }

    protected abstract DayChecker getDayChecker();
    protected abstract PauseChecker getPauseChecker();
    protected abstract AfterWorkChecker getAfterWorkChecker();
    protected abstract WorkingHoursChecker getWorkingHoursChecker();
}
