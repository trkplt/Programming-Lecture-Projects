package edu.kit.informatik.wtrs.ui;

import edu.kit.informatik.wtrs.time.ExactTime;
import edu.kit.informatik.wtrs.time.WorkTime;

public class Main {

    /**
     * This is a String with no characters in it. It can be used to initialize an empty String.
     */
    public static final String EMPTY_STRING = "";

    /**
     * This is a String with a normal left brackets in it. It can be used in patterns as a group initializer.
     */
    public static final String PATTERN_GROUP_INIT = "(";

    /**
     * This is a String with a normal right brackets in it. It can be used in patterns as a group ending.
     */
    public static final String PATTERN_GROUP_END = ")";

    /**
     * This is a String with a logical or character in it. It can be used in patterns.
     */
    public static final String PATTERN_OR = "|";

    /**
     * This is a String with a curly left brackets in it. It can be used in patterns.
     */
    public static final String PATTERN_COUNT_INIT = "{";

    /**
     * This is a String with a curly right brackets in it. It can be used in patterns.
     */
    public static final String PATTERN_COUNT_END = "}";

    /**
     * This is a String with a square left brackets in it. It can be used in patterns as a character class initializer.
     */
    public static final String PATTERN_CHAR_CLASS_INIT = "[";

    /**
     * This is a String with a square right brackets in it. It can be used in patterns as a character class ending.
     */
    public static final String PATTERN_CHAR_CLASS_END = "]";

    /**
     * This is a String containing a hyphen. It could be used as a range operator in patterns.
     */
    public static final String PATTERN_RANGE = "-";

    public static final int COMPARE_NEUTRAL = 0;

    public static final int COMPARE_PRIOR = -1;

    public static final int COMPARE_LATTER = 1;

    public static final int COUNTER_START_ZERO = 0;

    public static void main(String[] args) {
        ExactTime workTimeStart = new ExactTime(1964, 10, 18, 13, 37);
        ExactTime workTimeEnd = new ExactTime(1964, 10, 18, 19, 32);

        ExactTime pauseStart = new ExactTime(1964, 10, 18, 18, 50);
        ExactTime pauseEnd = new ExactTime(1964, 10, 18, 19, 32);

        WorkTime workTime = new WorkTime(workTimeStart, workTimeEnd, pauseStart, pauseEnd);
        System.out.println(workTime.pureWorkTime());
    }
}
