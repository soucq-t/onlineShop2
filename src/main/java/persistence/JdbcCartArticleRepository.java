package persistence;

import domain.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.SortedSet;
import java.util.TreeSet;

public record JdbcCartArticleRepository(Connection connection) implements CartArticleRepository {
    @Override
    public ResultSet getAllInformationFromThisArticle(int id) throws SQLException {
        var sql = "select * from warenKorbArtikel" +
                " inner join artikel" +
                " on artikel.art_id = warenKorbArtikel.war_kA_id" +
                "where artikel.art_id = ?";
        ResultSet resultSet;
        try (var statement = connection.prepareStatement(sql))
        {
            statement.setInt(1,id);
            resultSet = statement.executeQuery();
        }
        return resultSet;
    }

    @Override
    public CartArticle add_to_basket(CartArticle cartArticle) throws SQLException {
        var sql = "Insert into warenKorbArtikel(war_art_id,war_kA_id) values (?,?)";
        CartArticle warenkorbArtikel = null;
        try (var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {


            statement.setInt(1, cartArticle.getArticle().getId());
            statement.setInt(2, cartArticle.getBuyer().getId());
            statement.executeUpdate();
            warenkorbArtikel = new CartArticle(statement.getGeneratedKeys().getInt(1),
                    cartArticle.getArticle(), cartArticle.getBuyer());
        }
        return warenkorbArtikel;
    }

    @Override
    public void delete(Integer artikel_id) throws SQLException {
        var sql = "delete from warenKorbArtikel where war_art_id = ?";
        try (var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, artikel_id);
            statement.executeUpdate();
        }
    }

    @Override
    public SortedSet<CartArticle> getAllCartArticleFromThisBuyer(BuyerAccount buyerAccount) throws SQLException {
        var sql = "select * from warenKorbArtikel" +
                " inner join artikel" +
                " on warenKorbArtikel.war_kA_id = artikel.art_id " +
                " where wKA_id = ?";
        SortedSet<CartArticle> warenkorbArtikelSortedSet = new TreeSet<>();
        try (var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, buyerAccount.getId());
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                warenkorbArtikelSortedSet.add(new CartArticle(resultSet.getInt("wKA_id"),
                        new Article(resultSet.getInt("art_id"), resultSet.getString("art_name"),
                                resultSet.getDouble("art_price"), resultSet.getString("art_Description"),
                                new SellerAccount(resultSet.getInt("vA_id"), resultSet.getString("verkaeuferAccount.username"),
                                        resultSet.getString("verkaeuferAccount.kennwort"), resultSet.getString("verkaeuferAccount.sitz")),
                                new Sorts(resultSet.getInt("kat_id"), resultSet.getString("kat_name"))),
                        buyerAccount));
            }
        }
        return warenkorbArtikelSortedSet;
    }




}
