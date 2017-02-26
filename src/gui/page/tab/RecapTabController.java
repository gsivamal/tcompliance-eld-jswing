package gui.page.tab;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.LocalDate;

public class RecapTabController {

    @FXML
    private Parent recapTab;
    @FXML
    private Button buttonPreviousDay, buttonNextDay, buttonDetails;
    @FXML
    private DatePicker datePickerSelectedDay;
    @FXML
    private TableView tableRecap;
    @FXML
    private TableColumn tableColumnDate, tableColumnDriving, tableColumnOnDuty, tableColumnMileage, tableColumnTotal;


    @FXML
    private void initialize(){
        datePickerSelectedDay.setValue( LocalDate.now() );
        tableColumnDate.prefWidthProperty().bind( tableRecap.widthProperty().multiply( 0.197 ) );
        tableColumnDriving.prefWidthProperty().bind( tableRecap.widthProperty().multiply( 0.20 ) );
        tableColumnOnDuty.prefWidthProperty().bind( tableRecap.widthProperty().multiply( 0.2 ) );
        tableColumnMileage.prefWidthProperty().bind( tableRecap.widthProperty().multiply( 0.2 ) );
        tableColumnTotal.prefWidthProperty().bind( tableRecap.widthProperty().multiply( 0.2 ) );
    }

    @FXML
    private void buttonPreviousDayClicked(ActionEvent actionEvent) {
        datePickerSelectedDay.setValue( datePickerSelectedDay.getValue().minusDays( 1 ) );
    }

    @FXML
    private void buttonNextDayClicked(ActionEvent actionEvent) {
        datePickerSelectedDay.setValue( datePickerSelectedDay.getValue().plusDays( 1 ) );
    }

    @FXML
    private void buttonDetailsClicked(ActionEvent actionEvent) {
    }
}
