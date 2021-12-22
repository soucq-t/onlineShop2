package persistence;

import domain.Account;
import domain.BuyerAccount;

import java.sql.SQLException;
import java.util.List;

public interface BuyerRepository {
    Account findById(int id) throws SQLException;
    List<Account> findAll() throws SQLException;
    Account save(Account buyer) throws SQLException;
    void delete(Account buyer) throws SQLException;
}
