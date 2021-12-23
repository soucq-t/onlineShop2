package persistence;

import domain.SellerAccount;
import domain.Sorts;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public record JdbcSortsRepository(Connection connection) implements SortsRepositroy{
    @Override
    public List<Sorts> findAll() throws SQLException {
        String sql = """
                select *
                from kategorie
                """;
        try (var statement = connection.prepareStatement(sql)) {
            var resultSet = statement.executeQuery();
            List<Sorts> list = new LinkedList<>();
            while (resultSet.next()) {
                list.add(new Sorts(resultSet.getInt("kat_id"),resultSet.getString("kat_name")));
            }
            return list;
        }
    }

    @Override
    public Sorts save(Sorts sorts) throws SQLException {
        return null;
    }

    @Override
    public void delete(int id) throws SQLException {

    }
}
