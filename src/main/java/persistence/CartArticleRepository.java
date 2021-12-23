package persistence;

import domain.Article;
import domain.BuyerAccount;
import domain.CartArticle;
import domain.OrderArticel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.SortedSet;

public interface CartArticleRepository {
    ResultSet getAllInformationFromThisArticle(int id) throws SQLException;

    CartArticle add_to_basket(CartArticle cartArticle) throws SQLException;

    void delete(Integer artikel) throws SQLException;


    List<CartArticle> getAllCartArticleFromThisBuyer(BuyerAccount buyerAccount) throws SQLException;



}
