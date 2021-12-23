package persistence;

import domain.Article;
import domain.BuyerAccount;
import domain.Order;
import domain.SellerAccount;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.SortedSet;

public interface OrderRepositroy {
    List<Article> findAllInformationFromThisOrder(int id) throws SQLException;


    Order buy(BuyerAccount kunde) throws SQLException;

    List<Order> findAllFromThisBuyer(BuyerAccount buyerAccount) throws SQLException;

    List<Order>  findAllFromThisSeller(SellerAccount sellerAccount) throws SQLException;
    SortedSet<Order> show_deliveries_for_buyer(Order bestellung) throws SQLException;
}
