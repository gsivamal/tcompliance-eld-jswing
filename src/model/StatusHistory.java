package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Iterator;

public class StatusHistory implements Iterable<Status>{

    private ObservableList<Status> statuses;

    public StatusHistory(){
        statuses = FXCollections.observableArrayList();
    }

    public void addStatus(Status status){
        statuses.add( status );
    }

    public void removeStatus(Status status){
        statuses.remove( status );
    }

    public void removeStatus(int index) {
        statuses.remove( index );
    }

    public ObservableList<Status> observableList(){
        return statuses;
    }

    public Iterator<Status> iterator(){
        return statuses.iterator();
    }

    public Status get(int index) {
        return statuses.get( index );
    }

    public int size(){
        return statuses.size();
    }
}
