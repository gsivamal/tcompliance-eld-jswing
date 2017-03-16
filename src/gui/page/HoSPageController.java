package gui.page;

import gui.ControllerHelper;
import gui.PageController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.StageStyle;
import model.Clock;
import model.Driver;
import model.Logbook;
import model.LogbookList;

public class HoSPageController {

    @FXML
    private Label labelCurrentStatus, labelDriver, labelDriverValue, labelDate, labelDateValue, labelCoDriver, labelCoDriverValue, labelBigTimeValue, labelNotificationMessage;
    @FXML
    private Button buttonCurrentStatusValue;


    private Driver driver;
    private LogbookList logbookList;
    private PageController pageController = PageController.getInstance();

    @FXML
    private void initialize(){
        pageController.setTitle( new Label("Hours of Service") );
        this.driver = pageController.getDriver();
        this.logbookList = driver.getLogbookList();

        String colorHexCode1 = logbookList.last().getDutyStatus().getColorHexCode();
        buttonCurrentStatusValue.setStyle( "-fx-background-color: " + colorHexCode1 + ";" );
        buttonCurrentStatusValue.setText( logbookList.last().getDutyStatus().toString() );

        logbookList.addListener( (ListChangeListener<Logbook>) changeListener -> {
            if ( changeListener.next() && logbookList.last() != null ) {
                String colorHexCode = logbookList.last().getDutyStatus().getColorHexCode();
                buttonCurrentStatusValue.setStyle( "-fx-background-color: " + colorHexCode + ";" );
                buttonCurrentStatusValue.setText( logbookList.last().getDutyStatus().toString() );
            }
        } );
        labelDateValue.setText( logbookList.first().getStartTime().format( Clock.formatter ) );


        labelDriverValue.textProperty().bind( driver.firstNameProperty() );
        labelNotificationMessage.textProperty().bind( new SimpleStringProperty( "Notification message" ) );

    }

    @FXML
    private void buttonCurrentStatusValueClicked(ActionEvent actionEvent) {
        Alert alert = new Alert( Alert.AlertType.NONE );
        alert.initStyle( StageStyle.UNDECORATED );
        alert.setResizable( false );
        alert.getDialogPane().setContent( ControllerHelper.getChangeStatusPage() );
        alert.getDialogPane().getStylesheets().add( ControllerHelper.getUtilCSSFile() );
        alert.getDialogPane().setStyle( "-fx-border-color: #4f81bd;" +
                                        "-fx-border-width: 5px;" );
        alert.showAndWait();
    }
}
