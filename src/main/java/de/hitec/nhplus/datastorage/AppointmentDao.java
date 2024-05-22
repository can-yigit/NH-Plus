package de.hitec.nhplus.datastorage;

import de.hitec.nhplus.model.Appointment;
import de.hitec.nhplus.utils.DateConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements the Interface <code>DaoImp</code>. Overrides methods to generate specific <code>PreparedStatements</code>,
 * to execute the specific SQL Statements.
 */
public class AppointmentDao extends DaoImp<Appointment> {

    /**
     * The constructor initiates an object of <code>TreatmentDao</code> and passes the connection to its super class.
     *
     * @param connection Object of <code>Connection</code> to execute the SQL-statements.
     */
    public AppointmentDao(Connection connection) {
        super(connection);
    }
    /**
     * Generates a <code>PreparedStatement</code> to query a treatment by a given treatment id (tid).
     *
     * @param uid Treatment id to query.
     * @return <code>PreparedStatement</code> to query the treatment.
     */
    @Override
    protected PreparedStatement getReadByIDStatement(long uid) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "SELECT treament.treatment_date FROM treatment INNER JOIN patient ON treament.pid=patient.pids WHERE uid = ?";

            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setLong(1, uid);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Maps a <code>ResultSet</code> of one treatment to an object of <code>Treatment</code>.
     *
     * @param result ResultSet with a single row. Columns will be mapped to an object of class <code>Treatment</code>.
     * @return Object of class <code>Treatment</code> with the data from the resultSet.
     */
    @Override
    protected Appointment getInstanceFromResultSet(ResultSet result) throws SQLException {
        LocalTime begin = DateConverter.convertStringToLocalTime(result.getString(1));
        LocalTime end = DateConverter.convertStringToLocalTime(result.getString(2));
        return new Appointment(begin, end, result.getString(3), result.getString(4), result.getString(5));
    }

    /**
     * Generates a <code>PreparedStatement</code> to query all treatments.
     *
     * @return <code>PreparedStatement</code> to query all treatments.
     */
    @Override
    protected PreparedStatement getReadAllStatement() {
        PreparedStatement statement = null;
        try {
            final String SQL = "SELECT * FROM treatment";
            statement = this.connection.prepareStatement(SQL);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return statement;
    }

    /**
     * Maps a <code>ResultSet</code> of all treatments to an <code>ArrayList</code> with objects of class
     * <code>Treatment</code>.
     *
     * @param result ResultSet with all rows. The columns will be mapped to objects of class <code>Treatment</code>.
     * @return <code>ArrayList</code> with objects of class <code>Treatment</code> of all rows in the
     * <code>ResultSet</code>.
     */
    @Override
    protected ArrayList<Appointment> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<Appointment> list = new ArrayList<Appointment>();
        while (result.next()) {
            LocalTime begin = DateConverter.convertStringToLocalTime(result.getString(1));
            LocalTime end = DateConverter.convertStringToLocalTime(result.getString(2));
            Appointment Appointment = new Appointment(begin, end, result.getString(3), result.getString(4), result.getString(5));
            list.add(Appointment);
        }
        return list;
    }

    @Override
    protected PreparedStatement getCreateStatement(Appointment appointment) {
        return null;
    }

    /**
     * Generates a <code>PreparedStatement</code> to query all treatments of a patient with a given patient id (pid).
     *
     * @param cid Patient id to query all treatments referencing this id.
     * @return <code>PreparedStatement</code> to query all treatments of the given patient id (pid).
     */
    private PreparedStatement getReadAllTreatmentsOfOnePatientByPid(long cid) {
        PreparedStatement preparedStatement = null;
        try {
            String formattedDate = DateConverter.convertLocalDateToString(LocalDate.now());

            final String SQL = "SELECT treatment.begin, treatment.end, patient.roomnumber, CONCAT(patient.firstname, ' ', patient.surname) AS patient, treatment.description  FROM treatment INNER JOIN patient ON treatment.pid = patient.pid WHERE treatment.cid = ? AND treatment.treatment_date = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setLong(1, cid);
            preparedStatement.setString(2, formattedDate);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Queries all treatments of a given patient id (pid) and maps the results to an <code>ArrayList</code> with
     * objects of class <code>Treatment</code>.
     *
     * @param cid Patient id to query all treatments referencing this id.
     * @return <code>ArrayList</code> with objects of class <code>Treatment</code> of all rows in the
     * <code>ResultSet</code>.
     */
    public List<Appointment> readTreatmentsByCid(long cid) throws SQLException {
        ResultSet result = getReadAllTreatmentsOfOnePatientByPid(cid).executeQuery();
        return getListFromResultSet(result);
    }

    /**
     * Generates a <code>PreparedStatement</code> to update the given treatment, identified
     * by the id of the treatment (tid).
     *
     * @param appointment Treatment object to update.
     * @return <code>PreparedStatement</code> to update the given treatment.
     */
    @Override
    protected PreparedStatement getUpdateStatement(Appointment appointment) {
        return null;
    }

    /**
     * Generates a <code>PreparedStatement</code> to delete a treatment with the given id.
     *
     * @param tid Id of the Treatment to delete.
     * @return <code>PreparedStatement</code> to delete treatment with the given id.
     */
    @Override
    protected PreparedStatement getDeleteStatement(long tid) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL =
                    "DELETE FROM treatment WHERE tid = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setLong(1, tid);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }

    @Override
    protected PreparedStatement getStatus(long key) {
        return null;
    }
}