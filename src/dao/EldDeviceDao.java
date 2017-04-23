package dao;

import domain.model.EldDevice;

import java.util.List;

public interface EldDeviceDao {
    void add(EldDevice eldDevice) throws DaoException;
    void remove(EldDevice eldDevice) throws DaoException;
    EldDevice get(int id) throws DaoException;
    List<EldDevice> getAll() throws DaoException;
}
