package de.hitec.nhplus.controller;

import de.hitec.nhplus.datastorage.DaoFactory;
import de.hitec.nhplus.datastorage.CaregiverDao;
import de.hitec.nhplus.model.Caregiver;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import de.hitec.nhplus.utils.DateConverter;

import java.sql.SQLException;

/**
 * The <code>AllCareGiverController</code> contains the entire logic of the caregiver view. It determines which data is displayed and how to react to events.
 */
public class AllCareGiverController {

    @FXML
    private TableView<Caregiver> tableView;
    @FXML
    public TableColumn<Caregiver, Integer> colId;

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

    private final ObservableList<Caregiver> caregivers = FXCollections.observableArrayList();
    private CaregiverDao dao;

    public AllCareGiverController() {
    }

    /**
     * Initializes the controller.
     */
    public void initialize() {
        this.readAllAndShowInTableView();

        this.colId.setCellValueFactory(new PropertyValueFactory<>("cid"));

        this.colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.colFirstName.setCellFactory(TextFieldTableCell.forTableColumn());
        this.colSurname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        this.colSurname.setCellFactory(TextFieldTableCell.forTableColumn());
        this.colTelephone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        this.colTelephone.setCellFactory(TextFieldTableCell.forTableColumn());

        this.tableView.setItems(this.caregivers);

        this.buttonDelete.setDisable(true);
        this.tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Caregiver>() {
            @Override
            public void changed(ObservableValue<? extends Caregiver> observableValue, Caregiver oldCaregiver, Caregiver newCaregiver) {
                AllCareGiverController.this.buttonDelete.setDisable(newCaregiver == null);
            }
        });

        this.buttonAdd.setDisable(true);
        ChangeListener<String> inputNewCaregiverListener = (observableValue, oldText, newText) ->
                AllCareGiverController.this.buttonAdd.setDisable(!AllCareGiverController.this.areInputDataValid());
        this.textFieldFirstName.textProperty().addListener(inputNewCaregiverListener);
        this.textFieldLastName.textProperty().addListener(inputNewCaregiverListener);
        this.textFieldPhoneNumber.textProperty().addListener(inputNewCaregiverListener);
    }

    @FXML
    public void handleOnEditFirstName(TableColumn.CellEditEvent<Caregiver, String> event) {
        event.getRowValue().setFirstName(event.getNewValue());
        this.doUpdate(event);
    }

    @FXML
    public void handleOnEditLastName(TableColumn.CellEditEvent<Caregiver, String> event) {
        event.getRowValue().setSurname(event.getNewValue());
        this.doUpdate(event);
    }

    @FXML
    public void handleOnEditPhoneNumber(TableColumn.CellEditEvent<Caregiver, String> event) {
        event.getRowValue().setPhoneNumber(event.getNewValue());
        this.doUpdate(event);
    }

    private void doUpdate(TableColumn.CellEditEvent<Caregiver, String> event) {
        try {
            this.dao.update(event.getRowValue());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void readAllAndShowInTableView() {
        this.caregivers.clear();
        this.dao = DaoFactory.getDaoFactory().createCaregiverDAO();
        try {
            this.caregivers.addAll(this.dao.readAll());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    public void handleDelete() {
        Caregiver selectedItem = this.tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                this.dao.deleteById(selectedItem.getCaregiverId());
                this.tableView.getItems().remove(selectedItem);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    @FXML
    public void handleAdd() {
        String firstName = this.textFieldFirstName.getText();
        String lastName = this.textFieldLastName.getText();
        String phoneNumber = this.textFieldPhoneNumber.getText();
        try {
            this.dao.create(new Caregiver(firstName, lastName, phoneNumber));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        readAllAndShowInTableView();
        clearTextfields();
    }

    private void clearTextfields() {
        this.textFieldFirstName.clear();
        this.textFieldLastName.clear();
        this.textFieldPhoneNumber.clear();
    }

    private boolean areInputDataValid() {
        return !this.textFieldFirstName.getText().isBlank() && !this.textFieldLastName.getText().isBlank() &&
                !this.textFieldPhoneNumber.getText().isBlank();
    }
}