package edu.kit.informatik;

public class Flight {

    //flightCounter is static because it is supposed to count the number of constructed flight objects.
    private static int flightCounter = 0;
    private Airport departure;
    private Airport arrival;
    //id is final because it is unique to every flight and cannot be changed.
    private final int id;
    private Date departureDate;
    private int flightTime;
    //passengers array is final because there are exactly 100 passengers per flight.
    private final Passenger[] passengers = new Passenger[100];
    private Aircraft aircraft;

    /**
     * Constructs a flight object with the given parameters.
     * @param departure the departure airport
     * @param arrival the arrival airport
     * @param departureDate the departure date
     * @param flightTime the estimated airborne time
     * @param passengers the passengers who booked this flight
     * @param aircraft the aircraft which will serve this flight
     */
    public Flight(Airport departure, Airport arrival, Date departureDate, int flightTime,
                  Passenger[] passengers, Aircraft aircraft) {
        this.departure = departure;
        this.arrival = arrival;
        this.id = flightCounter;
        this.departureDate = departureDate;
        this.flightTime = flightTime;
        this.aircraft = aircraft;

        System.arraycopy(passengers, 0, this.passengers, 0, passengers.length);

        flightCounter++;
    }

    /**
     * To get the departure airport of the flight. Because only the departure may be needed.
     * @return the departure airport of the flight
     */
    public Airport getDeparture() {
        return departure;
    }

    /**
     * To get the arrival airport of the flight. Because only the arrival may be needed.
     * @return the arrival airport of the flight
     */
    public Airport getArrival() {
        return arrival;
    }

    /**
     * To get the unique ID of this flight. Because only the ID may be needed.
     * @return the unique ID of this flight
     */
    public int getId() {
        return id;
    }

    /**
     * To get the departure date of this flight. Because only the departure date may be needed.
     * @return the departure date of this flight.
     */
    public Date getDepartureDate() {
        return departureDate;
    }

    /**
     * To get the estimated flight time of this flight. Because only the flight time may be needed.
     * @return the estimated flight time of this flight
     */
    public int getFlightTime() {
        return flightTime;
    }

    /**
     * To get the passenger with the given index who booked this flight. Because a specific passenger may be needed.
     * @param index the index of the passenger
     * @return the passenger with the given index who booked this flight
     */
    public Passenger getPassenger(int index) {
        return passengers[index];
    }

    /**
     * To get all the passengers who booked this flight. Because all the passengers may be needed at once.
     * @return all the passengers who booked this flight
     */
    public Passenger[] getPassengers() {
        return passengers;
    }

    /**
     * To get the aircraft which will serve this flight. Because only the aircraft may be needed.
     * @return the aircraft which will serve this flight
     */
    public Aircraft getAircraft() {
        return aircraft;
    }

    /**
     * To change the departure airport of this flight with the given one.
     * Because only the departure may be needed to change.
     * @param airport the new departure airport
     */
    public void setDeparture(Airport airport) {
        this.departure = airport;
    }

    /**
     * To change the arrival airport of this flight with the given one.
     * Because only the arrival may be needed to change.
     * @param airport the new arrival airport
     */
    public void setArrival(Airport airport) {
        this.arrival = airport;
    }

    /**
     * To change the departure date of this flight with the given one.
     * Because only the departure date may be needed to change.
     * @param date the new departure date
     */
    public void setDepartureDate(Date date) {
        this.departureDate = date;
    }

    /**
     * To change the flight time of this flight with the given one. The time is in minutes.
     * Because only the flight time may be needed to change.
     * @param minutes the new flight time
     */
    public void setFlightTime(int minutes) {
        this.flightTime = minutes;
    }

    /**
     * To change the passenger with the given index with the given passenger.
     * Because a specific passenger may be needed to change.
     * @param index the index of the passenger
     * @param passenger the new passenger who booked this flight
     */
    public void setPassenger(int index, Passenger passenger) {
        this.passengers[index] = passenger;
    }

    /**
     * To change all the passengers of this flight with the given ones.
     * Because all the passengers may be needed to change at once (for reasons like transferring all the passengers to
     * another flight). An array with a size of exactly 100 shall be given.
     * Otherwise not all the passengers of this flight can be replaced.
     * @param passengers the new passengers
     */
    public void setPassengers(Passenger[] passengers) {
        for (int i = 0; i < passengers.length; i++) {
            setPassenger(i, passengers[i]);
        }
    }

    /**
     * To change the aircraft which will serve this flight with the given one.
     * Because only the aircraft may be needed to change.
     * @param aircraft the new aircraft
     */
    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    /**
     * To get a string representation of this flight. Contains departure, arrival, flight number, departure date,
     * flight time in minutes and aircraft information.
     * Passengers are excluded because generally that information is not needed in written representations of flights.
     * There is getPassenger(int index) and getPassengers() if needed.
     * @return the string representation of this flight
     */
    public String toString() {
        return "Departure: " + departure.getName() + ", Arrival: " + arrival.getName()
                + ", Flight Number: " + id + ", Departure Date: " + departureDate.toString()
                + ", Flight Time: " + flightTime + ", Aircraft: " + aircraft.toString();
    }

}
