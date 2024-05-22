package de.hitec.nhplus.controller;

import de.hitec.nhplus.datastorage.CaregiverDao;
import de.hitec.nhplus.datastorage.DaoFactory;
import de.hitec.nhplus.datastorage.UserDao;
import de.hitec.nhplus.model.Caregiver;
import de.hitec.nhplus.model.User;
import de.hitec.nhplus.sessions.Session;
import de.hitec.nhplus.utils.PassHash;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.sql.SQLException;

/**
 * The <code>AllCareGiverController</code> contains the entire logic of the caregiver view. It determines which data is displayed and how to react to events.
 */
public class AllCareGiverController {

    // FXML UI components
    @FXML
    private TableView<Caregiver> tableView;
    @FXML
    private TableColumn<Caregiver, Integer> colId;
    @FXML
    private TableColumn<Caregiver, String> colFirstName;
    @FXML
    private TableColumn<Caregiver, String> colSurname;
    @FXML
    private TableColumn<Caregiver, String> colTelephone;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button buttonAdd;
    @FXML
    private TextField textFieldFirstName;
    @FXML
    private TextField textFieldLastName;
    @FXML
    private TextField textFieldPhoneNumber;

    // Observable list to hold caregivers for the TableView
    private final ObservableList<Caregiver> caregivers = FXCollections.observableArrayList();
    private CaregiverDao dao;
    private UserDao udao;

    public AllCareGiverController() {
    }

    /**
     * Initializes the controller.
     */
    public void initialize() {
        readAllAndShowInTableView();

        // Set up the TableView columns with the corresponding properties of Caregiver
        this.colId.setCellValueFactory(new PropertyValueFactory<>("cid"));
        this.colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.colFirstName.setCellFactory(TextFieldTableCell.forTableColumn());
        this.colSurname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        this.colSurname.setCellFactory(TextFieldTableCell.forTableColumn());
        this.colTelephone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        this.colTelephone.setCellFactory(TextFieldTableCell.forTableColumn());

        // Assign the observable list to the TableView
        this.tableView.setItems(this.caregivers);

        // Disable the delete button if no caregiver is selected
        this.buttonDelete.setDisable(true);
        this.tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Caregiver>() {
            @Override
            public void changed(ObservableValue<? extends Caregiver> observableValue, Caregiver oldCaregiver, Caregiver newCaregiver) {
                buttonDelete.setDisable(newCaregiver == null);
            }
        });

        // Disable the add button if the input fields are not valid
        this.buttonAdd.setDisable(true);
        ChangeListener<String> inputNewCaregiverListener = (observableValue, oldText, newText) ->
                buttonAdd.setDisable(!areInputDataValid());
        this.textFieldFirstName.textProperty().addListener(inputNewCaregiverListener);
        this.textFieldLastName.textProperty().addListener(inputNewCaregiverListener);
        this.textFieldPhoneNumber.textProperty().addListener(inputNewCaregiverListener);
    }

    @FXML
    public void handleOnEditFirstName(TableColumn.CellEditEvent<Caregiver, String> event) {
        event.getRowValue().setFirstName(event.getNewValue());
        doUpdate(event.getRowValue());
    }

    @FXML
    public void handleOnEditLastName(TableColumn.CellEditEvent<Caregiver, String> event) {
        event.getRowValue().setSurname(event.getNewValue());
        doUpdate(event.getRowValue());
    }

    @FXML
    public void handleOnEditPhoneNumber(TableColumn.CellEditEvent<Caregiver, String> event) {
        event.getRowValue().setPhoneNumber(event.getNewValue());
        doUpdate(event.getRowValue());
    }

    private void doUpdate(Caregiver caregiver) {
        try {
            dao.update(caregiver);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void readAllAndShowInTableView() {
        caregivers.clear();
        dao = DaoFactory.getDaoFactory().createCaregiverDAO();
        try {
            caregivers.addAll(dao.readAll());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    public void handleDelete() {
        Caregiver selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            if(selectedItem.getCaregiverId() == Session.getInstance().getUserSession().getCaregiverId() || !Session.getInstance().getUserSession().getPermissions().equals("Administrator")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Account kann nicht gelöscht werden.");
                    alert.setContentText("Du kannst deinen Account nicht löschen oder einen anderen ohne Administrator Berechtigung, wenn du in diesen einloggt bist.");
                    alert.showAndWait();
            } else {
                try {
                    dao.deleteById(selectedItem.getCaregiverId());
                    tableView.getItems().remove(selectedItem);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }

        }
    }

    @FXML
    public void handleAdd() {
        String firstName = textFieldFirstName.getText();
        String lastName = textFieldLastName.getText();
        String phoneNumber = textFieldPhoneNumber.getText();
        udao = DaoFactory.getDaoFactory().createUserDAO();
        try {
            Caregiver newCaregiver = new Caregiver(firstName, lastName, phoneNumber);
            dao.create(newCaregiver);
            udao.create(new User(firstName, lastName, phoneNumber, "Caregiver", PassHash.hashPassword("DefaultPW1234!")));
            caregivers.add(newCaregiver);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        clearTextfields();
        readAllAndShowInTableView();
    }

    private void clearTextfields() {
        textFieldFirstName.clear();
        textFieldLastName.clear();
        textFieldPhoneNumber.clear();
    }

    private boolean areInputDataValid() {
        return !textFieldFirstName.getText().isBlank() && !textFieldLastName.getText().isBlank() &&
                !textFieldPhoneNumber.getText().isBlank();
    }
}