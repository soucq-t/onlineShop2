package persistence;

import domain.Article;
import domain.SellerAccount;
import domain.Sorts;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public record JdbcArticleRepository(Connection connection) implements ArticleRepositroy {
    @Override
    public Article insert_into_store(Article Article) throws SQLException {
        var sql = "insert into Article(art_name,art_price,art_Description,art_kat_id,art_vA_id) values (?,?,?,?,?)";

        try (var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, Article.getName());
            statement.setDouble(2, Article.getPrice());
            statement.setString(3, Article.getDescription());
            statement.setInt(4, Article.getSort().getId());
            statement.setInt(5, Article.getSeller().getId());
            statement.executeUpdate();

            var resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                Article = new Article(resultSet.getInt(1), Article.getName(), Article.getPrice(),
                        Article.getDescription(), Article.getSeller(), Article.getSort());
            }
        }

        return Article;

    }

    @Override
    public void delete_from_store(Article Article) throws SQLException {

        var sql = "delete from Article " +
                " where art_id = ?";

        try (var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, Article.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void update(Article Article) throws SQLException {

        var sql_update = "update Article " +
                " set art_name = ?, art_price = ?, art_Description, art_kat_id = ?, art_vA_id = ? " +
                " where art_id = ?";
        try (var statement = connection.prepareStatement(sql_update)) {
            statement.setString(1, Article.getName());
            statement.setDouble(2, Article.getPrice());
            statement.setString(3, Article.getDescription());
            statement.setInt(4, Article.getSort().getId());
            statement.setInt(5, Article.getSeller().getId());
            statement.executeUpdate();
        }
    }

    @Override
    public SortedSet<Article> find_by_name(String name) throws SQLException {
        SortedSet<Article> Article_set = new TreeSet<>();
        var sql = "select * from Article " +
                " inner join verkaueferAccount " +
                " on verkaueferAccount.vA_id = artikel.art_vA_id " +
                " inner join kategorie " +
                " on kategorie.kat_id = artikel.art_vA_id " +
                " where art_name like *?*";

        try (var statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Article_set.add(new Article(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getDouble(3), resultSet.getString(4)
                        , new SellerAccount(resultSet.getInt("art_vA_id"), resultSet.getString("username"),
                        resultSet.getString("kennwort"), resultSet.getString("sitz")),
                        new Sorts(resultSet.getInt("art_kat_id"), resultSet.getString("kat_name"))));
            }
        }

        return Article_set;
    }

    @Override
    public List<Article> return_articles_by_category(Integer ka) throws SQLException {
        List<Article> ArticleList = new LinkedList<>();
        var sql = "select * from Article " +
                " inner join verkaueferAccount " +
                " on verkaueferAccount.vA_id = artikel.art_vA_id " +
                " inner join kategorie " +
                " on kategorie.kat_id = artikel.art_vA_id " +
                " where art_kat_id = ?";


        try (var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, ka.intValue());
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ArticleList.add(new Article(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getDouble(3), resultSet.getString(4)
                        , new SellerAccount(resultSet.getInt("art_vA_id"), resultSet.getString("username"),
                        resultSet.getString("kennwort"), resultSet.getString("sitz")),
                        new Sorts(resultSet.getInt("art_kat_id"), resultSet.getString("kat_name"))));
            }
        }
        return ArticleList;

    }

    @Override
    public SortedSet<Article> findAllfromThisSeller(SellerAccount sellerAccount) throws SQLException {
        var sql = "select * from artikel" +
                " inner join verkaueferAccount " +
                " on verkaueferAccount.vA_id = artikel.art_vA_id " +
                " inner join kategorie " +
                " on kategorie.kat_id = artikel.art_vA_id " +
                " where art_vA_id = ?";

        SortedSet<Article> articleSortedSet = new TreeSet<>();
        try (var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, sellerAccount.getId());
            var resultSet = statement.executeQuery();

            while (resultSet.next()) {
                articleSortedSet.add(new Article(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getDouble(3), resultSet.getString(4)
                        , new SellerAccount(resultSet.getInt("art_vA_id"), resultSet.getString("username"),
                        resultSet.getString("kennwort"), resultSet.getString("sitz")),
                        new Sorts(resultSet.getInt("art_kat_id"), resultSet.getString("kat_name"))));
            }
        }
        return articleSortedSet;
    }

}
