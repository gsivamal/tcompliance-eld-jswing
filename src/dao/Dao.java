package dao;

import java.sql.SQLException;

public interface Dao<T,L> {
    void add(T item) throws SQLException;
    void update(T item) throws SQLException;
    void remove(T item) throws SQLException;
    T get(int id) throws SQLException;
    L getAll() throws SQLException;
    int getLastID() throws SQLException;
}
