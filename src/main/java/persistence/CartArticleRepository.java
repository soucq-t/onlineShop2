package persistence;

import domain.Article;
import domain.CartArticle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface CartArticleRepository {
    ResultSet getAllInformationFromThisArticle(int id) throws SQLException;
    List<CartArticle> getAllCartArticle() throws SQLException;
    CartArticle save(CartArticle article) throws  SQLException;
    void delete(int id) throws SQLException;
}
