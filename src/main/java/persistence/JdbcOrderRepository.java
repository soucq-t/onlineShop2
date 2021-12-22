package persistence;

import domain.BuyerAccount;
import domain.Order;
import domain.SellerAccount;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.SortedSet;
import java.util.TreeSet;

public record JdbcOrderRepository(Connection connection) implements OrderRepositroy {
    @Override
    public ResultSet findAllInformationFromThisOrder(int id) throws SQLException {
        var sql = "select * from Order where bes_id = ? ";
        SortedSet<Order> OrderSortedSet = new TreeSet<>();
        ResultSet resultSet;
        try (var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

        }
        return resultSet;
    }

    @Override
    public Order buy(BuyerAccount kunde) throws SQLException {
        //var sql_show_basket = "select * from warenKorbArtikel where war_kA_id = ?";
        var sql_insert = "insert into bestellung (bes_kA_id) values(?)";

        Order order = null;
        try (var statement = connection.prepareStatement(sql_insert, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, kunde.getId());
            statement.executeUpdate();
            var resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {

                order = new Order(resultSet.getInt(1),kunde);

            }


        }



        return order;
    }

    @Override
    public SortedSet<Order> findAllFromThisBuyer(BuyerAccount buyerAccount) throws SQLException {
        var sql = "select * from bestellung " +
                "inner join kundeAccount " +
                "on kundeAccount.kA_id = bestellung.bes_kA_id" +
                " where bes_kA_id = ? ";
        SortedSet<Order> bestellungSortedSet = new TreeSet<>();
        try (var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, buyerAccount.getId());
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                bestellungSortedSet.add(new Order(resultSet.getInt("bes_id"),
                        new BuyerAccount(resultSet.getInt("bes_kA_id"),
                                resultSet.getString("username"),
                                resultSet.getString("kennwort"))));
            }
        }
        return bestellungSortedSet;
    }

    @Override
    public SortedSet<Order> findAllFromThisSeller(SellerAccount sellerAccount) throws SQLException {
        var sql = "select * from bestellung " +
                "inner join bestellungsArtikel " +
                "on bestellungsArtikel.bA_bes_id = bestellung.bes_id" +
                "inner join artikel " +
                "on bestelleungsArtikel.bA_art_id = artikel.art_id" +
                "inner join verkaeuferAccount " +
                "on  verkaeuferAcoount.vA_id = artikel.art_vA_id " +
                "inner join kategorie" +
                "               on artikel.art_kat_id = kategorie.kat_id " +
                "              inner join bestellung " +
                "               on bestellung.bes_id = bestellungsArtikel.bA_bes_id" +
                "              inner join kundeAccount" +
                "                on kundeAccount.kA_id = bestellung.bes_kA_id  " +
                "where verkaeuferAccount = ? ";
        SortedSet<Order> bestellungSortedSet = new TreeSet<>();
        try (var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, sellerAccount.getId());
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                bestellungSortedSet.add(new Order(resultSet.getInt("bes_id"),
                        new BuyerAccount(resultSet.getInt("bes_kA_id"),
                                resultSet.getString("username"),
                                resultSet.getString("kennwort"))));
            }
        }
        return bestellungSortedSet;
    }

    @Override
    public SortedSet<Order> show_deliveries_for_buyer(Order order) throws SQLException {
        var sql = "select * from bestellung " +
                "inner join kundeAccount " +
                "on kundeAccount.kA_id = bestellung.bes_kA_id" +
                " where bes_id = ? ";
        SortedSet<Order> bestellungSortedSet = new TreeSet<>();
        try (var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, order.getId());
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                bestellungSortedSet.add(new Order(resultSet.getInt("bes_id"),
                        new BuyerAccount(resultSet.getInt("bes_kA_id"),
                                resultSet.getString("username"),
                                resultSet.getString("kennwort"))));
            }
        }
        return bestellungSortedSet;
    }
}
