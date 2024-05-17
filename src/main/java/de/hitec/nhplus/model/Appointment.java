package de.hitec.nhplus.model;

import javafx.beans.property.SimpleStringProperty;
import java.time.LocalTime;

public class Appointment {
    private LocalTime begin;
    private LocalTime end;
    private final SimpleStringProperty patient;
    private final SimpleStringProperty roomNumber;

    public Appointment(LocalTime begin, LocalTime end, String roomNumber, String patient) {
        this.begin = begin;
        this.end = end;
        this.patient = new SimpleStringProperty(patient);
        this.roomNumber = new SimpleStringProperty(roomNumber);
    }

    public LocalTime getBegin() {
        return begin;
    }

    public void setBegin(LocalTime begin) {
        this.begin = begin;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    public String getPatient() {
        return patient.get();
    }

    public SimpleStringProperty patientProperty() {
        return patient;
    }

    public String getRoomNumber() {
        return roomNumber.get();
    }

    public SimpleStringProperty roomNumberProperty() {
        return roomNumber;
    }
}
