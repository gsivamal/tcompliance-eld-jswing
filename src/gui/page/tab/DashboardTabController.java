package gui.page.tab;

import gui.PageController;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import model.Clock;
import model.Driver;
import model.Logbook;
import model.Mediator;

import java.time.Duration;

public class DashboardTabController {

    @FXML
    private Label labelCurrentStatus, labelStartDateTime, labelCurrentTime, labelDuration;
    @FXML
    private PieChart pieChartRule1, pieChartRule2, pieChartRule3, pieChartRule4;

    private PageController pageController = PageController.getInstance();
    private Driver driver = Mediator.getInstance().getLoggedDriver();

    @FXML
    private void initialize(){

        labelCurrentStatus.setText( "" + driver.getLogbookList().last().getDutyStatus() );
        labelStartDateTime.setText( Clock.localDateTimeToString( driver.getLogbookList().last().getStartTime() ) );

        driver.getLogbookList().addListener( (ListChangeListener<Logbook>) changeListener -> {
            if ( changeListener.next() && driver.getLogbookList().last() != null ) {
                labelCurrentStatus.setText( driver.getLogbookList().last().getDutyStatus().toString() );
                labelStartDateTime.setText( Clock.localDateTimeToString( driver.getLogbookList().last().getStartTime() ) );
            }
        } );
        Clock.currentTimeProperty().addListener( observable -> {
                labelCurrentTime.setText( Clock.localDateTimeToString( Clock.currentTimeProperty().get() ) );
                labelDuration.setText( Clock.durationToString( Duration.between( driver.getLogbookList().last().getStartTime(), Clock.currentTimeProperty().get() ) ) );
        });

        ObservableList<PieChart.Data> pieChartData1 = FXCollections.observableArrayList( new PieChart.Data( "Work", 60 ), new PieChart.Data( "Free", 40 ) );
        ObservableList<PieChart.Data> pieChartData2 = FXCollections.observableArrayList( new PieChart.Data( "Work", 60 ), new PieChart.Data( "Free", 40 ) );
        ObservableList<PieChart.Data> pieChartData3 = FXCollections.observableArrayList( new PieChart.Data( "Work", 60 ), new PieChart.Data( "Free", 40 ) );
        ObservableList<PieChart.Data> pieChartData4 = FXCollections.observableArrayList( new PieChart.Data( "Work", 60 ), new PieChart.Data( "Free", 40 ) );
        pieChartRule1.setData( pieChartData1 );
        pieChartRule2.setData( pieChartData2 );
        pieChartRule3.setData( pieChartData3 );
        pieChartRule4.setData( pieChartData4 );
    }
}
