package edu.kit.informatik.wtrs.time;

public enum WorkTimeType {

    WITH_PAUSE() {
        @Override
        Duration getPureWorkTime(ExactTime workTimeStart, ExactTime workTimeEnd, ExactTime pauseStart, ExactTime pauseEnd) {
            return null;
        }
    },

    WITHOUT_PAUSE() {
        @Override
        Duration getPureWorkTime(ExactTime workTimeStart, ExactTime workTimeEnd, ExactTime pauseStart, ExactTime pauseEnd) {
            return null;
        }
    };

    //TODO: ACCESS MODIFIER
    abstract Duration getPureWorkTime(ExactTime workTimeStart, ExactTime workTimeEnd,
                                      ExactTime pauseStart, ExactTime pauseEnd);
}
