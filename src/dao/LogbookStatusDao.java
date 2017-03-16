package dao;

import model.LogbookStatus;

public interface LogbookStatusDao {
    void add(LogbookStatus status);
    void remove(LogbookStatus status);
    void removeAll();
}
