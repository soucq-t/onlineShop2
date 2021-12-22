package persistence;

import domain.Article;
import domain.BuyerAccount;
import domain.CartArticle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.SortedSet;

public interface CartArticleRepository {
    ResultSet getAllInformationFromThisArticle(int id) throws SQLException;

    CartArticle add_to_basket(Article artikel, BuyerAccount kundeAccount) throws SQLException;

    void delete_from_basket(Article artikel, BuyerAccount kundeAccount) throws SQLException;

    SortedSet<CartArticle> show_basket(Article artikel, BuyerAccount kunde) throws SQLException;
}
