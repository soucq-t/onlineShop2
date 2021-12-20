package persistence;

import domain.Article;

import java.sql.SQLException;
import java.util.List;

public interface ArticleRepositroy {
    Article findById(int id) throws SQLException;
    List<Article> getAllArticle() throws SQLException;
    Article saveArticle(Article article) throws SQLException;
    void deleteArticle(int id) throws SQLException;
}
