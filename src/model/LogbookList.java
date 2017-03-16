package model;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.time.LocalDateTime;

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
}
