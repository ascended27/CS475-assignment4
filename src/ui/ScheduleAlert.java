package src.ui;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import src.Calendar;
import src.Client;
import src.Event;
import src.ui.AlertBox;
import src.ui.ClientUiController;
import src.ui.ClientUiFx;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScheduleAlert {

    final static ToggleGroup openGroup = new ToggleGroup();
    final static ToggleGroup privateGroup = new ToggleGroup();
    private static Scene openEventScene;
    private static Scene eventScene;
    private static Client client;

    public ScheduleAlert(){

    }

    public void display(Calendar calendar){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("New Event");
        window.setMinWidth(250);

        try {
            client = calendar.getOwner();
        } catch(RemoteException e1){
            AlertBox.display("Error",e1.getMessage());
        }
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("New Event");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label isOpen = new Label("Open Event:");
        grid.add(isOpen, 0, 1);
        RadioButton openYes = new RadioButton("Yes");
        RadioButton openNo = new RadioButton("No");
        openYes.setSelected(true);
        openYes.setToggleGroup(openGroup);
        openNo.setToggleGroup(openGroup);
        grid.add(openYes,2,1);
        grid.add(openNo, 1,1);


        Button confirmButton = new Button();
        confirmButton.setText("Continue");
        confirmButton.setOnMouseClicked(e->{
            if(openYes.isSelected()){
                setupOpenEventScene(window);
                window.setScene(openEventScene);
            } else{
                setupEventScene(window);
                window.setScene(eventScene);
            }
        });
        grid.add(confirmButton,1,3);

        Button denyButton = new Button();
        denyButton.setText("Cancel");
        denyButton.setOnMouseClicked(e->{
            window.close();
        });
        grid.add(denyButton,0,3);

        Scene scene = new Scene(grid,300,275);
        window.setScene(scene);
        window.showAndWait();
    }


    private boolean isAllFilledIn(){
        return false;
    }

    private static void setupOpenEventScene(Stage window){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("New Open Event");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label isPrivate = new Label("Private Event:");
        grid.add(isPrivate, 0, 1);

        GridPane grid2 = new GridPane();
        grid2.setAlignment(Pos.CENTER);
        grid2.setHgap(10);
        grid2.setVgap(10);
        grid2.setPadding(new Insets(0, 0, 0, 0));

        RadioButton privateYes = new RadioButton("Yes");
        RadioButton privateNo = new RadioButton("No");
        privateYes.setSelected(true);
        privateYes.setToggleGroup(privateGroup);
        privateNo.setToggleGroup(privateGroup);
        grid2.add(privateYes,0,0);
        grid2.add(privateNo, 1,0);
        grid.add(grid2,1,1);

        Label start = new Label("Start:");
        grid.add(start, 0,2);
        TextField startTF = new TextField();
        startTF.setPromptText("MM/DD/YYYY HH:MM:SS");
        grid.add(startTF, 1,2);

        Label stop = new Label("Stop:");
        grid.add(stop, 0,3);
        TextField stopTF = new TextField();
        stopTF.setPromptText("MM/DD/YYYY HH:MM:SS");
        grid.add(stopTF,1,3);

        Button confirmButton = new Button();
        confirmButton.setText("Save");
        confirmButton.setOnMouseClicked(e->{
            String startText = startTF.getText();
            String stopText = stopTF.getText();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/DD/YYYY HH:MM:SS");
            try {
                Date parsedDate = dateFormat.parse(startText);
                Timestamp startTime = new java.sql.Timestamp(parsedDate.getTime());
                parsedDate = dateFormat.parse(stopText);
                Timestamp stopTime = new java.sql.Timestamp(parsedDate.getTime());
//                ClientUiFx.getCalendar().scheduleEvent(client,null,"",startTime,stopTime,true);
//                ClientUiController.addEvent(ClientUiFx.getCalendar().retrieveEvent(client,startTime,stopTime),true);
            } catch (ParseException ex) {
                AlertBox.display("Error", ex.getMessage());
            }
            window.close();
        });

        grid.add(confirmButton,1,4);

        Button denyButton = new Button();
        denyButton.setText("Cancel");
        denyButton.setOnMouseClicked(e->{
            window.close();
        });
        grid.add(denyButton,0,4);

        openEventScene = new Scene(grid,400,275);
    }

    private static void setupEventScene(Stage window){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("New Event");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label isPrivate = new Label("Private Event:");
        grid.add(isPrivate, 0, 2);
        RadioButton privateYes = new RadioButton("Yes");
        RadioButton privateNo = new RadioButton("No");
        privateYes.setSelected(true);
        privateYes.setToggleGroup(privateGroup);
        privateNo.setToggleGroup(privateGroup);
        grid.add(privateYes,2,2);
        grid.add(privateNo, 1,2);

        Button confirmButton = new Button();
        confirmButton.setText("Save");
        confirmButton.setOnMouseClicked(e->{
            // TODO: Save
        });
        grid.add(confirmButton,1,3);

        Button denyButton = new Button();
        denyButton.setText("Cancel");
        denyButton.setOnMouseClicked(e->{
            window.close();
        });
        grid.add(denyButton,0,3);

        eventScene = new Scene(grid,300,275);
    }

}
