package model.factory;

import model.Status;

import java.time.LocalDateTime;

public class StatusFactory extends Factory{

    private static final StatusFactory instance = new StatusFactory();

    private StatusFactory(){}

    public static StatusFactory getInstance() {
        return instance;
    }

    public Status createStatus(Integer id, Status.StatusValue statusValue, LocalDateTime startTime, LocalDateTime endTime) {
        return new Status( id, statusValue, startTime, endTime );
    }

    public Status createStatus(Status.StatusValue statusValue, LocalDateTime startTime) {
        try {
            incrementCount();
            return createStatus( getCount(), statusValue, startTime, startTime );
        } catch (Exception e) {
            decrementCount();
            throw e;
        }
    }

    public Status createStatus(Status.StatusValue statusValue) {
        return createStatus( statusValue, LocalDateTime.now() );
    }
}
