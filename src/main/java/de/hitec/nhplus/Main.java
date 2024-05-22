package de.hitec.nhplus;

import de.hitec.nhplus.controller.DatabaseChecker;
import de.hitec.nhplus.controller.AuthenticatorController;
import de.hitec.nhplus.datastorage.ConnectionBuilder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

/**
 * Main class for the NHPlus application.
 * This class initializes the JavaFX application and sets up the main window.
 */
public class Main extends Application {

    private Stage primaryStage;

    /**
     * The entry point for the JavaFX application.
     *
     * @param primaryStage the primary stage for this application
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showAuthenticatorWindow();
        initializeDatabaseChecker();
    }

    /**
     * Loads and displays the authenticator window.
     */
    private void showAuthenticatorWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/AuthenticationView.fxml"));
            BorderPane pane = loader.load();

            Scene scene = new Scene(pane);
            this.primaryStage.setTitle("NHPlus");
            this.primaryStage.setScene(scene);
            this.primaryStage.setResizable(false);
            this.primaryStage.show();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Initializes the database checker to run every 45 minutes.
     */
    private void initializeDatabaseChecker() {
        Connection connection = ConnectionBuilder.getConnection();
        DatabaseChecker databaseChecker = new DatabaseChecker(connection);
        databaseChecker.repeatEvery45Minutes();
    }

    /**
     * The main method that launches the JavaFX application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}