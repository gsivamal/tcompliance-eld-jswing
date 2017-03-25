package gui.page.tab;

import gui.PageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import model.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TodayTabController {

    private PageController pageController = PageController.getInstance();
    private Driver driver;

    @FXML
    private Parent todayTab;
    @FXML
    private DatePicker datePickerSelectedDay;
    @FXML
    private Button buttonPreviousDay, buttonNextDay, buttonPrint;
    @FXML
    private TableView<Logbook> tableToday;
    @FXML
    private TableColumn<Logbook, String> tableColumnDuty, tableColumnLocation, tableColumnNotes;
    @FXML
    private TableColumn<Logbook, LocalDateTime> tableColumnStart;
    @FXML
    private TableColumn<Logbook, Duration> tableColumnDuration;


    @FXML
    private void initialize(){
        this.driver = pageController.getDriver();
        tableColumnDuty.prefWidthProperty().bind( tableToday.widthProperty().multiply( 0.197 ) );
        tableColumnStart.prefWidthProperty().bind( tableToday.widthProperty().multiply( 0.20 ) );
        tableColumnDuration.prefWidthProperty().bind( tableToday.widthProperty().multiply( 0.2 ) );
        tableColumnLocation.prefWidthProperty().bind( tableToday.widthProperty().multiply( 0.2 ) );
        tableColumnNotes.prefWidthProperty().bind( tableToday.widthProperty().multiply( 0.2 ) );

        tableColumnDuty.setCellValueFactory( cellData -> cellData.getValue().dutyStatusProperty().asString() );
        tableColumnStart.setCellValueFactory( cellData -> cellData.getValue().startTimeProperty() );
        tableColumnDuration.setCellValueFactory( cellData -> cellData.getValue().durationProperty() );
        tableColumnLocation.setCellValueFactory( cellData -> cellData.getValue().gpsLocationProperty().asString() );
        tableColumnNotes.setCellValueFactory( cellData -> cellData.getValue().notesProperty() );

        tableColumnStart.setCellFactory( col -> new TableCell<Logbook, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem( item, empty );
                if ( empty )
                    setText( null );
                else
                    setText( Clock.localDateTimeToString( item ) );
            }
        } );
        tableColumnDuration.setCellFactory( column -> new TableCell<Logbook, Duration>(){
            @Override
            protected void updateItem(Duration duration, boolean empty) {
                super.updateItem( duration, empty );
                if ( empty ) {
                    setText( null );
                }else{
                    setText( Clock.durationToString( duration ) );
                }
            }
        } );

        datePickerSelectedDay.valueProperty().addListener( observable -> {
            selectedDayValueChanged();
        } );
        datePickerSelectedDay.setValue( LocalDate.now() );
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
    }


    private void selectedDayValueChanged(){
        LocalDate selectedDay = datePickerSelectedDay.getValue();
        tableToday.setItems( driver.getLogbookList().getForDay( selectedDay ) );
    }
}
