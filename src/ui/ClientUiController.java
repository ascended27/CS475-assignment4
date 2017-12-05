package src.ui;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import src.Event;

import java.io.IOException;
import java.sql.Timestamp;

public class ClientUiController {

    @FXML
    public Button scheduleButton;
    @FXML
    public Button deleteButton;
    @FXML
    public TableView table;

    private ObservableList<EventRow> data = FXCollections.observableArrayList();
    private static Util utils;

    @FXML
    public void initialize() {
        utils = Util.getInstance();
        if (utils != null) {
            utils.registerTableList(data);
        }

        TableColumn<EventRow, String> ownerColumn = (TableColumn<EventRow, String>) table.getColumns().get(0);
        TableColumn<EventRow, String> titleColumn = (TableColumn<EventRow, String>) table.getColumns().get(1);
        TableColumn<EventRow, Timestamp> startColumn = (TableColumn<EventRow, Timestamp>) table.getColumns().get(2);
        TableColumn<EventRow, Timestamp> stopsColumn = (TableColumn<EventRow, Timestamp>) table.getColumns().get(3);

        ownerColumn.setCellValueFactory(new PropertyValueFactory<>("ownerName"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        stopsColumn.setCellValueFactory(new PropertyValueFactory<>("stop"));

        table.setItems(data);
    }

    public void openScheduleDialog(MouseEvent mouseEvent) {
        try {
            Stage window = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("FXML/NewEventOpen.fxml"));
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("New Event Open");
            window.setResizable(false);

            Scene scene = new Scene(root);
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            AlertBox.display("Error", "Failed to open new event");
        }
    }

    public void openDeleteDialog(MouseEvent mouseEvent) {
        deleteButton.setText("Test");
    }
}
