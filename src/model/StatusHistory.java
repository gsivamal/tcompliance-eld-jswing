package model;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.util.Iterator;

public class StatusHistory implements Iterable<Status>{

    private ObservableList<Status> statuses;
    private  ObservableList<XYChart.Data<Integer, String>> chartData = FXCollections.observableArrayList();

    public StatusHistory(){
        statuses = FXCollections.observableArrayList();
        statuses.addListener( (ListChangeListener<Status>)listener -> {
            while ( listener.next() ) {
                for(Status addedStatus : listener.getAddedSubList()){
                    chartData.add( new XYChart.Data<>( size(), addedStatus.getStatusValue().toString() ) );
                }
            }
        } );
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

    public ObservableList<XYChart.Data<Integer, String>> chartData(){
        return chartData;
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
