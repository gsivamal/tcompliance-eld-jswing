package gui.page.tab;

import domain.mediator.PDFGeneration;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import domain.model.*;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public class RecapTabController {

    @FXML
    private Parent recapTab;
    @FXML
    private Button buttonPreviousDay, buttonNextDay, buttonPrint;
    @FXML
    private DatePicker datePickerSelectedDay;
    @FXML
    private TableView<Recap> tableRecap;
    @FXML
    private TableColumn<Recap, LocalDate> tableColumnDate;
    @FXML
    private TableColumn<Recap, Duration> tableColumnDriving, tableColumnOnDuty, tableColumnTotal;
    @FXML
    private TableColumn<Recap, Integer> tableColumnMileage;
    @FXML
    private Label labelTotal;


    @FXML
    private void initialize(){
        datePickerSelectedDay.valueProperty().addListener( (observable, oldValue, newValue) -> {
            selectedDayValueUpdated( newValue );
        } );
        datePickerSelectedDay.setValue( LocalDate.now() );
        tableColumnDate.prefWidthProperty().bind( tableRecap.widthProperty().multiply( 0.197 ) );
        tableColumnDriving.prefWidthProperty().bind( tableRecap.widthProperty().multiply( 0.20 ) );
        tableColumnOnDuty.prefWidthProperty().bind( tableRecap.widthProperty().multiply( 0.2 ) );
        tableColumnMileage.prefWidthProperty().bind( tableRecap.widthProperty().multiply( 0.2 ) );
        tableColumnTotal.prefWidthProperty().bind( tableRecap.widthProperty().multiply( 0.2 ) );

        tableColumnDate.setCellFactory( col -> new LocalDateTableCell<>() );
        tableColumnDriving.setCellFactory( col -> new DurationTableCell<>() );
        tableColumnOnDuty.setCellFactory( col -> new DurationTableCell<>() );
        tableColumnTotal.setCellFactory( col -> new DurationTableCell<>() );
        tableColumnDate.setCellValueFactory( cellData -> cellData.getValue().dateProperty() );
        tableColumnDriving.setCellValueFactory( cellData -> cellData.getValue().drivingDurationProperty() );
        tableColumnOnDuty.setCellValueFactory( cellData -> cellData.getValue().onDutyDurationProperty() );
        tableColumnMileage.setCellValueFactory( cellData -> cellData.getValue().mileageProperty().asObject() );
        tableColumnTotal.setCellValueFactory( cellData -> cellData.getValue().totalDurationProperty() );
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
    private void buttonPrintClicked(ActionEvent actionEvent) {
        List<Recap> recapList = tableRecap.getItems();
        try {
            PDFGeneration.printRecapPDF( recapList );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void selectedDayValueUpdated(LocalDate localDate) {
        LogbookList logbookList = Mediator.getInstance().getLoggedDriver().getLogbookList();
        ObservableList<Logbook> allDays = logbookList.recapBeforeDay( localDate );
        RecapList recapList = new RecapList( allDays );
        tableRecap.setItems( recapList );
        labelTotal.textProperty().bind(
                Bindings.createStringBinding( () ->
                        Clock.durationToString( recapList.getTotalDuration() ),
                        recapList.totalDurationProperty() )
        );
    }
}
