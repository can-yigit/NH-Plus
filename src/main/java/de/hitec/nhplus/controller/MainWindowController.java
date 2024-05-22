package de.hitec.nhplus.controller;

import de.hitec.nhplus.Main;
import de.hitec.nhplus.model.User;
import de.hitec.nhplus.sessions.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MainWindowController {


    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private Label username;

    public void initialize() {
        // Hier kannst du auch andere Initialisierungen durchführen
        User session = Session.getInstance().getUserSession();
        username.setText(session.getFullName());
    }

    @FXML
    private void handleShowAllPatient(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/AllPatientView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    @FXML
    private void handleShowAllTreatments(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/AllTreatmentView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    @FXML
    private void handleShowAllCareGiver(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/AllCaregiverView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    @FXML
    private void handleShowAllAppointment(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/AllAppointmentView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private AllPatientController allPatientController;
    public boolean isDarkTheme = false;

    @FXML
    private Button showAllPatient;
    @FXML
    private Button showAllTreatments;
    @FXML
    private Button showAllCareGiver;
    @FXML
    private VBox vBox;
    @FXML
    private Button darkWhiteChancing;
    @FXML
    private Button showTerminKalender;

    @FXML
    private void readAllPatientView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/de/hitec/nhplus/AllPatientView.fxml"));
            AnchorPane pane = loader.load();
            allPatientController = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void changeThema() {


        isDarkTheme = !isDarkTheme;


        setButtonStyle(showAllPatient, CSSClasses.darkButton);
        setButtonStyle(showAllTreatments, CSSClasses.darkButton);
        setButtonStyle(showAllCareGiver, CSSClasses.darkButton);
        setButtonStyle(darkWhiteChancing, CSSClasses.darkButton);
        setButtonStyle(showTerminKalender, CSSClasses.darkButton);
        setVboxStyle(vBox, CSSClasses.dartVbox);


        // TODO
        if (allPatientController != null) {
            allPatientController.setPatientView(CSSClasses.darkButton, isDarkTheme);
        }
    }

    private void setButtonStyle(Button button, String darkStyle) {
        if (isDarkTheme) {
            if (!button.getStyleClass().contains(darkStyle)) {
                button.getStyleClass().add(darkStyle);
            }
        } else {
            button.getStyleClass().remove(darkStyle);
        }
    }

    private void setVboxStyle(VBox vBox, String darkBackground) {
        if (isDarkTheme) {
            if (!vBox.getStyleClass().contains(darkBackground)) {
                vBox.getStyleClass().add(darkBackground);
            }
        } else {
            vBox.getStyleClass().remove(darkBackground);
        }
    }


}
