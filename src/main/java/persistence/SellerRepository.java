package persistence;

import domain.SellerAccount;

import java.sql.SQLException;

public interface SellerRepository {
    SellerAccount save(SellerAccount buyer) throws SQLException;
    void delete(SellerAccount buyer) throws SQLException;
}
