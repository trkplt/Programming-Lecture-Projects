package edu.kit.informatik;

/**
 * This class represents a real life aircraft.
 * @author Tarik Polat
 * @version 1.0.0
 */
public class Aircraft {

    //These are final because they cannot be changed once the aircraft is manufactured.
    private final String manufacturer;
    private final int serialNumber;
    private final Airport homeAirport;

    /**
     * Constructs an aircraft object with the given parameters.
     * @param manufacturer the name of the manufacturer
     * @param serialNumber the serial number of the aircraft
     * @param homeAirport the origin airport of the aircraft
     */
    public Aircraft(String manufacturer, int serialNumber, Airport homeAirport) {
        this.manufacturer = manufacturer;
        this.serialNumber = serialNumber;
        this.homeAirport = homeAirport;
    }

    /**
     * To get the name of the manufacturer. Because only the manufacturer name may be needed.
     * @return the name of the manufacturer
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * To get the serial number of the aircraft. Because only the serial number may be needed.
     * @return the serial number of the aircraft
     */
    public int getSerialNumber() {
        return serialNumber;
    }

    /**
     * To get the origin airport of the aircraft. Because only the origin airport may be needed.
     * @return the origin airport of the aircraft
     */
    public Airport getHomeAirport() {
        return homeAirport;
    }

    /**
     * To get a string representation of the aircraft. Contains manufacturer and serial number of the aircraft.
     * Origin airport is excluded because it is not a relevant information about aircraft in general.
     * @return the string representation of the aircraft
     */
    @Override
    public String toString() {
        return manufacturer + " " + serialNumber;
    }

    /**
     * Checks if the given object is equal with this aircraft object.
     * @param o the object which needs to be compared with the aircraft object
     * @return true if the two objects are the same object or if the given object is Aircraft type and their
     * manufacturers and serial numbers are the same, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aircraft aircraft = (Aircraft) o;
        return getSerialNumber() == aircraft.getSerialNumber()
                && getManufacturer().equals(aircraft.getManufacturer());
    }
}
