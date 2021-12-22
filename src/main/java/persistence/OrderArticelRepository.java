package persistence;

import domain.BuyerAccount;
import domain.CartArticle;
import domain.OrderArticel;
import domain.SellerAccount;

import java.sql.SQLException;
import java.util.List;
import java.util.SortedSet;

public interface OrderArticelRepository {

    void delete(Integer article_id) throws SQLException;


    OrderArticel save(OrderArticel bestellungsArtikel) throws SQLException;

    SortedSet<OrderArticel> getAllOrderArticleFromThisBuyer(BuyerAccount buyerAccount) throws SQLException;

    SortedSet<OrderArticel> show_order_for_seller(SellerAccount verkaeuferAccount) throws SQLException;

}
