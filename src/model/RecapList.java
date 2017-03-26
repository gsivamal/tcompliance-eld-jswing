package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class RecapList extends SimpleListProperty<Recap>{

    private ObjectProperty<Duration> totalDuration = new SimpleObjectProperty<>();

    public RecapList(){
        super( FXCollections.observableArrayList() );
        totalDuration.set( Duration.ZERO );
    }

    public RecapList(ObservableList<Logbook> logbookList) {
        this();
        Map<LocalDate, Recap> recapMap = new HashMap<>();
        for (Logbook logbook : logbookList) {
            LocalDate localDate = logbook.getStartTime().toLocalDate();
            Recap recap = recapMap.getOrDefault( localDate, new Recap( localDate, Duration.ZERO, Duration.ZERO, 0 ) );
            recap.addMileage( logbook.getMileage() );
            if ( logbook.getDutyStatus().equals( DutyStatus.DRV ) ) {
                recap.addDrivingDuration( logbook.getDuration() );
            }else{
                recap.addOnDutyDuration( logbook.getDuration() );
            }
            if ( !recapMap.containsKey( localDate ) ) {
                recapMap.put( localDate, recap );
            }
        }
        for (Recap recap : recapMap.values()) {
            add( recap );
            setTotalDuration( getTotalDuration().plus( recap.getTotalDuration() ) );
        }
        recapMap = null;
    }

    public Duration getTotalDuration() {
        return totalDuration.get();
    }

    public ObjectProperty<Duration> totalDurationProperty() {
        return totalDuration;
    }

    public void setTotalDuration(Duration totalDuration) {
        this.totalDuration.set( totalDuration );
    }
}
