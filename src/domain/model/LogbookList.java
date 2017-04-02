package domain.model;

import domain.mediator.PDFGeneration;
import domain.mediator.easytable.Cell;
import domain.mediator.easytable.Table;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class LogbookList extends SimpleListProperty<Logbook> {

    private ObservableList<XYChart.Data<Integer, String>> chartData = FXCollections.observableArrayList();

    public LogbookList(){
        super( FXCollections.observableArrayList() );
        addListener( (ListChangeListener<Logbook>) listener -> {
            while ( listener.next() ) {
                for(Logbook addedStatus : listener.getAddedSubList()){
                    chartData.add( new XYChart.Data<>( size(), addedStatus.getDutyStatus().toString() ) );
                }
            }
        } );
    }

    public void changeStatus(LocalDateTime endTime, LogbookStatus logbookStatus, LocalDateTime approveTime, int mileage, String additionalInfo, String notes, Logbook newStatus) {
        Logbook last = last();
        last.setEndTime( endTime );
        last.setApprovedStatus( logbookStatus );
        last.setApprovedTime( approveTime );
        last.setMileage( mileage );
        last.setAdditionalInfo( additionalInfo );
        last.setNotes( notes );
        add( newStatus );
    }

    public Logbook last(){
        return get( size() - 1 );
    }

    public Logbook first(){
        return get( 0 );
    }

    public ObservableList<XYChart.Data<Integer, String>> chartData(){
        return chartData;
    }

    //Returns filtered logbook list.
    //Passes if logbook is between start date and end date(including both)
    public ObservableList<Logbook> getForDay(LocalDate localDate) {
        return filtered( logbook -> ( logbook.getStartTime().toLocalDate().isEqual( localDate )
                || ( localDate.isAfter( logbook.getStartTime().toLocalDate() ) && localDate.isBefore( logbook.getEndTime().toLocalDate() ) )
                || logbook.getEndTime().toLocalDate().isEqual( localDate ) ) );
    }

    public ObservableList<Logbook> recapBeforeDay(LocalDate localDate) {
        return filtered( logbook -> localDate.isAfter( logbook.getStartTime().toLocalDate() )
                && !logbook.dutyStatusProperty().get().equals( DutyStatus.OFF ) );
    }

    public static String [] getHeaders(){
        String[] headers = new String[5];
        headers[0] = "Duty";
        headers[1] = "Start";
        headers[2] = "Location";
        headers[3] = "Duration";
        headers[4] = "Notes";
        return headers;
    }

    public static String [][] getContent(List<Logbook> logbookList){
        String[][] content = new String[logbookList.size()][5];
        for (int a = 0; a < content.length; a++) {
            Logbook logbook = logbookList.get( a );
            content[a][0] = logbook.getDutyStatus().toString();
            content[a][1] = Clock.localDateTimeToString( logbook.getStartTime() );
            content[a][2] = logbook.getGpsLocation().getLocation();
            content[a][3] = Clock.durationToString( logbook.getDuration() );
            content[a][4] = logbook.getNotes();
        }
        return content;
    }

    public static Table drawLogbookTable(List<Logbook> logbookList) throws IOException{
        int[] colWidths = {50, 120, 100, 80, 180};
        String [] headers = LogbookList.getHeaders();
        String[][] content = LogbookList.getContent( logbookList );
        Table table = PDFGeneration.drawTable( colWidths, headers, content, Cell.HorizontalAlignment.CENTER );
        return table;
    }
}
