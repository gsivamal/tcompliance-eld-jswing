package domain.model;

import domain.mediator.PDFGeneration;
import domain.mediator.easytable.Cell;
import domain.mediator.easytable.Table;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
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

    public static String [] getHeaders(){
        String[] headers = {"Date", "Driving", "On-Duty", "Mileage", "Total"};
        return headers;
    }

    public static String[][] getContent(List<Recap> recapList) {
        String[][] content = new String[recapList.size()][getHeaders().length];
        for (int a = 0; a < recapList.size(); a++) {
            Recap recap = recapList.get( a );
            content[a][0] = Clock.localDateToString( recap.getDate() );
            content[a][1] = Clock.durationToString( recap.getDrivingDuration() );
            content[a][2] = Clock.durationToString( recap.getOnDutyDuration() );
            content[a][3] = String.valueOf( recap.getMileage() );
            content[a][4] = Clock.durationToString( recap.getTotalDuration() );
        }
        return content;
    }

    public static Table drawRecapTable(List<Recap> recapList) throws IOException{
        int[] colWidths = {160, 100, 100, 80, 100};
        String [] headers = RecapList.getHeaders();
        String[][] content = RecapList.getContent( recapList );
        Table table = PDFGeneration.drawTable( colWidths, headers, content, Cell.HorizontalAlignment.CENTER );
        return table;
    }
}
