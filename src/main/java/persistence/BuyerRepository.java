package persistence;

import domain.BuyerAccount;

import java.sql.SQLException;
import java.util.List;

public interface BuyerRepository {
    BuyerAccount findById(int id) throws SQLException;
    List<BuyerAccount> findAll() throws SQLException;
    BuyerAccount save(BuyerAccount buyer) throws SQLException;
    void delete(BuyerAccount buyer) throws SQLException;
}
