package edu.kit.informatik.wtrs;

import edu.kit.informatik.wtrs.time.Date;
import edu.kit.informatik.wtrs.time.ExactTime;
import edu.kit.informatik.wtrs.time.WorkTime;
import edu.kit.informatik.wtrs.ui.ProgramState;
import edu.kit.informatik.wtrs.worker.Worker;
import edu.kit.informatik.wtrs.worker.WorkerType;

import java.util.HashMap;

public class WorkTimeRecordingSystem {

    private ProgramState state;
    private HashMap<Integer, Worker> workers;

    public WorkTimeRecordingSystem() {
        this.state = ProgramState.RUNNING;
        this.workers = new HashMap<>();
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
}
