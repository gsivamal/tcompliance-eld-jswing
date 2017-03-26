package gui.page.tab;

import gui.ControllerHelper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.StageStyle;
import model.Mediator;
import model.Vehicle;

public class EquipmentListTabController {

    @FXML
    private Button buttonNew, buttonDisable, buttonDelete;
    @FXML
    private TableView<Vehicle> tableEquipmentList;
    @FXML
    private TableColumn<Vehicle, Integer> tableColumnID;
    @FXML
    private TableColumn<Vehicle, String> tableColumnName, tableColumnMake, tableColumnVIN;

    @FXML
    private void initialize(){
        tableEquipmentList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableColumnID.setCellValueFactory( cellData -> new SimpleIntegerProperty( cellData.getValue().getID() ).asObject() );
        tableColumnName.setCellValueFactory( cellData -> cellData.getValue().nameProperty() );
        tableColumnMake.setCellValueFactory( cellData -> cellData.getValue().makeProperty() );
        tableColumnVIN.setCellValueFactory( cellData -> cellData.getValue().vinProperty() );

        tableEquipmentList.setItems( Mediator.getInstance().getVehicleList() );
    }

    @FXML
    private void buttonNewClicked(ActionEvent actionEvent) {
        Alert alert = new Alert( Alert.AlertType.NONE );
        alert.initStyle( StageStyle.UNDECORATED );
        alert.setResizable( false );
        alert.getDialogPane().setContent( ControllerHelper.getAddEquipmentPage() );
        alert.getDialogPane().getStylesheets().add( ControllerHelper.getUtilCSSFile() );
        alert.getDialogPane().setStyle( "-fx-border-color: #4f81bd;" +
                "-fx-border-width: 5px;" );
        alert.showAndWait();
    }

    @FXML
    private void buttonDeleteClicked(ActionEvent actionEvent) {
        Vehicle selected = getSelected();
        if ( selected != null ) {
            if ( ControllerHelper.showConfirmationWindow( "Delete equipment", "Are you sure you want to delete equipment ID: " + selected.getID() ) ) {
                Mediator.getInstance().removeVehicle( selected );
            }
        }
    }

    private Vehicle getSelected(){
        return tableEquipmentList.getSelectionModel().getSelectedItem();
    }
}
