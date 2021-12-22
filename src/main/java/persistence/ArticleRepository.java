package persistence;

import domain.Article;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.SortedSet;

public record ArticleRepository(Connection connection) implements ArticleRepositroy{

    @Override
    public Article insert_into_store(Article artikel) throws SQLException {
        return null;
    }

    @Override
    public void delete_from_store(Article artikel) throws SQLException {

    }

    @Override
    public void update(Article artikel) throws SQLException {

    }

    @Override
    public SortedSet<Article> find_by_name(String name) throws SQLException {
        return null;
    }

    @Override
    public List<Article> return_articles_by_category(String kategorie_name) throws SQLException {
        return null;
    }
}
