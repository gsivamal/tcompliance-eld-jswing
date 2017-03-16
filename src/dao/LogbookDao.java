package dao;

import model.Logbook;
import model.LogbookList;

public interface LogbookDao extends Dao<Logbook, LogbookList>{
    LogbookList getAllByDriverID(int driverID);
}
