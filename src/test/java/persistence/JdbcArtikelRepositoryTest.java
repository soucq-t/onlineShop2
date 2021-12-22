package persistence;

import domain.Article;
import domain.BuyerAccount;
import domain.SellerAccount;
import domain.Sorts;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import persistence.setup.TestConnectionSupplier;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.SortedSet;

import static org.junit.Assert.*;

public class JdbcArtikelRepositoryTest {

    private final TestConnectionSupplier connectionSupplier = new TestConnectionSupplier();
    private ArticleRepositroy articleRepository;
    private Connection connection;

    @BeforeEach
    void createRepository() throws SQLException {

        connection = connectionSupplier.getConnection();
        articleRepository = new JdbcArticleRepository(connection);

    }

    @AfterEach
    void closeConnection() throws SQLException {
        connection.close();
    }

    @Nested
    class Insert {
        @Test
        void inserts_article() throws SQLException {
            var kunde = new BuyerAccount("bob", "passwort");
            var article = new Article("Stift",
                    3.2,
                    "ff",
                    new SellerAccount(1, "doofenshmirtz", "drowssap", "Utrecht, Niederlande"),
                    new Sorts(1,"cool"));
            article = articleRepository.insert_into_store(article);

            assertTrue(articleRepository.find_by_name("Stift").contains(article));
        }

    }

    @Nested
    class findAllfromThisSeller
    {
        @Test
         void find_article() throws SQLException
        {
            var sellerAccount = new SellerAccount(1,"joe","jo","st.pölten");
            var article = new Article("Stift",
                    3.2,
                    "ff",
                    new SellerAccount(1, "doofenshmirtz", "drowssap", "Utrecht, Niederlande"),
                    new Sorts(1,"cool"));
            article = articleRepository.insert_into_store(article);
            SortedSet<Article> articleSortedSet = articleRepository.findAllfromThisSeller(sellerAccount);
            assertTrue(articleSortedSet.contains(article));

        }
    }

}
