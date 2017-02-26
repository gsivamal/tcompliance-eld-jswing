package gui.page;

import gui.ControllerHelper;
import gui.PageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.StageStyle;
import model.Service;

public class HoSPageController {

    @FXML
    private Label labelCurrentStatus, labelDriver, labelDriverValue, labelDate, labelDateValue, labelCoDriver, labelCoDriverValue, labelBigTimeValue, labelNotificationMessage;
    @FXML
    private Button buttonCurrentStatusValue;


    private Service service;
    private PageController pageController = PageController.getInstance();

    @FXML
    private void initialize(){
        pageController.setTitle( new Label("Hours of Service") );
        this.service = pageController.getUser().getService();

        String colorHexCode1 = service.getCurrentStatus().getStatusValue().getColorHexCode();
        buttonCurrentStatusValue.setStyle( "-fx-background-color: " + colorHexCode1 + ";" );
        buttonCurrentStatusValue.setText( service.getCurrentStatus().getStatusValue().toString() );

        service.currentStatusProperty().addListener( observable -> {
            if(service.getCurrentStatus() != null) {
                String colorHexCode = service.getCurrentStatus().getStatusValue().getColorHexCode();
                buttonCurrentStatusValue.setStyle( "-fx-background-color: " + colorHexCode + ";" );
                buttonCurrentStatusValue.setText( service.getCurrentStatus().getStatusValue().toString() );
            }
        } );
        labelDateValue.textProperty().bind( service.startDateProperty() );


        labelDriverValue.textProperty().bind( service.driverNameProperty() );
        labelCoDriverValue.textProperty().bind( service.coDriverNameProperty() );
        labelNotificationMessage.textProperty().bind( service.notificationMessageProperty() );

    }

    @FXML
    private void buttonCurrentStatusValueClicked(ActionEvent actionEvent) {
        Alert alert = new Alert( Alert.AlertType.NONE );
        alert.initStyle( StageStyle.UNDECORATED );
        alert.setResizable( false );
        alert.getDialogPane().setContent( ControllerHelper.getChangeStatusPage() );
        alert.getDialogPane().getStylesheets().add( ControllerHelper.getUtilCSSFile() );
        alert.showAndWait();
    }
}
