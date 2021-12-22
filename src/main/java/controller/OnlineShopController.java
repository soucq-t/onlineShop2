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
    private ListView<Order> lvLieferungen;

    @FXML
    private ListView<?> lvSortiment;

    @FXML
    private Button btnDeleteSort;

    @FXML
    private TextField tfSortiment;

    @FXML
    private ListView<CartArticle> lvInCartArticles;

    @FXML
    private Button btnAddSort;

    @FXML
    private TextField tfArticleName;

    @FXML
    private TextField tfArticleDescript;

    @FXML
    private TextField tfArticlePrice;

    @FXML
    private ChoiceBox<?> cbArticleSort;

    @FXML
    private Button btnAddArticle;

    @FXML
    private Button btnDelCart;

    @FXML
    private Button btnLieferungDetails;


    private ObservableList<Article> itemsArticles;
    private ObservableList<CartArticle> itemsCartArcticles;
    private ObservableList<Order> itemsOrders;
    private ObservableList<Sorts> itemsSorts;
    private ObservableList<Article> itemsSellerArticles;

    private ArticleRepository articleRepositroy;
    private BuyerRepository buyerRepository;
    private SellerRepository sellerRepository;
    private CartArticleRepository cartArctileRepository;
    private OrderRepositroy orderRepositroy;
    private OrderArticelRepository orderArticelRepository;
    private SortsRepositroy sortsRepositroy;
    private Account buyerAccount;
    private SellerAccount  sellerAccount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setUp();
            if (buyerRepository.findAll() == null) {
                buyerRepository.save(new BuyerAccount("AdminUser", "passwort"));
            }
            buyerAccount = buyerRepository.findById(1);
        } catch (SQLException e) {
            System.out.println("SetUp Fehler");
        }

        try {
            itemsSorts = FXCollections.observableArrayList(sortsRepositroy.findAll());
            cbKategorie.setItems(itemsSorts);

            itemsArticles = FXCollections.observableArrayList(articleRepositroy.getAllArticle());
            lvArticles.setItems(itemsArticles);

            itemsCartArcticles = FXCollections.observableArrayList(cartArctileRepository.getAllCartArticle());
            lvCartArticles.setItems(itemsCartArcticles);

            itemsOrders = FXCollections.observableArrayList(orderRepositroy.findAll());
            lvOrders.setItems(itemsOrders);
            lvLieferungen.setItems(itemsOrders);

            itemsSellerArticles=FXCollections.observableArrayList()

        } catch (SQLException e) {
            System.out.println("AnfangsInitailize failed");
        }
        lvArticles.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvCartArticles.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvOrders.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        btnAddToCart.setOnAction(actionEvent -> addArticleToCart());
        btnDeleteCartArticle.setOnAction(actionEvent -> deleteCartArticle());
        btnCartArticleDetails.setOnAction(actionEvent -> cartArticleDetails());
        btnPayCartArticle.setOnAction(actionEvent -> payCartArticle());
        btnOrderDetails.setOnAction(actionEvent -> orderDetails(1));
        btnLieferungDetails.setOnAction(actionEvent -> orderDetails(2));
        lvInCartArticles.setItems(itemsSellerArticles);
    }

    private void orderDetails(int i) {
        Alert alert;
        int anzahlItems=0;
        if (i == 0){
            anzahlItems=lvOrders.getSelectionModel().getSelectedItems().size();
        }else if (i == 0){
            anzahlItems=lvLieferungen.getSelectionModel().getSelectedItems().size();
        }
        if (anzahlItems!= 1) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Bitte Nur eine Bestellung auswählen");
            alert.setHeaderText("Bitte Nur eine Bestellung auswählen");
            alert.showAndWait();
        } else {
            try {
                ResultSet allInformations = orderRepositroy.findAllInformationFromThisOrder(lvOrders.getSelectionModel().getSelectedItem().getId().intValue());
                alert = new Alert(Alert.AlertType.INFORMATION);
                double preisSumme = 0;
                int anzahl = 0;
                alert.setTitle("Bestellung Details");
                StringBuilder stringBuilder = new StringBuilder();
                alert.setHeaderText("Bestellungsnummer: " + allInformations.getString("bes_id"));
                while (allInformations.next()) {
                    stringBuilder.append("Artikel Name: " + allInformations.getString("art_name" + '\n' +
                            "Preis: " + allInformations.getDouble("art_price") + '\n'));
                    preisSumme += allInformations.getDouble("art_price");
                    anzahl++;
                }
                stringBuilder.append("Anzahl der Artikeln: " + anzahl);
                stringBuilder.append("Summe: " + preisSumme);
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
                Order order = orderRepositroy.save(new Order(buyerAccount));
                for (CartArticle article : allSelectedArticles) {
                    orderArticelRepository.save(new OrderArticel(article.getArticle(), order));
                    cartArctileRepository.delete(article.getId());
                }
                itemsOrders = FXCollections.observableArrayList(orderRepositroy.findAll());
                itemsCartArcticles = FXCollections.observableArrayList(cartArctileRepository.getAllCartArticle());
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
            try {
                ResultSet allInformations = cartArctileRepository.getAllInformationFromThisArticle(lvCartArticles.getSelectionModel().getSelectedItem().getId().intValue());
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Artikel Details");
                alert.setHeaderText("Verkäufer: " + allInformations.getString("username"));
                alert.setContentText("Name: " + allInformations.getString("art_name" + '\n' +
                        "Preis: " + allInformations.getDouble("art_price") + '\n' +
                        "Description: " + allInformations.getString("art_Description")));
                alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteCartArticle() {
        if (lvCartArticles.getSelectionModel().getSelectedItems() != null) {
            ObservableList<CartArticle> allSelectedArticles = lvCartArticles.getSelectionModel().getSelectedItems();
            for (CartArticle article : allSelectedArticles) {
                try {
                    cartArctileRepository.delete(article.getId().intValue());
                    itemsCartArcticles = FXCollections.observableArrayList(cartArctileRepository.getAllCartArticle());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private List<CartArticle> addArticleToCart() {
        List<CartArticle> allSavedArticles = null;
        if (lvArticles.getSelectionModel().getSelectedItems() != null) {
            allSavedArticles = new LinkedList<>();
            ObservableList<Article> allSelectedArticles = lvArticles.getSelectionModel().getSelectedItems();
            for (Article article : allSelectedArticles) {
                try {
                    allSavedArticles.add(cartArctileRepository.save(new CartArticle(article, buyerAccount)));
                    itemsCartArcticles = FXCollections.observableArrayList(cartArctileRepository.getAllCartArticle());
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
        var connection = DriverManager.getConnection("jdbc:sqlserver://IFSQL-03;database=chen_onlineShop", properties);
        this.articleRepositroy = new ArticleRepository(connection);
    }
}


