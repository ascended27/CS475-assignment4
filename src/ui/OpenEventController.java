package src.ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class OpenEventController {

    @FXML
    public ToggleGroup privateGroup;
    @FXML
    public RadioButton yesPrivate;
    @FXML
    public RadioButton noPrivate;
    @FXML
    public TextField startTF;
    @FXML
    public TextField stopTF;
    @FXML
    public Button cancelButton;
    @FXML
    public Button savedButton;

    public void cancel(MouseEvent mouseEvent) {
        // TODO: Close window
    }

    public void save(MouseEvent mouseEvent) {
        // TODO: Save event
    }
}
