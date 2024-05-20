package de.hitec.nhplus;

import de.hitec.nhplus.controller.DatabaseChecker;
import de.hitec.nhplus.controller.AuthenticatorController;
import de.hitec.nhplus.datastorage.ConnectionBuilder;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class Main extends Application {

    private Stage primaryStage;
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        AuthenticatorWindow();
        getDatabaseChecker();
    }
    public void AuthenticatorWindow(){
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

    public void getDatabaseChecker(){
        Connection connection = ConnectionBuilder.getConnection();
        DatabaseChecker databaseChecker = new DatabaseChecker(connection);
        databaseChecker.repeatEvery45Minutes();
    }

    public static void main(String[] args) {
        launch(args);
    }
}