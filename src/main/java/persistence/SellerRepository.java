package persistence;

import domain.Account;

import java.sql.SQLException;

public interface SellerRepository {
    Account save(Account buyer) throws SQLException;
    void delete(Account buyer) throws SQLException;
}
