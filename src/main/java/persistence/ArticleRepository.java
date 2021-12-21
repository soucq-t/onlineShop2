package persistence;

import domain.Article;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public record ArticleRepository(Connection connection) implements ArticleRepositroy{
    @Override
    public Article findById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Article> getAllArticle() throws SQLException {
        return null;
    }

    @Override
    public Article saveArticle(Article article) throws SQLException {
        return null;
    }

    @Override
    public void deleteArticle(int id) throws SQLException {

    }
}
