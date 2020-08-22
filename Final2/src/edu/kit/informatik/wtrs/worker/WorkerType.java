package edu.kit.informatik.wtrs.worker;

import edu.kit.informatik.wtrs.regulator.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public enum WorkerType {

    REGULAR(new AWorkTimeRegulator()),

    NIGHT(new NWorkTimeRegulator()),

    PRODUCTION(new PWorkTimeRegulator()),

    NIGHTPRODUCTION(new NPWorkTimeRegulator());

    private final Constructor<? extends WorkTimeRegulator> regulatorConstructor;

    WorkerType(WorkTimeRegulator regulator) {
        Constructor<? extends WorkTimeRegulator> tempRegulator;
        try {
            tempRegulator = regulator.getClass().getConstructor();
        } catch (NoSuchMethodException e) {
            tempRegulator = null;
        }
        this.regulatorConstructor = tempRegulator;
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
