package persistence;

import domain.OrderArticel;

import java.sql.SQLException;
import java.util.List;

public interface OrderArticelRepository {
    OrderArticel save(OrderArticel bestellungsArticel) throws SQLException;

    OrderArticel findById(int id) throws SQLException;

    List<OrderArticel> findAll() throws SQLException;
}
