package persistence;

import domain.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.SortedSet;
import java.util.TreeSet;

public record JdbcOrderArticleRepository(Connection connection) implements OrderArticelRepository {
    @Override
    public void delete(Integer article_id) throws SQLException {
        var sql ="delete  from bestellungsArtikel " +
                " where bA_art_id = ? ";

        try (var statement = connection.prepareStatement(sql))
        {
            statement.setInt(1, article_id);
            statement.executeUpdate();
        }
    }

    @Override
    public SortedSet<OrderArticel> getAllOrderArticleFromThisBuyer(BuyerAccount buyerAccount) throws SQLException {
        var sql = "select * from bestellungsArtikel " +
                "inner join artikel " +
                "on bestelleungsArtikel.bA_art_id = artikel.art_id" +
                " inner join verkaeuferAccount " +
                " on  verkaeuferAcoount.vA_id = artikel.art_vA_id " +
                "inner join kategorie" +
                " on artikel.art_kat_id = kategorie.kat_id " +
                " inner join bestellung " +
                " on bestellung.bes_id = bestellungsArtikel.bA_bes_id" +
                " inner join kundeAccount" +
                " on kundeAccount.kA_id = bestellung.bes_kA_id  " +
                " where kundeAccount.kA_id = ?";
        SortedSet<OrderArticel> orderArticelSortedSet = new TreeSet<>();
        try (var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, buyerAccount.getId());

            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                orderArticelSortedSet.add(new OrderArticel(resultSet.getInt("bestellungsArtikel.bA_id"),
                        new Article(resultSet.getInt("art_id"), resultSet.getString("art_name"),
                                resultSet.getDouble("art_price"), resultSet.getString("art_Description"),
                                new SellerAccount(resultSet.getInt("vA_id"), resultSet.getString("verkaeuferAccount.username"),
                                        resultSet.getString("verkaeuferAccount.kennwort"), resultSet.getString("verkaeuferAccount.sitz")),
                                new Sorts(resultSet.getInt("kat_id"), resultSet.getString("kat_name"))),
                        new Order(resultSet.getInt("bA_bes_id"),
                                new BuyerAccount(resultSet.getInt("bestellung.bes_kA_id"), resultSet.getString("kundeAccount.username"),
                                        resultSet.getString("kundeAccount.kennwort")))));
            }
        }

        return orderArticelSortedSet;
    }

    @Override
    public OrderArticel save(OrderArticel orderArticle) throws SQLException {
        var sql = "insert into bestellungsArtikel(bA_art_id,bA_bes_id) values (?,?)";

        try (var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, orderArticle.getArticle().getId());
            statement.setInt(2, orderArticle.getOrder().getId());
            statement.executeUpdate();

            orderArticle = new OrderArticel(statement.getGeneratedKeys().getInt(1),
                    orderArticle.getArticle(),
                    orderArticle.getOrder());

        }
        return orderArticle;
    }

    @Override
    public SortedSet<OrderArticel> show_order_for_seller(SellerAccount sellerAccount) throws SQLException {
        var sql = "select * from bestellungsArtikel " +
                "inner join artikel " +
                "on bestelleungsArtikel.bA_art_id = artikel.art_id" +
                " inner join verkaeuferAccount " +
                " on  verkaeuferAcoount.vA_id = artikel.art_vA_id " +
                "inner join kategorie" +
                " on artikel.art_kat_id = kategorie.kat_id " +
                " inner join bestellung " +
                " on bestellung.bes_id = bestellungsArtikel.bA_bes_id" +
                " inner join kundeAccount" +
                " on kundeAccount.kA_id = bestellung.bes_kA_id  " +
                " where verkaueferAccount.vA_id = ?";
        SortedSet<OrderArticel> orderArticelSortedSet = new TreeSet<>();
        try (var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, sellerAccount.getId());

            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                orderArticelSortedSet.add(new OrderArticel(resultSet.getInt("bestellungsArtikel.bA_id"),
                        new Article(resultSet.getInt("art_id"), resultSet.getString("art_name"),
                                resultSet.getDouble("art_price"), resultSet.getString("art_Description"),
                                new SellerAccount(resultSet.getInt("vA_id"), resultSet.getString("verkaeuferAccount.username"),
                                        resultSet.getString("verkaeuferAccount.kennwort"), resultSet.getString("verkaeuferAccount.sitz")),
                                new Sorts(resultSet.getInt("kat_id"), resultSet.getString("kat_name"))),
                        new Order(resultSet.getInt("bA_bes_id"),
                                new BuyerAccount(resultSet.getInt("bestellung.bes_kA_id"), resultSet.getString("kundeAccount.username"),
                                        resultSet.getString("kundeAccount.kennwort")))));
            }
        }

        return orderArticelSortedSet;
    }
}
