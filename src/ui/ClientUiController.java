package src.ui;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import src.Event;

import java.io.IOException;
import java.rmi.RemoteException;

public class ClientUiController {

    @FXML
    public Button scheduleButton;
    @FXML
    public Button deleteButton;
    @FXML
    public TableView table;

    public static ObservableList<EventRow> data = FXCollections.observableArrayList();

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
            AlertBox.display("Error","Failed to open new event");
        }
    }

    public void openDeleteDialog(MouseEvent mouseEvent) {
        deleteButton.setText("Test");
    }

    public void addEvent(Event event,boolean isOpen) {
        try {
            if(isOpen)
                data.add(new EventRow(event.getOwner().getName(),event.getStart().toString(),"Open Event"));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        table.refresh();
    }
}
