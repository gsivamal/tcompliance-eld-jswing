package gui.page.tab;

import gui.PageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import model.Clock;
import model.Service;
import model.Status;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TodayTabController {

    private PageController pageController = PageController.getInstance();
    private Service service;

    @FXML
    private Parent todayTab;
    @FXML
    private DatePicker datePickerSelectedDay;
    @FXML
    private Button buttonPreviousDay, buttonNextDay, buttonPrint;
    @FXML
    private TableView<Status> tableToday;
    @FXML
    private TableColumn<Status, String> tableColumnDuty, tableColumnLocation, tableColumnNotes;
    @FXML
    private TableColumn<Status, LocalDateTime> tableColumnStart;
    @FXML
    private TableColumn<Status, Duration> tableColumnDuration;


    @FXML
    private void initialize(){
        this.service = pageController.getUser().getService();
        datePickerSelectedDay.setValue( LocalDate.now() );
        tableColumnDuty.prefWidthProperty().bind( tableToday.widthProperty().multiply( 0.197 ) );
        tableColumnStart.prefWidthProperty().bind( tableToday.widthProperty().multiply( 0.20 ) );
        tableColumnDuration.prefWidthProperty().bind( tableToday.widthProperty().multiply( 0.2 ) );
        tableColumnLocation.prefWidthProperty().bind( tableToday.widthProperty().multiply( 0.2 ) );
        tableColumnNotes.prefWidthProperty().bind( tableToday.widthProperty().multiply( 0.2 ) );

        tableColumnDuty.setCellValueFactory( cellData -> cellData.getValue().statusValueProperty().asString() );
        tableColumnStart.setCellValueFactory( cellData -> cellData.getValue().startTimeProperty() );
        tableColumnDuration.setCellValueFactory( cellData -> cellData.getValue().durationProperty() );
        tableColumnLocation.setCellValueFactory( cellData -> cellData.getValue().locationProperty() );
        tableColumnNotes.setCellValueFactory( cellData -> cellData.getValue().notesProperty() );

        tableColumnStart.setCellFactory( col -> new TableCell<Status, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem( item, empty );
                if ( empty )
                    setText( null );
                else
                    setText( Clock.localDateTimeToString( item ) );
            }
        } );
        tableColumnDuration.setCellFactory( colum -> new TableCell<Status, Duration>(){
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

        tableToday.setItems( service.getStatusHistory().observableList() );
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
}
