package de.hitec.nhplus.controller;

import de.hitec.nhplus.Main;
import de.hitec.nhplus.utils.PassHash;
import de.hitec.nhplus.datastorage.DaoFactory;
import de.hitec.nhplus.datastorage.UserDao;
import de.hitec.nhplus.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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


    private User Session;

    public User getSession() {
        return Session;
    }

    @FXML
    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        this.dao = DaoFactory.getDaoFactory().createUserDAO();
            try {
                User user = this.dao.getUserByUsername(username);
                if(PassHash.verifyPassword(password, user.getHashedPassword())){
                    this.Session = user;
                        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/MainWindowView.fxml"));
                        try {
                            mainBorderPane.setCenter(loader.load());
                            MainWindowController controller = loader.getController();
                            controller.setUserSession(this);
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                }
            } catch (SQLException e) {
                showAlert("Error", "Ungültige Anmeldeinformationen", "Bitte überprüfen Sie Ihren Benutzernamen und Ihr Passwort.");
            }
    }
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
