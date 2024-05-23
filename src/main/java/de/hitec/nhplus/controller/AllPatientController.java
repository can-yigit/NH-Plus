package de.hitec.nhplus.controller;

import de.hitec.nhplus.datastorage.ConnectionBuilder;
import de.hitec.nhplus.datastorage.DaoFactory;
import de.hitec.nhplus.datastorage.PatientDao;
import de.hitec.nhplus.utils.PatientDataExport;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import de.hitec.nhplus.model.Patient;
import de.hitec.nhplus.utils.DateConverter;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;


/**
 * The <code>AllPatientController</code> contains the entire logic of the patient view. It determines which data is displayed and how to react to events.
 */
public class AllPatientController {

    @FXML
    private TableView<Patient> tableView;

    @FXML
    private TableColumn<Patient, Integer> columnId;

    @FXML
    private TableColumn<Patient, String> columnFirstName;

    @FXML
    private TableColumn<Patient, String> columnSurname;

    @FXML
    private TableColumn<Patient, String> columnDateOfBirth;

    @FXML
    private TableColumn<Patient, String> columnCareLevel;

    @FXML
    private TableColumn<Patient, String> columnRoomNumber;

    @FXML
    private Button buttonDelete;

    @FXML
    private Button buttonAdd;

    @FXML
    private TextField textFieldSurname;

    @FXML
    private TextField textFieldFirstName;

    @FXML
    private TextField textFieldDateOfBirth;

    @FXML
    private TextField textFieldCareLevel;

    @FXML
    private TextField textFieldRoomNumber;

    @FXML
    private Button buttonBlock;
    private final ObservableList<Patient> patients = FXCollections.observableArrayList();
    private PatientDao dao;
    Connection connection = ConnectionBuilder.getConnection();

    /**
     * When <code>initialize()</code> gets called, all fields are already initialized. For example from the FXMLLoader
     * after loading an FXML-File. At this point of the lifecycle of the Controller, the fields can be accessed and
     * configured.
     */
    public void initialize() {
        this.readAllAndShowInTableView();

        this.columnId.setCellValueFactory(new PropertyValueFactory<>("pid"));

        // CellValueFactory to show property values in TableView
        this.columnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        // CellFactory to write property values from with in the TableView
        this.columnFirstName.setCellFactory(TextFieldTableCell.forTableColumn());

        this.columnSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        this.columnSurname.setCellFactory(TextFieldTableCell.forTableColumn());

        this.columnDateOfBirth.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        this.columnDateOfBirth.setCellFactory(TextFieldTableCell.forTableColumn());

        this.columnCareLevel.setCellValueFactory(new PropertyValueFactory<>("careLevel"));
        this.columnCareLevel.setCellFactory(TextFieldTableCell.forTableColumn());

        this.columnRoomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        this.columnRoomNumber.setCellFactory(TextFieldTableCell.forTableColumn());

        //Anzeigen der Daten
        this.tableView.setItems(this.patients);

        this.buttonDelete.setDisable(true);
        this.buttonBlock.setDisable(true);
        this.tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Patient>() {
            @Override
            public void changed(ObservableValue<? extends Patient> observableValue, Patient oldPatient, Patient newPatient) {;
                AllPatientController.this.buttonDelete.setDisable(newPatient == null);
                AllPatientController.this.buttonBlock.setDisable(newPatient == null);
            }
        });

        this.buttonAdd.setDisable(true);

