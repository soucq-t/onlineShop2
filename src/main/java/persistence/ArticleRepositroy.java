package persistence;

import domain.Article;

import java.sql.SQLException;
import java.util.List;
import java.util.SortedSet;

public interface ArticleRepositroy {


    Article insert_into_store(Article artikel) throws SQLException;

    void delete_from_store(Article artikel) throws SQLException;

    void update(Article artikel) throws SQLException;

    SortedSet<Article> find_by_name(String name) throws SQLException;

    List<Article> return_articles_by_category(String kategorie_name) throws SQLException;
}
