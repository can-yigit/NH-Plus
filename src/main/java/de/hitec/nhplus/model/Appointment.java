package de.hitec.nhplus.model;

import javafx.beans.property.SimpleStringProperty;
import java.time.LocalTime;

/**
 * Appointment represents a scheduled meeting or consultation with a patient.
 */
public class Appointment {
    private LocalTime begin;
    private LocalTime end;
    private final SimpleStringProperty patient;
    private final SimpleStringProperty roomNumber;
    private final SimpleStringProperty description;

    /**
     * Constructor to create a new Appointment object.
     *
     * @param begin       The starting time of the appointment.
     * @param end         The ending time of the appointment.
     * @param roomNumber  The room number where the appointment will take place.
     * @param patient     The name of the patient for the appointment.
     * @param description The description of the appointment.
     */
    public Appointment(LocalTime begin, LocalTime end, String roomNumber, String patient, String description) {
        this.begin = begin;
        this.end = end;
        this.patient = new SimpleStringProperty(patient);
        this.roomNumber = new SimpleStringProperty(roomNumber);
        this.description = new SimpleStringProperty(description);
    }

    /**
     * Gets the beginning time of the appointment.
     *
     * @return the beginning time of the appointment.
     */
    public LocalTime getBegin() {
        return begin;
    }

    /**
     * Sets the beginning time of the appointment.
     *
     * @param begin the beginning time to set.
     */
    public void setBegin(LocalTime begin) {
        this.begin = begin;
    }

    /**
     * Gets the ending time of the appointment.
     *
     * @return the ending time of the appointment.
     */
    public LocalTime getEnd() {
        return end;
    }

    /**
     * Sets the ending time of the appointment.
     *
     * @param end the ending time to set.
     */
    public void setEnd(LocalTime end) {
        this.end = end;
    }

    /**
     * Gets the patient name of the appointment.
     *
     * @return the patient name.
     */
    public String getPatient() {
        return patient.get();
    }

    /**
     * Gets the patient property of the appointment.
     *
     * @return the patient property.
     */
    public SimpleStringProperty patientProperty() {
        return patient;
    }

    /**
     * Gets the room number of the appointment.
     *
     * @return the room number.
     */
    public String getRoomNumber() {
        return roomNumber.get();
    }

    /**
     * Gets the room number property of the appointment.
     *
     * @return the room number property.
     */
    public SimpleStringProperty roomNumberProperty() {
        return roomNumber;
    }

    /**
     * Gets the description of the appointment.
     *
     * @return the description.
     */
    public String getDescription() {
        return description.get();
    }

    /**
     * Gets the description property of the appointment.
     *
     * @return the description property.
     */
    public SimpleStringProperty descriptionProperty() {
        return description;
    }
}