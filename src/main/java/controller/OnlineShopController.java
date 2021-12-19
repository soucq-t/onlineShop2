package controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
public class OnlineShopController {

    @FXML
    private TabPane tabFenster;

    @FXML
    private Tab tabKaeufer;

    @FXML
    private ListView<?> lvSC;

    @FXML
    private ChoiceBox<?> cbKategorie;

    @FXML
    private ListView<?> lvWaren;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnPay;

    @FXML
    private Button btnDetails;

    @FXML
    private TextField tfAdresse;

    @FXML
    private ListView<?> lvOrders;

    @FXML
    private Button btnOrderDetails;

    @FXML
    private Tab tabVerkaeufer;

    @FXML
    private TableView<?> tvLieferungen;

    @FXML
    private ListView<?> lvSortiment;

    @FXML
    private Button btnDeleteSort;

    @FXML
    private TextField tfSortiment;

    @FXML
    private ListView<?> lvInCartArticles;

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
}
