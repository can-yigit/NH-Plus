package de.hitec.nhplus.model;

import de.hitec.nhplus.utils.PassHash;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Users represent individuals who have access to the system, including caregivers with specific permissions.
 */
public class User extends Person {
    private SimpleLongProperty uid;
    private final SimpleStringProperty phoneNumber;
    private final SimpleStringProperty permissions;
    private final SimpleStringProperty hashedPassword;
    private SimpleLongProperty cid;  // foreign key reference to caregiver

    /**
     * Constructor to create a new User object without a caregiver id.
     *
     * @param firstName      The first name of the user.
     * @param surname        The last name of the user.
     * @param phoneNumber    The phone number of the user.
     * @param permissions    The permissions of the user.
     * @param hashedPassword The hashed password of the user.
     */
    public User(String firstName, String surname, String phoneNumber, String permissions, String hashedPassword) {
        super(firstName, surname);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.permissions = new SimpleStringProperty(permissions);
        this.hashedPassword = new SimpleStringProperty(hashedPassword);
    }

    /**
     * Constructor to create a new User object with an optional caregiver id.
     *
     * @param firstName      The first name of the user.
     * @param surname        The last name of the user.
     * @param phoneNumber    The phone number of the user.
     * @param permissions    The permissions of the user.
     * @param hashedPassword The hashed password of the user.
     * @param cid            The caregiver id if applicable.
     */
    public User(String firstName, String surname, String phoneNumber, String permissions, String hashedPassword, long cid) {
        super(firstName, surname);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.permissions = new SimpleStringProperty(permissions);
        this.hashedPassword = new SimpleStringProperty(hashedPassword);
        this.cid = new SimpleLongProperty(cid);
    }

    /**
     * Constructor to create a new User object with an optional caregiver id and uid.
     *
     * @param firstName      The first name of the user.
     * @param surname        The last name of the user.
     * @param phoneNumber    The phone number of the user.
     * @param permissions    The permissions of the user.
     * @param hashedPassword The hashed password of the user.
     * @param cid            The caregiver id if applicable.
     * @param uid            The user id.
     */
    public User(String firstName, String surname, String phoneNumber, String permissions, String hashedPassword, long cid, long uid) {
        super(firstName, surname);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.permissions = new SimpleStringProperty(permissions);
        this.hashedPassword = new SimpleStringProperty(hashedPassword);
        this.cid = new SimpleLongProperty(cid);
        this.uid = new SimpleLongProperty(uid);
    }

    /**
     * Gets the user id.
     *
     * @return the user id
     */
    public long getUserId() {
        return uid != null ? uid.get() : 0;
    }

    public SimpleLongProperty uidProperty() {
        return uid;
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public SimpleStringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public String getPermissions() {
        return permissions.get();
    }

    public SimpleStringProperty permissionsProperty() {
        return permissions;
    }

    public String getHashedPassword() {
        return hashedPassword.get();
    }

    public SimpleStringProperty hashedPasswordProperty() {
        return hashedPassword;
    }

    public long getCaregiverId() {
        return cid != null ? cid.get() : 0;
    }

    public SimpleLongProperty cidProperty() {
        return cid;
    }

    public void setUserId(long uid) {
        if (this.uid == null) {
            this.uid = new SimpleLongProperty(uid);
        } else {
            this.uid.set(uid);
        }
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public void setPermissions(String permissions) {
        this.permissions.set(permissions);
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword.set(hashedPassword);
    }

    public void setCaregiverId(long cid) {
        this.cid.set(cid);
    }
    /**
     * Returns the full name of the user.
     *
     * @return the full name of the user
     */
    public String getFullName() {
        return this.getFirstName() + " " + this.getSurname();
    }

    public long getUid() {
        return uid != null ? uid.get() : 0;
    }

    public boolean hasCareGiverID() {
        return cid != null;
    }
    /**
     * Returns a string representation of the User object.
     *
     * @return a string representation of the User
     */

    @Override
    public String toString() {
        return "User" + "\nUID: " + this.getUserId() +
                "\nFirstname: " + this.getFirstName() +
                "\nSurname: " + this.getSurname() +
                "\nPhoneNumber: " + this.getPhoneNumber() +
                "\nPermissions: " + this.getPermissions() +
                "\nHashed Password: " + this.getHashedPassword() +
                "\nCaregiver ID: " + this.getCaregiverId() +
                '\n';
    }
}