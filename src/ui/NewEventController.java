package src.ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class NewEventController {
    @FXML
    public ToggleGroup privateGroup;
    @FXML
    public RadioButton yesPrivate;
    @FXML
    public RadioButton noPrivate;
    @FXML
    public TextField titleTF;
    @FXML
    public TextField startTF;
    @FXML
    public TextField stopTF;
    @FXML
    public Button cancelButton;
    @FXML
    public Button savedButton;
    @FXML
    public ListView clientList;
    @FXML
    public ListView selectedClients;
    @FXML
    public Button selectClient;
    @FXML
    public Button removeClient;

    public void cancel(MouseEvent mouseEvent) {
        //TODO: Close window
    }

    public void save(MouseEvent mouseEvent) {
        //TODO: Save Event
    }

    public void moveClientRight(MouseEvent mouseEvent) {
        //TODO: Move client from clientList to selectedClients
    }

    public void moveClientLeft(MouseEvent mouseEvent) {
        //TODO: Move client from selectedClients to clientList
    }
}
