package dao;

import domain.model.TruckCompany;

import java.util.List;

public interface TruckCompanyDao {
    void add(TruckCompany truckCompany) throws DaoException;
    void remove(TruckCompany truckCompany) throws DaoException;
    TruckCompany get(int id) throws DaoException;
    List<TruckCompany> getAll() throws DaoException;
}
