package persistence;

import domain.SellerAccount;

import java.sql.SQLException;
import java.util.List;

public interface SellerRepository {
    SellerAccount save(SellerAccount buyer) throws SQLException;
    void delete(SellerAccount buyer) throws SQLException;
    List<SellerAccount> findAll() throws SQLException;
    SellerAccount findById(Integer id) throws SQLException;
}
