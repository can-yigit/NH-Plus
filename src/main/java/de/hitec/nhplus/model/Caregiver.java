package de.hitec.nhplus.model;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Caregivers work in a nursing home and provide care for patients.
 */
public class Caregiver extends Person {
    private SimpleLongProperty cid;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty surname;
    private final SimpleStringProperty phoneNumber;

    /**
     * Constructor to create a new Caregiver object.
     *
     * @param firstName   The first name of the caregiver.
     * @param surname     The last name of the caregiver.
     * @param phoneNumber The phone number of the caregiver.
     */
    public Caregiver(String firstName, String surname, String phoneNumber) {
        super(firstName, surname);
        this.firstName = new SimpleStringProperty(firstName);
        this.surname = new SimpleStringProperty(surname);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
    }

    /**
     * Constructor to create a new Caregiver object with a caregiver ID.
     * Use this constructor for objects that are already persisted and have a caregiver ID (cid).
     *
     * @param cid         The caregiver id.
     * @param firstName   The first name of the caregiver.
     * @param surname     The last name of the caregiver.
     * @param phoneNumber The phone number of the caregiver.
     */
    public Caregiver(long cid, String firstName, String surname, String phoneNumber) {
        super(firstName, surname);
        this.cid = new SimpleLongProperty(cid);
        this.firstName = new SimpleStringProperty(firstName);
        this.surname = new SimpleStringProperty(surname);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
    }

    /**
     * Gets the caregiver id.
     *
     * @return the caregiver id.
     */
    public long getCaregiverId() {
        return cid != null ? cid.get() : 0;
    }
    /**
     * Gets the Caregiver ID property of the person.
     *
     * @return the cid property.
     */
    public SimpleLongProperty cidProperty() {
        return cid;
    }

    /**
     * Gets the first name of the caregiver.
     *
     * @return the first name of the caregiver.
     */
    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    /**
     * Gets the last name of the caregiver.
     *
     * @return the last name of the caregiver.
     */
    public String getLastName() {
        return surname.get();
    }

    public SimpleStringProperty surnameProperty() {
        return surname;
    }

    /**
     * Gets the phone number of the caregiver.
     *
     * @return the phone number of the caregiver.
     */
    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    /**
     * Gets the phoneNumber property of the person.
     *
     * @return the phoneNumber property.
     */
    public SimpleStringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    /**
     * Sets the first name of the caregiver.
     *
     * @param firstName the first name to set.
     */
    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    /**
     * Sets the last name of the caregiver.
     *
     * @param surname the last name to set.
     */
    public void setLastName(String surname) {
        this.surname.set(surname);
    }

    /**
     * Sets the phone number of the caregiver.
     *
     * @param phoneNumber the phone number to set.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    /**
     * Gets the full name of the caregiver.
     *
     * @return the full name of the caregiver.
     */
    public String getFullName() {
        return firstName.get() + " " + surname.get();
    }

    /**
     * Returns a string representation of the Caregiver object.
     *
     * @return a string representation of the Caregiver.
     */
    @Override
    public String toString() {
        return "Caregiver" + "\nCID: " + this.getCaregiverId() +
                "\nFirst Name: " + this.getFirstName() +
                "\nSurname: " + this.getLastName() +
                "\nPhone Number: " + this.getPhoneNumber() +
                '\n';
    }
}
