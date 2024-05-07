package de.hitec.nhplus.model;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Caregivers work in a NURSING home and provide care for patients.
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
     * @param surname    The last name of the caregiver.
     * @param phoneNumber The phone number of the caregiver.
     */
    public Caregiver(String firstName, String surname, String phoneNumber) {
        super(firstName, surname);
        this.firstName = new SimpleStringProperty(firstName);
        this.surname = new SimpleStringProperty(surname);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
    }

    /**
     * Constructor to initiate an object of class <code>Patient</code> with the given parameter. Use this constructor
     * to initiate objects, which are already persisted and have a patient id (pid).
     *
     * @param cId caregiver id.
     * @param firstName   The first name of the caregiver.
     * @param surname    The last name of the caregiver.
     * @param phoneNumber The phone number of the caregiver.
     */
    public Caregiver(long cId, String firstName, String surname, String phoneNumber) {
        super(firstName, surname);
        this.cid = new SimpleLongProperty(cId);
        this.firstName = new SimpleStringProperty(firstName);
        this.surname = new SimpleStringProperty(surname);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
    }

    public long getCaregiverId() {
        return cid.get();
    }

    public SimpleLongProperty cidProperty() {
        return cid;
    }
    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return surname.get();
    }

    public SimpleStringProperty lastSurnameProperty() {
        return surname;
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public SimpleStringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public String getFullName() {
        return firstName.get() + " " + surname.get();
    }

    @Override
    public String toString() {
        return "Caregiver" + "\nCID: " + this.getCaregiverId() +
                "\nFirstname: " + this.getFirstName() +
                "\nSurname: " + this.getSurname() +
                "\nPhonenumber: " + this.phoneNumber +
                '\n';
    }
}