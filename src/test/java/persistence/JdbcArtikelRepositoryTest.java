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
    private SellerRepository sellerRepository;
    private SortsRepositroy sortsRepositroy;
    private Connection connection;

    @BeforeEach
    void createRepository() throws SQLException {

        connection = connectionSupplier.getConnection();
        articleRepository = new JdbcArticleRepository(connection);
        sellerRepository = new JdbcSellerRepository(connection);
        sortsRepositroy = new JdbcSortsRepository(connection);

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
                    new Sorts(1, "cool"));
            article = articleRepository.insert_into_store(article);

            assertTrue(articleRepository.find_by_Name(article.getId()).contains(article));
        }

    }

    @Nested
    class findAllfromThisSeller {
        @Test
        void find_article() throws SQLException {
            var sellerAccount = new SellerAccount(3, "joe", "jo", "st.pölten");
            var article = new Article("Stift",
                    3.2,
                    "ff",
                    sellerAccount,
                    new Sorts(1, "cool"));
            article = articleRepository.insert_into_store(article);
            SortedSet<Article> articleSortedSet = articleRepository.findAllfromThisSeller(sellerAccount);
            assertTrue(articleSortedSet.contains(article));
        }

    }

    @Nested
    class delete_from_store {
        @Test
        void deleteThisArticle() throws SQLException {
            var article = new Article("Stift",
                    3.2,
                    "ff",
                    sellerRepository.findById(1),
                    sortsRepositroy.findByID(1)
            );
            var savedArticle = articleRepository.insert_into_store(article);
            articleRepository.delete_from_store(savedArticle);
            assertTrue(articleRepository.find_by_Name(savedArticle.getId()).isEmpty());
        }
    }

    @Nested
    class return_articles_by_category {
        @Test
        void contains_article_in_this_category() throws SQLException {

            var article = new Article("Stift",
                    3.2,
                    "ff",
                    sellerRepository.findById(1),
                    sortsRepositroy.findByID(1));


            article = articleRepository.insert_into_store(article);

            assertTrue(articleRepository.return_articles_by_category(1).contains(article));

        }


    }

}
