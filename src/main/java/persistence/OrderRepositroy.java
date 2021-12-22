package persistence;

import domain.BuyerAccount;
import domain.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.SortedSet;

public interface OrderRepositroy {
    ResultSet findAllInformationFromThisOrder(int id) throws SQLException;


    Order buy(BuyerAccount kunde) throws SQLException;

    SortedSet<Order> show_all_orders_for_buyer(BuyerAccount buyerAccount) throws SQLException;
    SortedSet<Order> show_deliveries_for_buyer(Order bestellung) throws SQLException;
}
