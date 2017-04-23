package dao;

import domain.model.HosExemption;

import java.util.List;

public interface HosExemptionDao {
    void add(HosExemption hosExemption) throws DaoException;
    void update(HosExemption hosExemption) throws DaoException;
    void remove(HosExemption hosExemption) throws DaoException;
    HosExemption get(int id) throws DaoException;
    List<HosExemption> getAll() throws DaoException;
}
