package persistence;

import domain.BuyerAccount;
import domain.SellerAccount;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public record JdbcSellerRepository(Connection connection) implements SellerRepository {
    @Override
    public SellerAccount findById(Integer id) throws SQLException {
        String sql = """
                select *
                from verkaeuferAccount
                where vA_id=?
                """;
        SellerAccount sellerAccount = null;
        try (var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                sellerAccount = new SellerAccount(resultSet.getInt("vA_id"), resultSet.getString("username"), resultSet.getString("kennwort"), resultSet.getString("sitz"));
                return sellerAccount;
            }
        }

        return sellerAccount;
    }

    @Override
    public List<SellerAccount> findAll() throws SQLException {
        String sql = """
                select *
                from verkaeuferAccount
                """;
        try (var statement = connection.prepareStatement(sql)) {
            var resultSet = statement.executeQuery();
            List<SellerAccount> list = new LinkedList<>();
            while (resultSet.next()) {
                list.add(new SellerAccount(resultSet.getInt("vA_id"), resultSet.getString("username"), resultSet.getString("kennwort"), resultSet.getString("sitz")));
            }
            return list;
        }
    }

    @Override
    public SellerAccount save(SellerAccount seller) throws SQLException {
        SellerAccount sellerAccount = null;
        var sql = "Insert into verkaeuferAccount(username,kennwort,Lieferadresse) values (?,?,?)";
        try (var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, seller.getUsername());
            statement.setString(2, seller.getKennwort());
            System.out.println("dawdawdwa");
            statement.setString(3, "Moskau strasse 1");
            statement.executeUpdate();
            System.out.println("dawdwad");
            System.out.println("dawdaw" + seller.toString());
            if (statement.getGeneratedKeys().next()) {

                sellerAccount = new SellerAccount(statement.getGeneratedKeys().getInt("vA_id"), statement.getGeneratedKeys().getString("username"), statement.getGeneratedKeys().getString("kennwort"), statement.getGeneratedKeys().getString("sitz"));
                return sellerAccount;
            }
        }
        return sellerAccount;
    }

    @Override
    public void delete(SellerAccount buyer) throws SQLException {

    }


}
