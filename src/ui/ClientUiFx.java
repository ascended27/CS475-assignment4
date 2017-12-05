package src.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import src.*;

import java.io.IOException;
import java.rmi.RemoteException;

public class ClientUiFx extends Application {

    private Util utils;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            utils = Util.getInstance();
            Parent root = FXMLLoader.load(getClass().getResource("FXML/ClientUiFXML.fxml"));
            Scene scene = new Scene(root, 745, 500);
            primaryStage.setTitle("Scheduler");
            primaryStage.setResizable(false);
            primaryStage.setOnCloseRequest(e -> close(primaryStage));
//            primaryStage.setScene(scene);

            // First get username and client manager
            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(25, 25, 25, 25));

            Label userLabel = new Label("Username:");
            grid.add(userLabel, 0, 0);

            TextField usernameTF = new TextField();
            grid.add(usernameTF, 1, 0);

            Button continueBtn = new Button();
            continueBtn.setText("Next");
            grid.add(continueBtn, 1, 1);
            continueBtn.setOnMouseClicked(e -> {
                if (usernameTF.getText().equals("")) {
                    AlertBox.display("Error", "Username is required");
                } else {
                    String username = usernameTF.getText();

                    if (utils.checkUser(username)) {
                        // Open Scheduler
                        primaryStage.setScene(scene);
                    } else {
                        AlertBox.display("Error", "Failed to retrieve user: " + username);
                    }

                }
            });

            Scene initScene = new Scene(grid, 400, 275);
            primaryStage.setScene(initScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void close(Stage window) {
        // Handle any closing requirements here
        utils.killClock();
        window.close();
    }
}
