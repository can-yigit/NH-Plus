package de.hitec.nhplus.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class DatabaseChecker {

    private Connection connection;

    public DatabaseChecker(Connection connection) {
        this.connection = connection;
    }

    private void checkAndDeleteOldPatients() {
        try {
            LocalDate currentDate = LocalDate.now();

            final String SQL = "SELECT pid, currentDate FROM patient";
            PreparedStatement statement = this.connection.prepareStatement(SQL);

            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()){
                return;
            }
            while (resultSet.next()) {
                long pID = resultSet.getLong("pid");
                String currentDateString = resultSet.getString("currentDate");

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date getCurrentDateFromDatabase = dateFormat.parse(currentDateString);

                Calendar cal = Calendar.getInstance();
                cal.setTime(getCurrentDateFromDatabase);

                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH) + 1;
                int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

                LocalDate  date = LocalDate.of(year, month, dayOfMonth);

                LocalDate calculatedDate = date.plusYears(30);
                if (calculatedDate.equals(currentDate)) {
                    deletePatientData(pID);
                }
            }

            resultSet.close();
            statement.close();
        } catch (SQLException | ParseException exception) {
            exception.printStackTrace();
        }
    }


    private void deletePatientData(long pid) {
        try {
            final String SQL = "DELETE FROM patient WHERE pid = ?";
            PreparedStatement deleteStatement = this.connection.prepareStatement(SQL);
            deleteStatement.setLong(1, pid);
            deleteStatement.executeUpdate();
            deleteStatement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void repeatEvery45Minutes() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkAndDeleteOldPatients();
            }
        }, 0, 45 * 60 * 1000);
    }
}
