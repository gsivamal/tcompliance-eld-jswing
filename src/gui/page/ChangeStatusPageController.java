package gui.page;

import gui.ControllerHelper;
import gui.PageController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import model.Service;
import model.Status;

public class ChangeStatusPageController {

    @FXML
    private HBox changeStatusPage;

    private PageController pageController = PageController.getInstance();
    private Service service = pageController.getUser().getService();

    @FXML
    private void initialize(){
        Status.StatusValue[] statusValues = Status.StatusValue.values();
        for (Status.StatusValue statusValue : statusValues) {
            Button statusButton = new Button( statusValue.toString() );
            statusButton.setStyle( "-fx-background-color: " + statusValue.getColorHexCode() + ";" );
            statusButton.getStyleClass().add( "changeStatusButton" );
            statusButton.getStylesheets().add( ControllerHelper.class.getResource( "util.css" ).toExternalForm() );
            statusButton.setOnAction( event -> {
                Status.StatusValue buttonStatusValue = Status.StatusValue.valueOf( statusButton.getText() );
                service.setCurrentStatus( new Status( buttonStatusValue ) );
                pageController.setPage( ControllerHelper.getHoSPage() );
            } );
            changeStatusPage.getChildren().add( statusButton );
        }
    }
}
