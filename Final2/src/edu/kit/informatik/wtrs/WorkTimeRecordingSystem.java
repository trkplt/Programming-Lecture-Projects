package edu.kit.informatik.wtrs;

import edu.kit.informatik.wtrs.time.Date;
import edu.kit.informatik.wtrs.time.ExactTime;
import edu.kit.informatik.wtrs.time.WorkTime;
import edu.kit.informatik.wtrs.ui.ProgramState;
import edu.kit.informatik.wtrs.worker.Worker;
import edu.kit.informatik.wtrs.worker.WorkerType;

import java.util.HashMap;
import java.util.TreeSet;

public class WorkTimeRecordingSystem {

    private static final TreeSet<Date> HOLIDAYS = new TreeSet<>();

    private ProgramState state;
    private HashMap<Integer, Worker> workers;

    public WorkTimeRecordingSystem(String holidaysString) {
        this.state = ProgramState.RUNNING;
        this.workers = new HashMap<>();

        //TODO: PARSE STRING AND PLACE IN SET
    }

    public String addNewWorker(String name, Date birthday, WorkerType type) {

    }

    public String addNewWorkTime(int id, WorkTime workTime) {

    }

    public String listWorkTimes(int id) {

    }

    public String listWorkersAt(ExactTime exactTime) {

    }

    public void quit() {
        this.state = ProgramState.CLOSED;
    }

    public boolean isProgramClosed() {
        return this.state.equals(ProgramState.CLOSED);
    }

    public static boolean isHoliday(Date date) {
        return HOLIDAYS.contains(date);
    }
}
