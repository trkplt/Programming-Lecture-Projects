package edu.kit.informatik.wtrs.time;

public class WorkTime {

    private final WorkTimeType type;
    private final ExactTime workTimeStart;
    private final ExactTime workTimeEnd;
    private final ExactTime pauseStart;
    private final ExactTime pauseEnd;

    //TODO: IMPLEMENT, CHECK VALIDITY AND ACCESS MODIFIER
    WorkTime(WorkTimeType type, ExactTime workTimeStart, ExactTime workTimeEnd,
             ExactTime pauseStart, ExactTime pauseEnd) {
        this.type = type;
        this.workTimeStart = workTimeStart;
        this.workTimeEnd = workTimeEnd;
        this.pauseStart = pauseStart;
        this.pauseEnd = pauseEnd;
    }

    /*Duration getRoughWorkTime() {

    }*/
}
