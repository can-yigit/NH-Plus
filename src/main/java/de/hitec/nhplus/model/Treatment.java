package de.hitec.nhplus.model;

import de.hitec.nhplus.utils.DateConverter;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a treatment session for a patient, including details such as date, time, description, and remarks.
 */
public class Treatment {
    private long tid;
    private final long pid;
    private final long cid;
    private LocalDate date;
    private LocalTime begin;
    private LocalTime end;
    private String description;
    private String remarks;

    /**
     * Constructor to create a new Treatment object that is not yet persisted (i.e., without a treatment ID).
     *
     * @param pid         The ID of the treated patient.
     * @param cid         The ID of the caregiver.
     * @param date        The date of the treatment.
     * @param begin       The start time of the treatment in format "hh:mm".
     * @param end         The end time of the treatment in format "hh:mm".
     * @param description The description of the treatment.
     * @param remarks     Additional remarks about the treatment.
     */
    public Treatment(long pid, long cid, LocalDate date, LocalTime begin,
                     LocalTime end, String description, String remarks) {
        this.pid = pid;
        this.cid = cid;
        this.date = date;
        this.begin = begin;
        this.end = end;
        this.description = description;
        this.remarks = remarks;
    }

    /**
     * Constructor to create a new Treatment object that is already persisted (i.e., with a treatment ID).
     *
     * @param tid         The ID of the treatment.
     * @param pid         The ID of the treated patient.
     * @param cid         The ID of the caregiver.
     * @param date        The date of the treatment.
     * @param begin       The start time of the treatment in format "hh:mm".
     * @param end         The end time of the treatment in format "hh:mm".
     * @param description The description of the treatment.
     * @param remarks     Additional remarks about the treatment.
     */
    public Treatment(long tid, long pid, long cid, LocalDate date, LocalTime begin,
                     LocalTime end, String description, String remarks) {
        this.tid = tid;
        this.pid = pid;
        this.cid = cid;
        this.date = date;
        this.begin = begin;
        this.end = end;
        this.description = description;
        this.remarks = remarks;
    }

    /**
     * Gets the treatment ID.
     *
     * @return the treatment ID.
     */
    public long getTid() {
        return tid;
    }

    /**
     * Gets the patient ID.
     *
     * @return the patient ID.
     */
    public long getPid() {
        return pid;
    }

    /**
     * Gets the caregiver ID.
     *
     * @return the caregiver ID.
     */
    public long getCid() {
        return cid;
    }

    /**
     * Gets the date of the treatment.
     *
     * @return the date of the treatment as a string.
     */
    public String getDate() {
        return date.toString();
    }

    /**
     * Gets the start time of the treatment.
     *
     * @return the start time of the treatment as a string.
     */
    public String getBegin() {
        return begin.toString();
    }

    /**
     * Gets the end time of the treatment.
     *
     * @return the end time of the treatment as a string.
     */
    public String getEnd() {
        return end.toString();
    }

    /**
     * Sets the date of the treatment.
     *
     * @param date the date to set, in string format.
     */
    public void setDate(String date) {
        this.date = DateConverter.convertStringToLocalDate(date);
    }

    /**
     * Sets the start time of the treatment.
     *
     * @param begin the start time to set, in string format.
     */
    public void setBegin(String begin) {
        this.begin = DateConverter.convertStringToLocalTime(begin);
    }

    /**
     * Sets the end time of the treatment.
     *
     * @param end the end time to set, in string format.
     */
    public void setEnd(String end) {
        this.end = DateConverter.convertStringToLocalTime(end);
    }

    /**
     * Gets the description of the treatment.
     *
     * @return the description of the treatment.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the treatment.
     *
     * @param description the description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets additional remarks about the treatment.
     *
     * @return the remarks about the treatment.
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Sets additional remarks about the treatment.
     *
     * @param remarks the remarks to set.
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * Returns a string representation of the Treatment object.
     *
     * @return a string representation of the Treatment.
     */
    @Override
    public String toString() {
        return "\nTreatment" + "\nTID: " + this.tid +
                "\nPID: " + this.pid +
                "\nCID: " + this.cid +
                "\nDate: " + this.date +
                "\nBegin: " + this.begin +
                "\nEnd: " + this.end +
                "\nDescription: " + this.description +
                "\nRemarks: " + this.remarks + "\n";
    }
}
