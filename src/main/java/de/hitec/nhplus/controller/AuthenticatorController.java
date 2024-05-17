package de.hitec.nhplus.controller;

import de.hitec.nhplus.Main;
import de.hitec.nhplus.datastorage.AppointmentDao;
import de.hitec.nhplus.datastorage.CaregiverDao;
import de.hitec.nhplus.model.Caregiver;
import de.hitec.nhplus.sessions.Session;
import de.hitec.nhplus.utils.PassHash;
import de.hitec.nhplus.datastorage.DaoFactory;
import de.hitec.nhplus.datastorage.UserDao;
import de.hitec.nhplus.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.sql.SQLException;

public class AuthenticatorController {

    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;

    private UserDao dao;

    /**
     * Handles the login process when the login button is pressed.
     */
    @FXML
    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        dao = DaoFactory.getDaoFactory().createUserDAO();

        try {
            User user = dao.getUserByUsername(username);
            if (user != null && PassHash.verifyPassword(password, user.getHashedPassword())) {
                Session.getInstance().setUserSession(user);
                checkUnsyncCaregiverAccount(user);
                loadMainWindow();
            } else {
                showAlert("Error", "Ungültige Anmeldeinformationen", "Bitte überprüfen Sie Ihren Benutzernamen und Ihr Passwort.");
            }
        } catch (SQLException e) {
            showAlert("Error", "Ungültige Anmeldeinformationen", "Bitte überprüfen Sie Ihren Benutzernamen und Ihr Passwort.");
        }
    }

    /**
     * Loads the main window of the application after a successful login.
     */
    private void loadMainWindow() {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/MainWindowView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Displays an alert dialog with the specified title, header, and content.
     *
     * @param title   the title of the alert dialog
     * @param header  the header text of the alert dialog
     * @param content the content text of the alert dialog
     */
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Checks if there is an unsynchronized caregiver account linked to the user.
     * If a similar caregiver account is found, prompts the user to link their account.
     *
     * @param user the user to check for an unsynchronized caregiver account
     * @throws SQLException if a database access error occurs
     */
    /**
     * Checks if there is an unsynchronized caregiver account linked to the user.
     * If a similar caregiver account is found, prompts the user to link their account.
     *
     * @param user the user to check for an unsynchronized caregiver account
     * @throws SQLException if a database access error occurs
     */
    private void checkUnsyncCaregiverAccount(User user) throws SQLException {
        CaregiverDao caregiverDao = DaoFactory.getDaoFactory().createCaregiverDAO();
        Caregiver linkedCaregiver = caregiverDao.read(user.getCaregiverId());

        if (linkedCaregiver == null) {
            try {
                linkedCaregiver = caregiverDao.getUserByCredentials(user.getFirstName(), user.getSurname(), user.getPhoneNumber());
                if (linkedCaregiver != null) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Es wurde ein Eintrag als Pfleger gefunden. Möchtest du deinen Account mit diesem Eintrag verlinken? WICHTIG: Dies kann nicht direkt wieder entfernt werden.", new ButtonType("Yes", ButtonBar.ButtonData.YES), new ButtonType("Nein", ButtonBar.ButtonData.NO));
                    alert.setTitle("Meldung");

                    // Show the alert and wait for the user response
                    Caregiver finalLinkedCaregiver = linkedCaregiver;
                    alert.showAndWait().ifPresent(response -> {
                        if (response.getButtonData() == ButtonBar.ButtonData.YES) {
                            long caregiverId = finalLinkedCaregiver.getCaregiverId();
                            user.setCaregiverId(caregiverId);
                            System.out.println("Caregiver ID set: " + user.getCaregiverId() + user.getUserId());
                            user.getClass().toString();
                            try {
                                dao.update(user);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            System.out.println("User chose not to link the caregiver account.");
                        }
                    });
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
