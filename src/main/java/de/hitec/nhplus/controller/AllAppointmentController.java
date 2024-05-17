package de.hitec.nhplus.controller;

import de.hitec.nhplus.datastorage.AppointmentDao;
import de.hitec.nhplus.datastorage.DaoFactory;
import de.hitec.nhplus.datastorage.TreatmentDao;
import de.hitec.nhplus.model.*;
import de.hitec.nhplus.sessions.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class AllAppointmentController {
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
    private Button buttonDelete;


    private final ObservableList<Appointment> Appointment = FXCollections.observableArrayList();

    public void initialize() {
        readAllAndShowInTableView();

        this.columnBegin.setCellValueFactory(new PropertyValueFactory<>("begin"));
        this.columnEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        this.columnPatient.setCellValueFactory(new PropertyValueFactory<>("patient"));
        this.columnRoom.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        this.tableView.setItems(this.Appointment);
    }

    public void readAllAndShowInTableView() {
        this.Appointment.clear();
        AppointmentDao adao = DaoFactory.getDaoFactory().createAppointmentDAO();
        try {
            this.Appointment.addAll(adao.readTreatmentsByCid(session.getCaregiverId()));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

}
