package dao;

import domain.model.DutyStatus;

public interface DutyStatusDao {
    void add(DutyStatus status);
    void remove(DutyStatus status);
    void removeAll();
}
