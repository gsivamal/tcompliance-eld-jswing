package domain.model;

import javafx.scene.control.TableCell;

import java.time.Duration;

public class DurationTableCell<T> extends TableCell<T, Duration>{

    @Override
    protected void updateItem(Duration item, boolean empty) {
        super.updateItem( item, empty );
        if ( empty ) {
            setText( null );
        }else{
            setText( Clock.durationToString( item ) );
        }
    }
}
