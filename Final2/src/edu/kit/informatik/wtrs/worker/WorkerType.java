package edu.kit.informatik.wtrs.worker;

import edu.kit.informatik.wtrs.regulator.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public enum WorkerType {

    REGULAR(new AWorkTimeRegulator()),

    NIGHT(new NWorkTimeRegulator()),

    PRODUCTION(new PWorkTimeRegulator()),

    NIGHT_PRODUCTION(new NPWorkTimeRegulator());

    private final Constructor<? extends WorkTimeRegulator> regulatorConstructor;

    WorkerType(WorkTimeRegulator regulator) {
        Constructor<? extends WorkTimeRegulator> tempConstructor;
        try {
            tempConstructor = regulator.getClass().getConstructor();
        } catch (NoSuchMethodException e) {
            tempConstructor = null;
        }
        this.regulatorConstructor = tempConstructor;
    }

    protected WorkTimeRegulator workTimeRegulatorFactory() {
        WorkTimeRegulator newInstance;

        try {
            newInstance = this.regulatorConstructor.newInstance();
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            newInstance = null;
        }
        return newInstance;
    }
}
