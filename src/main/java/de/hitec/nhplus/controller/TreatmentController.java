package de.hitec.nhplus.controller;

import de.hitec.nhplus.datastorage.*;
import de.hitec.nhplus.model.Caregiver;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import de.hitec.nhplus.model.Patient;
import de.hitec.nhplus.model.Treatment;
import de.hitec.nhplus.utils.DateConverter;

import java.sql.SQLException;
import java.time.LocalDate;

public class TreatmentController {

    @FXML
    private Label labelPatientName;

    @FXML
    private Label labelCareLevel;

    @FXML
    private Label labelCareGiver;

    @FXML
    private Label labelCareGiverPhoneNumber;

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

    private AllTreatmentController controller;
    private Stage stage;
    private Patient patient;
    private Caregiver caregiver;
    private Treatment treatment;

    public void initializeController(AllTreatmentController controller, Stage stage, Treatment treatment) {
        this.stage = stage;
        this.controller= controller;
        PatientDao pDao = DaoFactory.getDaoFactory().createPatientDAO();
        CaregiverDao cDao = DaoFactory.getDaoFactory().createCaregiverDAO();
        try {
            this.patient = pDao.read((int) treatment.getPid());
            this.caregiver = cDao.read((int) treatment.getCid());
            this.treatment = treatment;
            showData();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
    private void showData(){
        this.labelPatientName.setText(patient.getSurname()+", "+patient.getFirstName());
        this.labelCareLevel.setText(patient.getCareLevel());
        this.labelCareGiver.setText(caregiver.getFullName());
        this.labelCareGiverPhoneNumber.setText(caregiver.getPhoneNumber());
        LocalDate date = DateConverter.convertStringToLocalDate(treatment.getDate());
        this.datePicker.setValue(date);
        this.textFieldBegin.setText(this.treatment.getBegin());
        this.textFieldEnd.setText(this.treatment.getEnd());
        this.textFieldDescription.setText(this.treatment.getDescription());
        this.textAreaRemarks.setText(this.treatment.getRemarks());
    }
    @FXML
    public void handleChange(){
        this.treatment.setDate(this.datePicker.getValue().toString());
        this.treatment.setBegin(textFieldBegin.getText());
        this.treatment.setEnd(textFieldEnd.getText());
        this.treatment.setDescription(textFieldDescription.getText());
        this.treatment.setRemarks(textAreaRemarks.getText());
        doUpdate();
        controller.readAllAndShowInTableView();
        stage.close();
    }

    private void doUpdate(){
        TreatmentDao dao = DaoFactory.getDaoFactory().createTreatmentDao();
        try {
            dao.update(treatment);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    public void handleCancel(){
        stage.close();
    }
}