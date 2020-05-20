package edu.kit.informatik;

/**
 * This class is a representation of a real life intersection and contains methods to manipulate or check its status.
 * @author Tarık Polat
 * @version 1.0.0
 */
public class Intersection {

    private final TrafficLight[] trafficLights;

    /**
     * Constructs an intersection object with 4 traffic lights. Traffic lights are indexed from 0 to 3.
     * The traffic lights with indexes 0 and 1 are green and the others red.
     */
    public Intersection() {
        trafficLights = new TrafficLight[4];

        for (int i = 0; i < trafficLights.length; i++) {
            trafficLights[i] = new TrafficLight();
        }

        trafficLights[0].switchLights();
        trafficLights[1].switchLights();
    }

    /**
     * Switches the states of the traffic lights. A traffic light turns green if it is red and vice versa.
     */
    public void switchLights() {
        trafficLights[0].switchLights();
        trafficLights[1].switchLights();
        trafficLights[2].switchLights();
        trafficLights[3].switchLights();
    }

    /**
     * Checks if the traffic light with the given index is safe to cross (green).
     * @param trafficLightIndex is the traffic light index to be checked.
     * @return true if the given traffic light is safe to cross (green).
     */
    public boolean isSafeToCross(int trafficLightIndex) {
        return trafficLights[trafficLightIndex].isGreenOn();
    }
}
