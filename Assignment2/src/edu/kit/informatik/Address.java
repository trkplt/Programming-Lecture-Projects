package edu.kit.informatik;

/**
 * This class represents typical address from real world.
 * @author Tarik Polat
 * @version 1.0.0
 */
public class Address {

    private String street;
    private int houseNumber;
    private int postCode;
    private String city;
    private String country;

    /**
     * Constructs an address object with the given parameters.
     *
     * @param street name of the street
     * @param houseNumber house number
     * @param postCode postal code
     * @param city name of the city
     * @param country name of the country
     */
    public Address(String street, int houseNumber, int postCode, String city, String country) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.postCode = postCode;
        this.city = city;
        this.country = country;
    }

    /**
     * To get the name of the street. Because only the name may be needed.
     * @return name of the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * To get the house number. Because only the house number may be needed.
     * @return house number
     */
    public int getHouseNumber() {
        return houseNumber;
    }

    /**
     * To get the postal code. Because only the postal code may be needed.
     * @return postal code
     */
    public int getPostCode() {
        return postCode;
    }

    /**
     * To get the name of the city. Because only the city may be needed.
     * @return the name of the city
     */
    public String getCity() {
        return city;
    }

    /**
     * To get the name of the country. Because only the country may be needed.
     * @return the name of the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * To change the house number. Because only the house number may be needed to change (because of typo or
     * some other reason).
     * @param houseNumber the new house number
     */
    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    /**
     * To change the name of the street. Because only the street name may be needed to change (because of typo or
     * some other reason).
     * @param street the name of the new street
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * To change the postal code. Because only the postal code may be needed to change (because of typo or
     * some other reason).
     * @param postCode the new postal code
     */
    public void setPostCode(int postCode) {
        this.postCode = postCode;
    }

    /**
     * To change the city. Because only the city may be needed to change (because of typo or or some other reason).
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * To change the country. Because only the country may be needed to change (because of typo or some other reason).
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * To change the address completely with the given parameters.
     * @param street the name of the new street
     * @param houseNumber the new house number
     * @param postCode the new post code
     * @param city the name of the new city
     * @param country the name of the new country
     */
    public void changeAddress(String street, int houseNumber, int postCode, String city, String country) {
        setStreet(street);
        setHouseNumber(houseNumber);
        setPostCode(postCode);
        this.city = city;
        this.country = country;
    }

    /**
     * To get a string representation of this address. Contains street, house number, postal code, city and country.
     * All those information are generally needed.
     * @return the string representation of the address
     */
    public String toString() {
        return street + " " + houseNumber + ", " + postCode + " " + city + ", " + country;
    }
}
