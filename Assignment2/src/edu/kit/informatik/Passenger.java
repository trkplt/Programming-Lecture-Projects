package edu.kit.informatik;

/**
 * This class represents the real life passenger in flights.
 * @author Tarik Polat
 * @version 1.0.0
 */
public class Passenger {

    //Name and birthday are final because they cannot be changed due to safety reasons in flights.
    private final String name;
    private final Date birthday;
    private Address address;
    private FlightClass flightClass;

    /**
     * Constructs a passenger object with the given parameters.
     * @param name name of the passenger
     * @param birthday birthday of the passenger
     * @param flightClass flight class of the passenger which he booked for the flight
     */
    public Passenger(String name, Date birthday, FlightClass flightClass) {
        this.name = name;
        this.birthday = birthday;
        this.flightClass = flightClass;
    }

    /**
     * To get the name of the passenger. Because only the name of the passenger may be needed.
     * @return the name of the passenger
     */
    public String getName() {
        return name;
    }

    /**
     * To get the birthday of the passenger. Because only the birthday of the passenger may be needed.
     * @return the birthday of the passenger
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * To get the address of the passenger. Because only the address of the passenger may be needed.
     * @return the address of the passenger
     */
    public Address getAddress() {
        return address;
    }

    /**
     * To change the address of the passenger with the given one.
     * Because the address of the passenger may be needed to change.
     * @param address the new address of the passenger
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * To get the flight class of the passenger. Because only the flight class of the passenger may be needed.
     * @return the flight class of the passenger
     */
    public FlightClass getFlightClass() {
        return flightClass;
    }

    /**
     * To change the flight class of the passenger with the given one.
     *  Because only the flight class of the passenger may be needed to change.
     * @param flightClass the new flight class of the passenger
     */
    public void setFlightClass(FlightClass flightClass) {
        this.flightClass = flightClass;
    }

    /**
     * To get a string representation of the passenger. Contains name, birthday and flight class.
     * Address is excluded because generally it is not needed among other information about the passenger.
     * It can be reached by getAddress().
     * @return the string representation of the passenger
     */
    public String toString() {
        return "Name: " + name + ", Birthday: " + birthday.toString() + ", Flight Class: " + flightClass;
    }
}
