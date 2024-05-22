package de.hitec.nhplus.controller;

import de.hitec.nhplus.datastorage.AppointmentDao;
import de.hitec.nhplus.datastorage.DaoFactory;
import de.hitec.nhplus.model.Appointment;
import de.hitec.nhplus.sessions.Session;
import de.hitec.nhplus.model.User;
import de.hitec.nhplus.utils.DateConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.time.LocalDate;

public class AllAppointmentController {

    // The current user session
    private User session = Session.getInstance().getUserSession();

    @FXML
    private TableView<Appointment> tableView;

    @FXML
    private TableColumn<Appointment, String> columnBegin;

    @FXML
    private TableColumn<Appointment, String> columnEnd;

    @FXML
    private TableColumn<Appointment, String> columnPatient;

    @FXML
    private TableColumn<Appointment, String> columnRoom;

    @FXML
    private TableColumn<Appointment, String> columnDescription;

    @FXML
    private Label headerDate;

    // Observable list to hold appointments for the TableView
    private final ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    /**
     * Initialize the controller and set up the TableView columns and data.
     */
    public void initialize() {
        // Call method to populate TableView with data
        readAllAndShowInTableView();

        // Set up the TableView columns with the corresponding properties of Appointment
        this.columnBegin.setCellValueFactory(new PropertyValueFactory<>("begin"));
        this.columnEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        this.columnPatient.setCellValueFactory(new PropertyValueFactory<>("patient"));
        this.columnRoom.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        this.columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Assign the observable list to the TableView
        this.tableView.setItems(this.appointments);

        this.headerDate.setText(DateConverter.convertLocalDateToString(LocalDate.now()));

    }

    /**
     * Read all appointments from the database and display them in the TableView.
     */
    public void readAllAndShowInTableView() {
        // Clear the current list of appointments
        this.appointments.clear();

        // Create an AppointmentDao instance using the DaoFactory
        AppointmentDao adao = DaoFactory.getDaoFactory().createAppointmentDAO();

        try {
            // Retrieve appointments for the current user's caregiver ID and add them to the observable list
            this.appointments.addAll(adao.readTreatmentsByCid(session.getCaregiverId()));
        } catch (SQLException exception) {
            // Print stack trace for debugging purposes in case of SQL exception
            exception.printStackTrace();
        }
    }
}
