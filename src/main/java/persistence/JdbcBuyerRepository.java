package persistence;

import domain.BuyerAccount;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public record JdbcBuyerRepository(Connection connection) implements BuyerRepository {
    @Override
    public BuyerAccount findById(int id) throws SQLException {
        String sql = """
                select *
                from kundeAccount
                where kA_id=?
                """;
        BuyerAccount buyerAccount = null;
        try (var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {

                buyerAccount = new BuyerAccount(resultSet.getInt("kA_id"), resultSet.getString("username"), resultSet.getString("kennwort"));
                return buyerAccount;
            }
        }

        return buyerAccount;
    }

    @Override
    public List<BuyerAccount> findAll() throws SQLException {
        String sql = """
                select *
                from kundeAccount
                """;
        try (var statement = connection.prepareStatement(sql)) {
            var resultSet = statement.executeQuery();
            List<BuyerAccount> list = new LinkedList<>();
            while (resultSet.next()) {
                list.add(new BuyerAccount(resultSet.getInt("kA_id"), resultSet.getString("username"), resultSet.getString("kennwort")));
            }
            return list;
        }
    }

    @Override
    public BuyerAccount save(BuyerAccount buyer) throws SQLException {
        var sql = "Insert into kundeAccount(username,kennwort,Lieferadresse) values (?,?,?)";
        BuyerAccount buyerAccount = null;
        try (var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, buyer.getUsername());
            statement.setString(2, buyer.getKennwort());
            statement.setString(3, "Moskau strasse 1");
            statement.executeUpdate();
            if (statement.getGeneratedKeys().next()) {
                buyerAccount = new BuyerAccount(statement.getGeneratedKeys().getInt("kA_id"),
                        statement.getGeneratedKeys().getString("username"),
                        statement.getGeneratedKeys().getString("kennwort"));
                return buyerAccount;

            }
        }
        return buyerAccount;
    }

    @Override
    public void delete(BuyerAccount buyer) throws SQLException {

    }
}
