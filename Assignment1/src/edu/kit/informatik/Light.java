package edu.kit.informatik;

/**
 * This class represents a real life light and contains methods to manipulate and check its status.
 * @author Tarık Polat
 * @version 1.0.0
 */
public class Light {

    private boolean on;

    /**
     * Constructer to create a light object.
     * @param init represents the initial status of the light. True means on.
     */
    protected Light(boolean init) {
        this.on = init;
    }

    /**
     * Switches the light on if it is off and switches it off otherwise.
     */
    protected void switchLight() {
        on = !on;
    }

    /**
     * Checks if the light is on.
     * @return true if the light is on.
     */
    protected boolean isOn() {
        return on;
    }
}
