package dao;

import domain.model.Logbook;
import domain.model.LogbookList;

public interface LogbookDao extends Dao<Logbook, LogbookList>{
    LogbookList getAllByDriverID(int driverID);
}
