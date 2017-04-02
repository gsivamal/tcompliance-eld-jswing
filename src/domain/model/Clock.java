package domain.model;

import javafx.beans.property.SimpleObjectProperty;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Clock {

    private static SimpleObjectProperty<LocalDateTime> currentTime = new SimpleObjectProperty<>();
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
    public static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern( "MM/dd/yyyy" );

    private Clock(){

    }

    public static SimpleObjectProperty<LocalDateTime> currentTimeProperty(){
        return currentTime;
    }

    public static String localDateTimeToString(LocalDateTime dateTime) {
        return dateTime.format( formatter );
    }

    public static String localDateToString(LocalDate localDate) {
        return localDate.format( dateFormatter );
    }

    public static String durationToString(Duration duration) {
        long seconds = duration.getSeconds();
        return String.format("%d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, (seconds % 60));
    }

    public static void setCurrentTime(LocalDateTime localDateTime) {
        currentTime.set( localDateTime );
    }
}
