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
            Timestamp start = utils.convertTime(startTF.getText());
            Timestamp stop = utils.convertTime(stopTF.getText());
            Event event = new Event("Open Event",start,stop,utils.getOwner(),null, false,true);
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
}
