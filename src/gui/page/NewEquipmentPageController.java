package gui.page;

import gui.ControllerHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Mediator;
import model.Vehicle;
import model.factory.VehicleFactory;

public class NewEquipmentPageController {

    @FXML
    private Parent newEquipmentPage;
    @FXML
    private TextField textFieldID, textFieldName, textFieldMake, textFieldVIN;
    @FXML
    private Label labelID, labelName, labelMake, labelVIN;

    @FXML
    private void initialize(){
        alignHeights( 28 );
    }

    private void alignHeights(double initialSize){
        ControllerHelper.alignHeights( labelID, textFieldID, initialSize );
        ControllerHelper.alignHeights( labelName, textFieldName, initialSize );
        ControllerHelper.alignHeights( labelMake, textFieldMake, initialSize );
        ControllerHelper.alignHeights( labelVIN, textFieldVIN, initialSize );
    }

    @FXML
    private void buttonSaveNewClicked(ActionEvent actionEvent) {
        if ( ControllerHelper.showConfirmationWindow( "Save", "Are you sure you want to save this user?" ) ) {
            try {
                saveEquipment();
                close();
            } catch (Exception e) {
                ControllerHelper.showErrorWindow( "Error", e.getMessage() );
            }
        }
    }

    @FXML
    private void buttonCancelNewClicked(ActionEvent actionEvent) {
        if ( ControllerHelper.showConfirmationWindow( "Cancel", "Are you sure you want to cancel user saving?" ) ) {
            close();
        }
    }

    private void close(){
        ((Stage)newEquipmentPage.getScene().getWindow()).close();
    }

    private void saveEquipment(){
        Mediator.getInstance().addVehicle( getEquipment() );
    }

    private Vehicle getEquipment(){
        String equipmentName = textFieldName.getText();
        String equipmentMake = textFieldMake.getText();
        String equipmentVIN = textFieldVIN.getText();
        return VehicleFactory.getInstance().getVehicle( equipmentName, equipmentMake, equipmentVIN );
    }
}
