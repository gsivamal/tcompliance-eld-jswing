package dao;

import model.StatusHistory;

public interface StatusDao {

    StatusHistory getAllService(int serviceID);
}
