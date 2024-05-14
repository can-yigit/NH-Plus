package de.hitec.nhplus.model;

import de.hitec.nhplus.model.Person;
import de.hitec.nhplus.utils.PassHash;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Represents a user in the system.
 */
public class User extends Person {
    private SimpleLongProperty uid;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty surname;
    private final SimpleStringProperty phoneNumber;
    private final SimpleStringProperty permissions;
    private final SimpleStringProperty hashedpassword;

    /**
     * Constructor to create a new User object.
     *
     * @param firstName       The first name of the user.
     * @param surname         The last name of the user.
     * @param phoneNumber     The phone number of the user.
     * @param permissions     The permissions of the user.
     * @param hashedpassword  The hashed password of the user.
     */
    public User(String firstName, String surname, String phoneNumber, String permissions, String hashedpassword) {
        super(firstName, surname);
        this.firstName = new SimpleStringProperty(firstName);
        this.surname = new SimpleStringProperty(surname);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.permissions = new SimpleStringProperty(permissions);
        this.hashedpassword = new SimpleStringProperty(PassHash.hashPassword(hashedpassword));
    }

    /**
     * Constructor to create a new User object with a specified user id.
     *
     * @param uid          The unique id of the user.
     * @param firstName       The first name of the user.
     * @param surname         The last name of the user.
     * @param phoneNumber     The phone number of the user.
     * @param permissions     The permissions of the user.
     * @param hashedpassword  The hashed password of the user.
     */
    public User(long uid, String firstName, String surname, String phoneNumber, String permissions, String hashedpassword) {
        super(firstName, surname);
        this.uid = new SimpleLongProperty(uid);
        this.firstName = new SimpleStringProperty(firstName);
        this.surname = new SimpleStringProperty(surname);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.permissions = new SimpleStringProperty(permissions);
        this.hashedpassword = new SimpleStringProperty(hashedpassword);
    }

    public long getUid() {
        return uid.get();
    }

    public SimpleLongProperty uidProperty() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid.set(uid);
    }

    @Override
    public String getFirstName() {
        return firstName.get();
    }

    @Override
    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    @Override
    public String getSurname() {
        return surname.get();
    }

    @Override
    public SimpleStringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public SimpleStringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public String getPermissions() {
        return permissions.get();
    }

    public SimpleStringProperty permissionsProperty() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions.set(permissions);
    }

    // Getters and setters for hashedPassword field
    public String getHashedPassword() {
        return hashedpassword.get();
    }

    public SimpleStringProperty hashedPasswordProperty() {
        return hashedpassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedpassword.set(hashedPassword);
    }

    public String getFullName() {
        return firstName.get() + " " + surname.get();
    }
}