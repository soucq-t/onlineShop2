package persistence;

import domain.Article;
import domain.BuyerAccount;
import domain.CartArticle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.SortedSet;

public record JdbcCartArticleRepository(Connection connection) implements CartArticleRepository {
    @Override
    public ResultSet getAllInformationFromThisArticle(int id) throws SQLException {
        return null;
    }

    @Override
    public CartArticle add_to_basket(Article artikel, BuyerAccount kundeAccount) throws SQLException {
        var sql = "Insert into warenKorbArtikel(war_art_id,war_kA_id) values (?,?)";
        CartArticle warenkorbArtikel = null;
        try (var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, artikel.getId());
            statement.setInt(2, kundeAccount.getId());
            statement.executeUpdate();
            warenkorbArtikel = new CartArticle(statement.getGeneratedKeys().getInt(1),
                    artikel.getId(), kundeAccount.getId());
        }
        return warenkorbArtikel;
    }

    @Override
    public void delete_from_basket(Article artikel, BuyerAccount kundeAccount) throws SQLException {

    }

    @Override
    public SortedSet<CartArticle> show_basket(Article artikel, BuyerAccount kunde) throws SQLException {
        return null;
    }
}
