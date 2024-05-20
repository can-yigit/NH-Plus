package de.hitec.nhplus.utils;

import de.hitec.nhplus.datastorage.*;
import de.hitec.nhplus.model.Caregiver;
import de.hitec.nhplus.model.Patient;
import de.hitec.nhplus.model.Treatment;
import de.hitec.nhplus.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import static de.hitec.nhplus.utils.DateConverter.*;

/**
 * The SetUpDB class provides static methods to set up and wipe the database.
 * It uses the ConnectionBuilder class to establish a connection to the database.
 * Executing this class will wipe the database, create fresh tables, and populate them with test data.
 */
public class SetUpDB {
    private static final Logger LOGGER = Logger.getLogger(SetUpDB.class.getName());

    /**
     * Sets up the database by wiping existing tables, creating new tables, and populating them with test data.
     */
    public static void setUpDb() {
        try (Connection connection = ConnectionBuilder.getConnection()) {
            wipeDb(connection);

            setUpTableCaregiver(connection);
            setUpCaregivers();

            setUpTablePatient(connection);
            setUpPatients();

            setUpTableTreatment(connection);
            setUpTreatments();

            setUpTableUser(connection);
            setUpUsers();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "Error setting up the database", exception);
        }
    }

    /**
     * Wipes the database by dropping existing tables.
     *
     * @param connection the database connection
     */
    public static void wipeDb(Connection connection) {
        String[] tables = {"patient", "treatment", "caregiver", "users"};
        try (Statement statement = connection.createStatement()) {
            for (String table : tables) {
                statement.execute("DROP TABLE IF EXISTS " + table);
            }
        } catch (SQLException exception) {
            LOGGER.log(Level.WARNING, "Error wiping the database", exception);
        }
    }

    /**
     * Creates the 'users' table in the database.
     *
     * @param connection the database connection
     */
    private static void setUpTableUser(Connection connection) {
        final String SQL = "CREATE TABLE IF NOT EXISTS users (" +
                "   uid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "   cid INTEGER, " +
                "   firstname TEXT NOT NULL, " +
                "   surname TEXT NOT NULL, " +
                "   phonenumber TEXT NOT NULL, " +
                "   permissions TEXT NOT NULL, " +
                "   hashedpassword TEXT NOT NULL, " +
                "   FOREIGN KEY (cid) REFERENCES caregiver (cid) ON DELETE CASCADE" +
                ");";
        executeStatement(connection, SQL, "users");
    }

    /**
     * Creates the 'patient' table in the database.
     *
     * @param connection the database connection
     */
    private static void setUpTablePatient(Connection connection) {
        final String SQL = "CREATE TABLE IF NOT EXISTS patient (" +
                "   pid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "   firstname TEXT NOT NULL, " +
                "   surname TEXT NOT NULL, " +
                "   dateOfBirth TEXT NOT NULL, " +
                "   carelevel TEXT NOT NULL, " +
                "   roomnumber TEXT NOT NULL, " +
                "   status BOOLEAN NOT NULL DEFAULT 1, " +
                "   currentDate TEXT NOT NULL" +
                ");";
        executeStatement(connection, SQL, "patient");
    }

    /**
     * Creates the 'caregiver' table in the database.
     *
     * @param connection the database connection
     */
    private static void setUpTableCaregiver(Connection connection) {
        final String SQL = "CREATE TABLE IF NOT EXISTS caregiver (" +
                "   cid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "   firstname TEXT NOT NULL, " +
                "   surname TEXT NOT NULL, " +
                "   phonenumber TEXT NOT NULL" +
                ");";
        executeStatement(connection, SQL, "caregiver");
    }

    /**
     * Creates the 'treatment' table in the database.
     *
     * @param connection the database connection
     */
    private static void setUpTableTreatment(Connection connection) {
        final String SQL = "CREATE TABLE IF NOT EXISTS treatment (" +
                "   tid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "   pid INTEGER NOT NULL, " +
                "   cid INTEGER, " +
                "   treatment_date TEXT NOT NULL, " +
                "   begin TEXT NOT NULL, " +
                "   end TEXT NOT NULL, " +
                "   description TEXT NOT NULL, " +
                "   remark TEXT NOT NULL, " +
                "   FOREIGN KEY (pid) REFERENCES patient (pid) ON DELETE CASCADE, " +
                "   FOREIGN KEY (cid) REFERENCES caregiver (cid) ON DELETE CASCADE" +
                ");";
        executeStatement(connection, SQL, "treatment");
    }

    /**
     * Executes a SQL statement to create a table.
     *
     * @param connection the database connection
     * @param sql the SQL statement to execute
     * @param tableName the name of the table being created
     */
    private static void executeStatement(Connection connection, String sql, String tableName) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
            LOGGER.log(Level.INFO, "Table '" + tableName + "' created successfully.");
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "Error creating table '" + tableName + "'", exception);
        }
    }

    /**
     * Inserts test users into the 'users' table.
     */
    private static void setUpUsers() {
        try {
            UserDao dao = DaoFactory.getDaoFactory().createUserDAO();
            dao.create(new User("Schwerk", "Berg", "0000187", "Administrator", PassHash.hashPassword("1234")));
            dao.create(new User("NHPlus", "Auf die #1", "187 / 361", "Caregiver", PassHash.hashPassword("1234"), 1));
            LOGGER.log(Level.INFO, "Test users inserted successfully.");
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "Error inserting test users", exception);
        }
    }

    /**
     * Inserts test patients into the 'patient' table.
     */
    private static void setUpPatients() {
        try {
            PatientDao dao = DaoFactory.getDaoFactory().createPatientDAO();
            dao.create(new Patient("Seppl", "Herberger", convertStringToLocalDate("1945-12-01"), "4", "202", LocalDate.now()));
            dao.create(new Patient("Martina", "Gerdsen", convertStringToLocalDate("1954-08-12"), "5", "010", LocalDate.now()));
            dao.create(new Patient("Gertrud", "Franzen", convertStringToLocalDate("1949-04-16"), "3", "002", LocalDate.now()));
            dao.create(new Patient("Ahmet", "Yilmaz", convertStringToLocalDate("1941-02-22"), "3", "013", LocalDate.now()));
            dao.create(new Patient("Hans", "Neumann", convertStringToLocalDate("1955-12-12"), "2", "001", LocalDate.now()));
            dao.create(new Patient("Elisabeth", "Müller", convertStringToLocalDate("1958-03-07"), "5", "110", LocalDate.now()));
            dao.create(new Patient("Schwerk", "Sören", convertStringToLocalDate("1958-03-07"), "10", "187", LocalDate.now()));
            LOGGER.log(Level.INFO, "Test patients inserted successfully.");
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "Error inserting test patients", exception);
        }
    }

    /**
     * Inserts test caregivers into the 'caregiver' table.
     */
    private static void setUpCaregivers() {
        try {
            CaregiverDao dao = DaoFactory.getDaoFactory().createCaregiverDAO();
            dao.create(new Caregiver("Björn", "Gütter", "019421201"));
            LOGGER.log(Level.INFO, "Test caregivers inserted successfully.");
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "Error inserting test caregivers", exception);
        }
    }

    /**
     * Inserts test treatments into the 'treatment' table.
     */
    private static void setUpTreatments() {
        try {
            TreatmentDao dao = DaoFactory.getDaoFactory().createTreatmentDao();
            dao.create(new Treatment(1, 3, 1, convertStringToLocalDate("2023-06-03"), convertStringToLocalTime("11:00"), convertStringToLocalTime("15:00"), "Gespräch", "Der Patient hat enorme Angstgefühle und glaubt, er sei überfallen worden. Ihm seien alle Wertsachen gestohlen worden.\nPatient beruhigt sich erst, als alle Wertsachen im Zimmer gefunden worden sind."));
            dao.create(new Treatment(1, 2, 1, convertStringToLocalDate("2023-07-03"), convertStringToLocalTime("11:00"), convertStringToLocalTime("15:00"), "Gespräch", "Der Patient hat enorme Angstgefühle und glaubt, er sei überfallen worden. Ihm seien alle Wertsachen gestohlen worden.\nPatient beruhigt sich erst, als alle Wertsachen im Zimmer gefunden worden sind."));
            LOGGER.log(Level.INFO, "Test treatments inserted successfully.");
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "Error inserting test treatments");
        }
    }

    public static void main(String[] args) {
        SetUpDB.setUpDb();
    }
}
