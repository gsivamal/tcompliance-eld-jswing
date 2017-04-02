package domain.model;

import javafx.scene.control.TableCell;

import java.time.LocalDate;

public class LocalDateTableCell<T> extends TableCell<T, LocalDate> {

    @Override
    protected void updateItem(LocalDate item, boolean empty) {
        super.updateItem( item, empty );
        if ( empty ) {
            setText( null );
        }else{
            setText( Clock.localDateToString( item ) );
        }
    }
}
