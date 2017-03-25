package gui.page.tab;

import gui.ControllerHelper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.*;
import model.factory.FuelCardFactory;
import model.factory.LoadFactory;

import java.time.LocalDateTime;

public class LoadFuelTabController {

    @FXML
    private TextField textFieldLoadValue, textFieldStartValue, textFieldEndValue, textFieldBLValue, textFieldFuelCardValue, textFieldFuelCardIssuedByValue;
    @FXML
    private ComboBox<Vehicle> comboBoxTruckTractorValue, comboBoxTrailer1Value, comboBoxTrailer2Value;
    @FXML
    private Label labelLoadNumber, labelStart, labelEnd, labelBLNumber, labelTruckTractor, labelTrailer1, labelTrailer2, labelFuelCardNumber, labelFuelCardIssuedBy;

    @FXML
    private void initialize(){
        alignHeights( 28 );
        VehicleList vehicleList = Mediator.getInstance().getVehicleList();
        comboBoxTruckTractorValue.setItems( vehicleList );
        comboBoxTrailer1Value.setItems( vehicleList );
        comboBoxTrailer2Value.setItems( vehicleList );
    }

    private void alignHeights(double initialSize){
        ControllerHelper.alignHeights( labelLoadNumber, textFieldLoadValue, initialSize );
        ControllerHelper.alignHeights( labelStart, textFieldStartValue, initialSize);
        ControllerHelper.alignHeights( labelEnd, textFieldEndValue, initialSize);
        ControllerHelper.alignHeights( labelBLNumber, textFieldBLValue, initialSize);
        ControllerHelper.alignHeights( labelTruckTractor, comboBoxTruckTractorValue, initialSize);
        ControllerHelper.alignHeights( labelTrailer1, comboBoxTrailer1Value, initialSize);
        ControllerHelper.alignHeights( labelTrailer2, comboBoxTrailer2Value, initialSize);
        ControllerHelper.alignHeights( labelFuelCardNumber, textFieldFuelCardValue, initialSize);
        ControllerHelper.alignHeights( labelFuelCardIssuedBy, textFieldFuelCardIssuedByValue, initialSize);
    }

    @FXML
    private void saveButtonClicked(ActionEvent actionEvent) {
        if ( ControllerHelper.showConfirmationWindow( "Load", "Are you sure you want to save load information?" ) ) {
            try {
                Mediator.getInstance().addLoad( getLoad() );
                ControllerHelper.showInformationWindow( "Load", "Load information saved!" );
            } catch (Exception e) {
                e.printStackTrace();
                ControllerHelper.showErrorWindow( "Error", "There's an error within the information you entered" );
            }
        }
    }

    private Load getLoad(){
        LocalDateTime startTime = LocalDateTime.parse( textFieldStartValue.getText(), Clock.formatter );
        LocalDateTime endTime = LocalDateTime.parse( textFieldEndValue.getText(), Clock.formatter );
        Vehicle truck = comboBoxTruckTractorValue.getValue();
        Vehicle trailer1 = comboBoxTrailer1Value.getValue();
        Vehicle trailer2 = comboBoxTrailer2Value.getValue();
        String blNumber = textFieldBLValue.getText();
        FuelCard fuelCard = getFuelCard();
        Driver driver = Mediator.getInstance().getLoggedDriver();
        Load load = LoadFactory.getInstance().getLoad( startTime, endTime, truck, trailer1, trailer2, blNumber, fuelCard, driver );
        return load;
    }

    private FuelCard getFuelCard(){
        int fuelCardNumber = Integer.parseInt( textFieldFuelCardValue.getText() );
        String fuelCardIssuedBy = textFieldFuelCardIssuedByValue.getText();
        return FuelCardFactory.getInstance().getFuelCard( fuelCardNumber, fuelCardIssuedBy );
    }
}
