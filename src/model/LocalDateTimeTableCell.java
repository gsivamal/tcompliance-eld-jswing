package model;

import javafx.scene.control.TableCell;

import java.time.LocalDateTime;

public class LocalDateTimeTableCell<T> extends TableCell<T, LocalDateTime> {
    @Override
    protected void updateItem(LocalDateTime item, boolean empty) {
        super.updateItem( item, empty );
        if ( empty ) {
            setText( null );
        }else{
            setText( Clock.localDateTimeToString( item ) );
        }
    }
}
