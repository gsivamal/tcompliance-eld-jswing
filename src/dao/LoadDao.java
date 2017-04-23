package dao;

import domain.model.Load;

import java.sql.SQLException;
import java.util.List;

public interface LoadDao extends Dao<Load, List<Load>> {

    public Load getLatestOpenLoad() throws SQLException;

}
