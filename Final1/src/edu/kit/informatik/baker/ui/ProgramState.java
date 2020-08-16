package edu.kit.informatik.baker.ui;

/**
 * This enum contains the possible states this program can be in.
 *
 * @author Tarik Polat
 * @version 1.0.0
 */
public enum ProgramState {

    /**
     * This {@link edu.kit.informatik.baker.ui.ProgramState} indicates that the program is currently running.
     */
    RUNNING,

    /**
     * This {@link edu.kit.informatik.baker.ui.ProgramState} indicates that the program received the quit command and
     * the program will be closed soon.
     */
    CLOSED;
}
