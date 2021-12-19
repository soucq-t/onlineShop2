package persistence;

import domain.Account;
import domain.BuyerAccount;

import java.sql.SQLException;

public interface BuyerRepository {
    Account save(Account buyer) throws SQLException;
    void delete(Account buyer) throws SQLException;
}
