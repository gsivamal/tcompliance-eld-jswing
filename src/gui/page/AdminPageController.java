package gui.page;

import gui.PageController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AdminPageController {

    @FXML
    private void initialize(){

    }

    @FXML
    private void driverTabSelected(){
        PageController.getInstance().setTitle( new Label( "Driver List" ) );
    }

    @FXML
    private void equipmentTabSelected(Event event) {
        PageController.getInstance().setTitle( new Label( "Equipment List" ) );
    }
}
