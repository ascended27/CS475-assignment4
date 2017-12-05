package src.ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import src.Event;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    private Util utils;

    @FXML
    public void initialize(){
        this.utils = Util.getInstance();
    }

    public void cancel(MouseEvent mouseEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void save(MouseEvent mouseEvent) {
        if(isAllFilledIn()){
            Timestamp start = convertTime(startTF.getText());
            Timestamp stop = convertTime(stopTF.getText());
            Event event = new Event("Open Event",start,stop,utils.getOwner(),null, !yesPrivate.isSelected(),true);
            if(!utils.insertOpenEvent(event)){
                AlertBox.display("Error","Failed to schedule open event");
            }
            Stage stage = (Stage) savedButton.getScene().getWindow();
            stage.close();
        } else{
            AlertBox.display("Error","All fields must be filled in");
        }
    }

    private boolean isAllFilledIn(){
        return !startTF.getText().isEmpty() && !stopTF.getText().isEmpty();
    }

    private Timestamp convertTime(String time) {
        // TODO: This isn't right
        // 03/03/03 03:03:25 -> 2017-12-31 03:00:00.003
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/DD/YYYY HH:MM:SS");
            Timestamp toReturn;
            Date parsedDate = null;
            parsedDate = dateFormat.parse(time);
            toReturn = new java.sql.Timestamp(parsedDate.getTime());
            return toReturn;
        } catch (ParseException e) {
            return null;
        }
    }
}
