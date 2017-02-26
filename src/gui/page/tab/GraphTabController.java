package gui.page.tab;

import gui.PageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import model.Service;

import java.time.LocalDate;

public class GraphTabController {

    private PageController pageController = PageController.getInstance();
    private Service service;

    @FXML
    private Button buttonNextDay, buttonPreviousDay, buttonPrint;
    @FXML
    private DatePicker datePickerSelectedDay;
    @FXML
    private LineChart lineChartGraph;

    @FXML
    private void initialize(){
        this.service = pageController.getUser().getService();
        datePickerSelectedDay.setValue( LocalDate.now() );
        XYChart.Series<Integer, String> series = new XYChart.Series();
        series.setData( service.getStatusHistory().chartData() );
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
