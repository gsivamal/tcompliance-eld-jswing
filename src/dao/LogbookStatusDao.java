package dao;

import domain.model.LogbookStatus;

public interface LogbookStatusDao {
    void add(LogbookStatus status);
    void remove(LogbookStatus status);
    void removeAll();
}
