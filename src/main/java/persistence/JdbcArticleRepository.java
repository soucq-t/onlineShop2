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
    public Article insert_into_store(Article article) throws SQLException {
        System.out.println("okkokoko"+article.toString());

        var sql = "insert into artikel(art_name,art_price,art_Description,art_kat_id,art_vA_id) values (?,?,?,?,?)";

        System.out.println(article.getSort().getId());
        try (var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, article.getName());
            statement.setDouble(2, article.getPrice());
            statement.setString(3, article.getDescription());
            statement.setInt(4, article.getSort().getId());
            statement.setInt(5, article.getSeller().getId());
            statement.executeUpdate();


            var resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                article = new Article(resultSet.getInt(1), article.getName(), article.getPrice(),
                        article.getDescription(), article.getSeller(), article.getSort());
            }
        }

        return article;

    }

    @Override
    public void delete_from_store(Article Article) throws SQLException {

        var sql = "delete from artikel " +
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
    public SortedSet<Article> find_by_Name(Integer id) throws SQLException {
        SortedSet<Article> Article_set = new TreeSet<>();
        var sql = "select * from artikel " +
                " inner join verkaeuferAccount " +
                " on verkaeuferAccount.vA_id = artikel.art_vA_id " +
                " inner join kategorie " +
                " on kategorie.kat_id = artikel.art_vA_id " +
                " where art_id = ?";

        try (var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
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
        var sql = "select * from artikel " +
                " inner join kategorie " +
                " on kategorie.kat_id = artikel.art_kat_id" +
                " inner join verkaeuferAccount " +
                " on verkaeuferAccount.vA_id = artikel.art_vA_id " +
                " where art_kat_id = ?  ";


        try (var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, ka.intValue());
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ArticleList.add(new Article(resultSet.getInt("art_id"), resultSet.getString("art_name"),
                        resultSet.getDouble("art_price"), resultSet.getString("art_Description")
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
                " inner join verkaeuferAccount " +
                " on verkaeuferAccount.vA_id = artikel.art_vA_id " +
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
