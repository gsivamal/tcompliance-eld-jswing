package gui.page.tab;

import gui.PageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import domain.model.Driver;
import domain.model.LogbookList;

import java.time.LocalDate;

public class GraphTabController {

    private PageController pageController = PageController.getInstance();
    private Driver driver;
    private LogbookList logbookList;

    @FXML
    private Button buttonNextDay, buttonPreviousDay, buttonPrint;
    @FXML
    private DatePicker datePickerSelectedDay;
    @FXML
    private LineChart lineChartGraph;

    @FXML
    private void initialize(){
        this.logbookList = pageController.getDriver().getLogbookList();
        datePickerSelectedDay.setValue( LocalDate.now() );
        XYChart.Series<Integer, String> series = new XYChart.Series();
        series.setData( logbookList.chartData() );
        lineChartGraph.getData().add( series );
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
