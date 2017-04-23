package dao;

import domain.model.CompanySettings;

import java.util.List;

public interface CompanySettingsDao {
    void add(CompanySettings companySettings) throws DaoException;
    void update(CompanySettings companySettings) throws DaoException;
    void remove(CompanySettings companySettings) throws DaoException;
    CompanySettings get(int id) throws DaoException;
    List<CompanySettings> getAll() throws DaoException;

}
