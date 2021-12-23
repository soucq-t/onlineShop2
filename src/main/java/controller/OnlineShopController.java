package controller;

import domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import persistence.*;

import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class OnlineShopController implements Initializable {

    @FXML
    private TabPane tabFenster;

    @FXML
    private Tab tabKaeufer;

    @FXML
    private ListView<CartArticle> lvCartArticles;

    @FXML
    private ChoiceBox<Sorts> cbKategorie;

    @FXML
    private ListView<Article> lvArticles;

    @FXML
    private Button btnAddToCart;

    @FXML
    private Button btnDeleteCartArticle;

    @FXML
    private Button btnPayCartArticle;

    @FXML
    private Button btnCartArticleDetails;

    @FXML
    private TextField tfAdresse;

    @FXML
    private ListView<Order> lvOrders;

    @FXML
    private Button btnOrderDetails;

    @FXML
    private Tab tabVerkaeufer;

    @FXML
    private ListView<Order> lvLieferung;

    @FXML
    private ListView<?> lvSortiment;

    @FXML
    private Button btnDeleteSort;

    @FXML
    private TextField tfSortiment;

    @FXML
    private ListView<Article> lvSellingArticles;

    @FXML
    private Button btnAddSort;

    @FXML
    private TextField tfArticleName;

    @FXML
    private TextField tfArticleDescript;

    @FXML
    private TextField tfArticlePrice;

    @FXML
    private ChoiceBox<Sorts> cbArticleSort;

    @FXML
    private Button btnAddArticle;

    @FXML
    private Button btnDelArt;

    @FXML
    private Button btnLieferungDetails;


    private ObservableList<Article> itemsArticles;
    private ObservableList<CartArticle> itemsCartArcticles;
    private ObservableList<Order> itemsOrders;
    private ObservableList<Order> itemsLieferung;
    private ObservableList<Sorts> itemsSorts;
    private ObservableList<Article> itemsSellerArticles;

    private ArticleRepositroy articleRepositroy;
    private BuyerRepository buyerRepository;
    private SellerRepository sellerRepository;
    private CartArticleRepository cartArctileRepository;
    private OrderRepositroy orderRepositroy;
    private OrderArticelRepository orderArticelRepository;
    private SortsRepositroy sortsRepositroy;
    private BuyerAccount buyerAccount;
    private SellerAccount sellerAccount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setUp();
            if (buyerRepository.findAll() == null) {
                buyerRepository.save(new BuyerAccount("AdminUser", "passwort"));
            }
            buyerAccount = buyerRepository.findById(1);
            if (sellerRepository.findAll() == null) {
                sellerRepository.save(new SellerAccount("AdminSeller", "passwort", "Moskau 1"));
            }
            sellerAccount = sellerRepository.findById(1);

        } catch (SQLException e) {
            System.out.println("SetUp Fehlere");
        }

        try {

            itemsSorts = FXCollections.observableArrayList(sortsRepositroy.findAll());
            cbKategorie.setItems(itemsSorts);
            cbKategorie.getSelectionModel().select(0);
            cbKategorie.setOnAction(actionEvent -> {
                try {
                    itemsArticles = FXCollections.observableArrayList(articleRepositroy.return_articles_by_category(cbKategorie.getSelectionModel().getSelectedItem().getId()));
                    lvArticles.setItems(itemsArticles);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            cbArticleSort.setItems(itemsSorts);
            cbArticleSort.getSelectionModel().select(0);

            System.out.println("1");
            itemsArticles = FXCollections.observableArrayList(articleRepositroy.return_articles_by_category(cbKategorie.getSelectionModel().getSelectedItem().getId()));
            lvArticles.setItems(itemsArticles);

            System.out.println("12");
            itemsCartArcticles = FXCollections.observableArrayList(cartArctileRepository.getAllCartArticleFromThisBuyer(buyerAccount));
            lvCartArticles.setItems(itemsCartArcticles);
            System.out.println("13");

            itemsOrders = FXCollections.observableArrayList(orderRepositroy.findAllFromThisBuyer(buyerAccount));
            lvOrders.setItems(itemsOrders);
            System.out.println("14");

            itemsLieferung = FXCollections.observableArrayList(orderRepositroy.findAllFromThisSeller(sellerAccount));
            lvLieferung.setItems(itemsLieferung);
            System.out.println("15");

            itemsSellerArticles = FXCollections.observableArrayList(articleRepositroy.findAllfromThisSeller(sellerAccount));
            lvSellingArticles.setItems(itemsSellerArticles);

        } catch (SQLException e) {
            System.out.println("AnfangsInitailize failed");
        }

        lvArticles.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvCartArticles.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvOrders.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        btnAddToCart.setOnAction(actionEvent -> addArticleToCart());
        btnDeleteCartArticle.setOnAction(actionEvent -> {
            try {
                deleteCartArticle();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        btnCartArticleDetails.setOnAction(actionEvent -> cartArticleDetails());
        btnPayCartArticle.setOnAction(actionEvent -> payCartArticle());
        btnOrderDetails.setOnAction(actionEvent -> orderDetails(1));
        btnLieferungDetails.setOnAction(actionEvent -> orderDetails(2));
        btnAddArticle.setOnAction(actionEvent -> {
            try {
                addArticle();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        btnDelArt.setOnAction(actionEvent -> {
            try {
                delArt();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void delArt() throws SQLException {
        if (lvSellingArticles.getSelectionModel().getSelectedItems() != null) {
            ObservableList<Article> allSelectedArticles = lvSellingArticles.getSelectionModel().getSelectedItems();
            for (Article article : allSelectedArticles) {
                try {
                    articleRepositroy.delete_from_store(article);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            itemsArticles = FXCollections.observableArrayList(articleRepositroy.findAllfromThisSeller(sellerAccount));
            lvSellingArticles.setItems(itemsArticles);
        }
    }

    private void addArticle() throws SQLException {
        if (!tfArticleName.getText().isEmpty() &&
                !tfArticleDescript.getText().isEmpty() &&
                !tfArticlePrice.getText().isEmpty() &&
                !cbArticleSort.getItems().isEmpty()) {

            articleRepositroy.insert_into_store(new Article(tfArticleName.getText(), Double.parseDouble(tfArticlePrice.getText()),
                    tfArticleDescript.getText(), sellerAccount, cbArticleSort.getSelectionModel().getSelectedItem()));
            tfArticleName.clear();
            tfArticleDescript.clear();
            tfArticlePrice.clear();
            itemsSellerArticles = FXCollections.observableArrayList(articleRepositroy.findAllfromThisSeller(sellerAccount));
            lvSellingArticles.setItems(itemsSellerArticles);
        }
    }

    private void orderDetails(int i) {
        Alert alert;
        int anzahlItems = 0;
        Order order = null;
        if (i == 1) {
            anzahlItems = lvOrders.getSelectionModel().getSelectedItems().size();
            order = lvOrders.getSelectionModel().getSelectedItem();
        } else if (i == 2) {
            anzahlItems = lvLieferung.getSelectionModel().getSelectedItems().size();
            order = lvLieferung.getSelectionModel().getSelectedItem();
        }
        if (anzahlItems != 1) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Bitte Nur eine Bestellung auswählen");
            alert.setHeaderText("Bitte Nur eine Bestellung auswählen");
            alert.showAndWait();
        } else {
            System.out.println(anzahlItems);
            try {
                List<Article> allArticleInThisOrder = orderRepositroy.findAllInformationFromThisOrder(order.getId().intValue());
                alert = new Alert(Alert.AlertType.INFORMATION);
                double preisSumme = 0;
                alert.setTitle("Bestellung Details");
                StringBuilder stringBuilder = new StringBuilder();
                alert.setHeaderText("Bestellungsnummer: " + order.getId());
                for (Article article : allArticleInThisOrder) {
                    stringBuilder.append("Artikel Name: " + article.getName() +
                            "Preis: " + article.getPrice() + '\n');
                    preisSumme += article.getPrice();
                }
                stringBuilder.append("\nAnzahl der Artikeln: " + allArticleInThisOrder.size());
                stringBuilder.append("\nSumme: " + preisSumme);
                alert.setContentText(stringBuilder.toString());
                alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void payCartArticle() {
        if (lvCartArticles.getSelectionModel().getSelectedItems() != null) {
            ObservableList<CartArticle> allSelectedArticles = lvCartArticles.getSelectionModel().getSelectedItems();
            try {
                Order order = orderRepositroy.buy(buyerAccount);
                for (CartArticle article : allSelectedArticles) {
                    orderArticelRepository.save(new OrderArticel(article.getArticle(), order));
                    System.out.println("okkk");
                    cartArctileRepository.delete(article.getArticle().getId());
                }
                itemsLieferung = FXCollections.observableArrayList(orderRepositroy.findAllFromThisSeller(sellerAccount));
                lvLieferung.setItems(itemsLieferung);
                itemsOrders = FXCollections.observableArrayList(orderRepositroy.findAllFromThisBuyer(buyerAccount));
                lvOrders.setItems(itemsOrders);
                itemsCartArcticles = FXCollections.observableArrayList(cartArctileRepository.getAllCartArticleFromThisBuyer(buyerAccount));
                lvCartArticles.setItems(itemsCartArcticles);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    private void cartArticleDetails() {
        Alert alert;
        if (lvCartArticles.getSelectionModel().getSelectedItems().size() != 1) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Bitte Nur ein Artikel auswählen");
            alert.setHeaderText("Bitte Nur ein Artikel auswählen");
            alert.showAndWait();
        } else {
            System.out.println("ok1");
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Artikel Details");
            alert.setHeaderText("Verkäufer: " + lvCartArticles.getSelectionModel().getSelectedItem().getArticle().getSeller().getId());
            alert.setContentText("Name: " + lvCartArticles.getSelectionModel().getSelectedItem().getArticle().getName() +
                    "\nPreis: " +
                    lvCartArticles.getSelectionModel().getSelectedItem().getArticle().getPrice() +
                    "\nDescription: " +
                    lvCartArticles.getSelectionModel().getSelectedItem().getArticle().getDescription());
            alert.showAndWait();

        }
    }

    private void deleteCartArticle() throws SQLException {
        if (lvCartArticles.getSelectionModel().getSelectedItems() != null) {
            ObservableList<CartArticle> allSelectedArticles = lvCartArticles.getSelectionModel().getSelectedItems();
            for (CartArticle article : allSelectedArticles) {
                try {
                    cartArctileRepository.delete(article.getArticle().getId());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            itemsCartArcticles = FXCollections.observableArrayList(cartArctileRepository.getAllCartArticleFromThisBuyer(buyerAccount));
            lvCartArticles.setItems(itemsCartArcticles);
        }
    }

    private List<CartArticle> addArticleToCart() {
        List<CartArticle> allSavedArticles = null;
        if (lvArticles.getSelectionModel().getSelectedItems() != null) {
            allSavedArticles = new LinkedList<>();
            ObservableList<Article> allSelectedArticles = lvArticles.getSelectionModel().getSelectedItems();
            for (Article article : allSelectedArticles) {
                try {
                    System.out.println(article.toString());
                    System.out.println(buyerAccount);
                    allSavedArticles.add(cartArctileRepository.add_to_basket(new CartArticle(article, buyerAccount)));
                    itemsCartArcticles = FXCollections.observableArrayList(cartArctileRepository.getAllCartArticleFromThisBuyer(buyerAccount));
                    lvCartArticles.setItems(itemsCartArcticles);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return allSavedArticles;
    }

    public void setUp() throws SQLException {
        var properties = new Properties();
        properties.put("user", "sa");
        properties.put("password", "");
        var connection = DriverManager.getConnection("""
                jdbc:sqlserver://IFSQL-03.htl-stp.if;database=chen_onlineShop
                """, properties);
        //   this.articleRepositroy = new ArticleRepository(connection);
        this.articleRepositroy = new JdbcArticleRepository(connection);
        this.buyerRepository = new JdbcBuyerRepository(connection);
        ;
        this.sellerRepository = new JdbcSellerRepository(connection);
        ;
        this.cartArctileRepository = new JdbcCartArticleRepository(connection);
        ;
        this.orderRepositroy = new JdbcOrderRepository(connection);
        ;
        this.orderArticelRepository = new JdbcOrderArticleRepository(connection);
        ;
        this.sortsRepositroy = new JdbcSortsRepository(connection);
        ;
    }
}