        ChangeListener<String> inputNewPatientListener = (observableValue, oldText, newText) ->
                AllPatientController.this.buttonAdd.setDisable(!AllPatientController.this.areInputDataValid());
        this.textFieldSurname.textProperty().addListener(inputNewPatientListener);
        this.textFieldFirstName.textProperty().addListener(inputNewPatientListener);
        this.textFieldDateOfBirth.textProperty().addListener(inputNewPatientListener);
        this.textFieldCareLevel.textProperty().addListener(inputNewPatientListener);
        this.textFieldRoomNumber.textProperty().addListener(inputNewPatientListener);
    }

    /**
     * When a cell of the column with first names was changed, this method will be called, to persist the change.
     *
     * @param event Event including the changed object and the change.
     */
    @FXML
    public void handleOnEditFirstname(TableColumn.CellEditEvent<Patient, String> event) {
        event.getRowValue().setFirstName(event.getNewValue());
        this.doUpdate(event);
    }

    /**
     * When a cell of the column with surnames was changed, this method will be called, to persist the change.
     *
     * @param event Event including the changed object and the change.
     */
    @FXML
    public void handleOnEditSurname(TableColumn.CellEditEvent<Patient, String> event) {
        event.getRowValue().setSurname(event.getNewValue());
        this.doUpdate(event);
    }

    /**
     * When a cell of the column with dates of birth was changed, this method will be called, to persist the change.
     *
     * @param event Event including the changed object and the change.
     */
    @FXML
    public void handleOnEditDateOfBirth(TableColumn.CellEditEvent<Patient, String> event) {
        event.getRowValue().setDateOfBirth(event.getNewValue());
        this.doUpdate(event);
    }

    /**
     * When a cell of the column with care levels was changed, this method will be called, to persist the change.
     *
     * @param event Event including the changed object and the change.
     */
    @FXML
    public void handleOnEditCareLevel(TableColumn.CellEditEvent<Patient, String> event) {
        event.getRowValue().setCareLevel(event.getNewValue());
        this.doUpdate(event);
    }

    /**
     * When a cell of the column with room numbers was changed, this method will be called, to persist the change.
     *
     * @param event Event including the changed object and the change.
     */
    @FXML
    public void handleOnEditRoomNumber(TableColumn.CellEditEvent<Patient, String> event){
        event.getRowValue().setRoomNumber(event.getNewValue());
        this.doUpdate(event);
    }
    /**
     * Updates a patient by calling the method <code>update()</code> of {@link PatientDao}.
     *
     * @param event Event including the changed object and the change.
     */
    private void doUpdate(TableColumn.CellEditEvent<Patient, String> event) {
        try {
            this.dao.update(event.getRowValue());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Reloads all patients to the table by clearing the list of all patients and filling it again by all persisted
     * patients, delivered by {@link PatientDao}.
     */
    private void readAllAndShowInTableView() {
        this.patients.clear();
        this.dao = DaoFactory.getDaoFactory().createPatientDAO();
        try {
            this.patients.addAll(this.dao.readAll());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * This method handles events fired by the button to delete patients. It calls {@link PatientDao} to delete the
     * patient from the database and removes the object from the list, which is the data source of the
     * <code>TableView</code>.
     */
    @FXML
    public void handleDelete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Bestätigung");
        alert.setHeaderText("Möchten Sie den Eintrag löschen?");
        alert.setContentText("Klicken Sie auf OK, um fortzufahren.");

        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            Patient selectedItem = this.tableView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                try {
                    DaoFactory.getDaoFactory().createPatientDAO().deleteById(selectedItem.getPid());
                    this.tableView.getItems().remove(selectedItem);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }


    @FXML
    public void exportToPDF() throws SQLException, IOException {
        Patient selectedItem = this.tableView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Keine Auswahl");
            alert.setHeaderText(null);
            alert.setContentText("Bitte wählen Sie einen Patienten aus.");
            alert.showAndWait();
            return;
        }

        long pid = selectedItem.getPid();
        PatientDataExport patientDataExport = new PatientDataExport(connection);

        ArrayList<String[]> patientData = patientDataExport.getPatientDataById(pid);
        ArrayList<String[]> treatmentData = patientDataExport.getTreatmentDataById(pid);

        if (!patientData.isEmpty() && !treatmentData.isEmpty()) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Speicherort für PDF auswählen");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Dateien", "*.pdf"));
            fileChooser.setInitialFileName("Patientenbehandlung.pdf");

            Stage stage = (Stage) this.tableView.getScene().getWindow();
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                String filePath = file.getAbsolutePath();
                patientDataExport.pdfExporter(patientData, treatmentData, filePath);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Patientenbehandlung wurde heruntergeladen:\n" + filePath);
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Keine Behandlung gefunden");
            alert.setHeaderText(null);
            alert.setContentText("Es gibt keine Behandlungsdaten für diesen Patienten.");
            alert.showAndWait();
        }
    }


    public void handleBlock() throws SQLException{
        Patient selectedItem = this.tableView.getSelectionModel().getSelectedItem();
        DaoFactory.getDaoFactory().createPatientDAO().geStatus(selectedItem.getPid());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Bestätigung");
        alert.setHeaderText("Möchten Sie den Eintrag sperren?");
        alert.setContentText("Klicken Sie auf OK, um fortzufahren.");

        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            this.tableView.getItems().remove(selectedItem);
        }
    }

    /**
     * This method handles the events fired by the button to add a patient. It collects the data from the
     * <code>TextField</code>s, creates an object of class <code>Patient</code> of it and passes the object to
     * {@link PatientDao} to persist the data.
     */
    @FXML
    public void handleAdd() {
        String surname = this.textFieldSurname.getText();
        String firstName = this.textFieldFirstName.getText();
        String birthday = this.textFieldDateOfBirth.getText();
        LocalDate date = DateConverter.convertStringToLocalDate(birthday);
        String careLevel = this.textFieldCareLevel.getText();
        String roomNumber = this.textFieldRoomNumber.getText();
        LocalDate currentDate = LocalDate.now();
        try {
            this.dao.create(new Patient(firstName, surname, date, careLevel, roomNumber, currentDate));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        readAllAndShowInTableView();
        clearTextfields();
    }

    /**
     * Clears all contents from all <code>TextField</code>s.
     */
    private void clearTextfields() {
        this.textFieldFirstName.clear();
        this.textFieldSurname.clear();
        this.textFieldDateOfBirth.clear();
        this.textFieldCareLevel.clear();
        this.textFieldRoomNumber.clear();
    }

    private boolean areInputDataValid() {
        if (!this.textFieldDateOfBirth.getText().isBlank()) {
            try {
                DateConverter.convertStringToLocalDate(this.textFieldDateOfBirth.getText());
            } catch (Exception exception) {
                return false;
            }
        }

        return !this.textFieldFirstName.getText().isBlank() && !this.textFieldSurname.getText().isBlank() &&
                !this.textFieldDateOfBirth.getText().isBlank() && !this.textFieldCareLevel.getText().isBlank();
    }

    public void setPatientView(String darkColumn, boolean isDarkTheme) {
        if (isDarkTheme){
            columnFirstName.getStyleClass().remove(darkColumn);
            columnSurname.getStyleClass().remove(darkColumn);
            columnRoomNumber.getStyleClass().remove(darkColumn);
            columnCareLevel.getStyleClass().remove(darkColumn);
            columnDateOfBirth.getStyleClass().remove(darkColumn);
        }else{
            columnFirstName.getStyleClass().add(darkColumn);
            columnSurname.getStyleClass().add(darkColumn);
            columnRoomNumber.getStyleClass().add(darkColumn);
            columnCareLevel.getStyleClass().add(darkColumn);
            columnDateOfBirth.getStyleClass().add(darkColumn);
        }
    }
}
