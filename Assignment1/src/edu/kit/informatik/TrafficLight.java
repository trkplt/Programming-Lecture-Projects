package edu.kit.informatik;

/**
 * This class represents a real life traffic light and contains methods to manipulate or check its status.
 * @author Tarık Polat
 * @version 1.0.0
 */
public class TrafficLight {

    private final Light redLight;
    private final Light greenLight;

    /**
     * Constructs a traffic light object with a red and a green light. The red light is on and the green is off.
     */
    protected TrafficLight() {
        this.redLight = new Light(true);
        this.greenLight = new Light(false);
    }

    /**
     * Switches the lights of the traffic light. Switches on light on if it is off and vice versa.
     */
    protected void switchLights() {
        redLight.switchLight();
        greenLight.switchLight();
    }

    /**
     * Checks if the green light of the corresponding traffic light is on.
     * @return true if the green light is on.
     */
    protected boolean isGreenOn() {
        return greenLight.isOn();
    }
}
