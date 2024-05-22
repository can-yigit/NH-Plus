package de.hitec.nhplus.model;

import de.hitec.nhplus.utils.DateConverter;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Patients live in a nursing home and are treated by nurses.
 */
public class Patient extends Person {
    private SimpleLongProperty pid;
    private final SimpleStringProperty dateOfBirth;
    private final SimpleStringProperty careLevel;
    private final SimpleStringProperty roomNumber;
    private LocalDate currentDate;
    private final List<Treatment> allTreatments = new ArrayList<>();

    /**
     * Constructor to initiate an object of class <code>Patient</code> with the given parameters.
     * Use this constructor to initiate objects which are not persisted yet, because it will not have a patient ID (pid).
     *
     * @param firstName   First name of the patient.
     * @param surname     Last name of the patient.
     * @param dateOfBirth Date of birth of the patient.
     * @param careLevel   Care level of the patient.
     * @param roomNumber  Room number of the patient.
     * @param currentDate The current date.
     */
    public Patient(String firstName, String surname, LocalDate dateOfBirth, String careLevel, String roomNumber, LocalDate currentDate) {
        super(firstName, surname);
        this.dateOfBirth = new SimpleStringProperty(DateConverter.convertLocalDateToString(dateOfBirth));
        this.currentDate = currentDate;
        this.careLevel = new SimpleStringProperty(careLevel);
        this.roomNumber = new SimpleStringProperty(roomNumber);
    }

    /**
     * Constructor to initiate an object of class <code>Patient</code> with the given parameters.
     * Use this constructor to initiate objects which are already persisted and have a patient ID (pid).
     *
     * @param pid         Patient ID.
     * @param firstName   First name of the patient.
     * @param surname     Last name of the patient.
     * @param dateOfBirth Date of birth of the patient.
     * @param careLevel   Care level of the patient.
     * @param roomNumber  Room number of the patient.
     */
    public Patient(long pid, String firstName, String surname, LocalDate dateOfBirth, String careLevel, String roomNumber) {
        super(firstName, surname);
        this.pid = new SimpleLongProperty(pid);
        this.dateOfBirth = new SimpleStringProperty(DateConverter.convertLocalDateToString(dateOfBirth));
        this.careLevel = new SimpleStringProperty(careLevel);
        this.roomNumber = new SimpleStringProperty(roomNumber);
    }

    /**
     * Gets the current date.
     *
     * @return The current date.
     */
    public LocalDate getCurrentDate() {
        return currentDate;
    }

    /**
     * Sets the current date.
     *
     * @param currentDate The current date to set.
     */
    public void setCurrentDate(LocalDate currentDate) {
        this.currentDate = currentDate;
    }

    /**
     * Gets the patient ID.
     *
     * @return The patient ID.
     */
    public long getPid() {
        return pid != null ? pid.get() : -1;
    }

    /**
     * Gets the patient ID property.
     *
     * @return The patient ID property.
     */
    public SimpleLongProperty pidProperty() {
        return pid;
    }

    /**
     * Gets the date of birth.
     *
     * @return The date of birth.
     */
    public String getDateOfBirth() {
        return dateOfBirth.get();
    }

    /**
     * Gets the date of birth property.
     *
     * @return The date of birth property.
     */
    public SimpleStringProperty dateOfBirthProperty() {
        return dateOfBirth;
    }

    /**
     * Sets the date of birth.
     *
     * @param dateOfBirth The date of birth as a string in the format YYYY-MM-DD.
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth.set(dateOfBirth);
    }

    /**
     * Gets the care level.
     *
     * @return The care level.
     */
    public String getCareLevel() {
        return careLevel.get();
    }

    /**
     * Gets the care level property.
     *
     * @return The care level property.
     */
    public SimpleStringProperty careLevelProperty() {
        return careLevel;
    }

    /**
     * Sets the care level.
     *
     * @param careLevel The care level to set.
     */
    public void setCareLevel(String careLevel) {
        this.careLevel.set(careLevel);
    }

    /**
     * Gets the room number.
     *
     * @return The room number.
     */
    public String getRoomNumber() {
        return roomNumber.get();
    }

    /**
     * Gets the room number property.
     *
     * @return The room number property.
     */
    public SimpleStringProperty roomNumberProperty() {
        return roomNumber;
    }

    /**
     * Sets the room number.
     *
     * @param roomNumber The room number to set.
     */
    public void setRoomNumber(String roomNumber) {
        this.roomNumber.set(roomNumber);
    }

    /**
     * Adds a treatment to the list of treatments, if the list does not already contain the treatment.
     *
     * @param treatment Treatment to add.
     * @return False if the treatment was already part of the list, else true.
     */
    public boolean addTreatment(Treatment treatment) {
        if (this.allTreatments.contains(treatment)) {
            return false;
        }
        this.allTreatments.add(treatment);
        return true;
    }

    /**
     * Returns a string representation of the Patient object.
     *
     * @return A string representation of the Patient.
     */
    @Override
    public String toString() {
        return "Patient" +
                "\nPID: " + this.getPid() +
                "\nFirstname: " + this.getFirstName() +
                "\nSurname: " + this.getSurname() +
                "\nBirthday: " + this.getDateOfBirth() +
                "\nCarelevel: " + this.getCareLevel() +
                "\nRoomnumber: " + this.getRoomNumber() +
                "\n";
    }
}
