package de.hitec.nhplus.controller;

import de.hitec.nhplus.datastorage.CaregiverDao;
import de.hitec.nhplus.datastorage.DaoFactory;
import de.hitec.nhplus.datastorage.TreatmentDao;
import de.hitec.nhplus.model.Caregiver;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import de.hitec.nhplus.model.Patient;
import de.hitec.nhplus.model.Treatment;
import de.hitec.nhplus.utils.DateConverter;
import javafx.util.StringConverter;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class NewTreatmentController {

    @FXML
    private Label labelFirstName;

    @FXML
    private Label labelSurname;

    @FXML
    private TextField textFieldBegin;

    @FXML
    private TextField textFieldEnd;

    @FXML
    private TextField textFieldDescription;

    @FXML
    private TextArea textAreaRemarks;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button buttonAdd;

    @FXML
    private ComboBox<String> comboBoxCareGiverSelection;
    private AllTreatmentController controller;
    private Patient patient;
    private Caregiver caregiver;
    private Stage stage;

    private final ObservableList<String> caregiversSelection = FXCollections.observableArrayList();
    private ArrayList<Caregiver> caregiverList;

    public void initialize(AllTreatmentController controller, Stage stage, Patient patient) {
        this.controller= controller;
        this.patient = patient;
        this.stage = stage;

        this.buttonAdd.setDisable(true);
        ChangeListener<String> inputNewPatientListener = (observableValue, oldText, newText) ->
                NewTreatmentController.this.buttonAdd.setDisable(NewTreatmentController.this.areInputDataInvalid());
        this.textFieldBegin.textProperty().addListener(inputNewPatientListener);
        this.textFieldEnd.textProperty().addListener(inputNewPatientListener);
        this.textFieldDescription.textProperty().addListener(inputNewPatientListener);
        this.textAreaRemarks.textProperty().addListener(inputNewPatientListener);
        this.datePicker.valueProperty().addListener((observableValue, localDate, t1) -> NewTreatmentController.this.buttonAdd.setDisable(NewTreatmentController.this.areInputDataInvalid()));
        this.datePicker.setConverter(new StringConverter<>() {
            @Override
            public String toString(LocalDate localDate) {
                return (localDate == null) ? "" : DateConverter.convertLocalDateToString(localDate);
            }

            @Override
            public LocalDate fromString(String localDate) {
                return DateConverter.convertStringToLocalDate(localDate);
            }
        });
        this.createComboBoxData();
        comboBoxCareGiverSelection.setItems(caregiversSelection);
        comboBoxCareGiverSelection.getSelectionModel().select(0);
        this.showPatientData();
    }
    private void createComboBoxData() {
        CaregiverDao dao = DaoFactory.getDaoFactory().createCaregiverDAO();
        try {
            caregiverList = (ArrayList<Caregiver>) dao.readAll();
            for (Caregiver caregiver: caregiverList) {
                this.caregiversSelection.add(caregiver.getFullName());
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void showPatientData(){
        this.labelFirstName.setText(patient.getFirstName());
        this.labelSurname.setText(patient.getSurname());
    }

    @FXML
    public void handleAdd(){
        Caregiver selectedCaregiver = getSelectedCaregiver();
        LocalDate date = this.datePicker.getValue();
        LocalTime begin = DateConverter.convertStringToLocalTime(textFieldBegin.getText());
        LocalTime end = DateConverter.convertStringToLocalTime(textFieldEnd.getText());
        String description = textFieldDescription.getText();
        String remarks = textAreaRemarks.getText();
        Treatment treatment = new Treatment(patient.getPid(), selectedCaregiver.getCaregiverId(), date, begin, end, description, remarks);
        createTreatment(treatment);
        controller.readAllAndShowInTableView();
        stage.close();
    }
    private Caregiver getSelectedCaregiver() {
        String selectedCaregiverName = this.comboBoxCareGiverSelection.getSelectionModel().getSelectedItem();
        for (Caregiver caregiver : caregiverList) {
            if ((caregiver.getFullName()).equals(selectedCaregiverName)) {
                return caregiver;
            }
        }
        return null; // Rückgabe von null, wenn kein entsprechender Pfleger gefunden wurde
    }
    private void createTreatment(Treatment treatment) {
        TreatmentDao dao = DaoFactory.getDaoFactory().createTreatmentDao();
        try {
            dao.create(treatment);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    public void handleCancel(){
        stage.close();
    }

    private boolean areInputDataInvalid() {
        if (this.textFieldBegin.getText() == null || this.textFieldEnd.getText() == null) {
            return true;
        }
        try {
            LocalTime begin = DateConverter.convertStringToLocalTime(this.textFieldBegin.getText());
            LocalTime end = DateConverter.convertStringToLocalTime(this.textFieldEnd.getText());
            if (!end.isAfter(begin)) {
                return true;
            }
        } catch (Exception exception) {
            return true;
        }
        return this.textFieldDescription.getText().isBlank() || this.datePicker.getValue() == null;
    }
}