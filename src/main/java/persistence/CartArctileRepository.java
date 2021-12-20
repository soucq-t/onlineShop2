package persistence;

import domain.Article;
import domain.CartArticle;

import java.sql.SQLException;
import java.util.List;

public interface CartArctileRepository {
    List<CartArticle> getAllCartArticle() throws SQLException;
    CartArticle save(CartArticle article) throws  SQLException;
    void delete(int id) throws SQLException;
}
