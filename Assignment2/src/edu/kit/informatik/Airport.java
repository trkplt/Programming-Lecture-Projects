package edu.kit.informatik;

/**
 * This class represents a real life airport.
 * @author Tarik Polat
 * @version 1.0.0
 */
public class Airport {

    private String name;
    private Address address;

    /**
     * Constructs an airport object with the given parameters.
     * @param name the name of the airport
     * @param address the address of the airport
     */
    public Airport(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    /**
     * To get the name of the airport. Because the name may be needed individually.
     * @return the name of the airport
     */
    public String getName() {
        return name;
    }

    /**
     * To get the address of the airport. Because the address may be needed individually.
     * @return the address of the airport
     */
    public Address getAddress() {
        return address;
    }

    /**
     * To change the name of the airport. Because the name may be needed to change individually.
     * @param name the new name of the airport
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * To change the address of the airport. Because the address may be needed to change individually.
     * @param address the new address of the airport
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * To get a string representation of the airport. Contains name and address of the airport.
     * Both of those information are included because there are already separate getters for those.
     * @return the string representation of the airport
     */
    public String toString() {
        return name + ", " + address.toString();
    }
}
