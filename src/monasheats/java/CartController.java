package monasheats.java;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;

public class CartController {

    private MonashEats monashEats;

    private String user;

    private Scene previousScene;

    public CartController() {

    }

    public void setMonashEats(MonashEats monashEats) {
        this.monashEats = monashEats;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPreviousScene(Scene previousScene) {
        this.previousScene = previousScene;
    }

    private ObservableList<String> cartList = FXCollections.observableArrayList();

    @FXML
    private TableView<String> cartTableView;
    @FXML
    private TableColumn<String, String> itemColumn;
    @FXML
    private TableColumn<String, String> unitPriceColumn;
    @FXML
    private TableColumn<String, String> quantityColumn;
    @FXML
    private TableColumn<String, String> totalPriceColumn;

    @FXML
    private Button cartEditButton;
    @FXML
    private Button checkoutButton;
    @FXML
    private Label totalPriceLabel;
    @FXML
    private Button backButton;

    @FXML
    private MenuButton accountButton;
    @FXML
    private MenuItem historyOrderMenuItem;

    @FXML
    private void initialize() {
        cartTableView.setItems(cartList);

        itemColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        unitPriceColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        quantityColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        totalPriceColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));

        totalPriceLabel.setText("$" + String.format("%1$,.2f", Math.random()*114514));

        checkoutButton.setOnAction(event -> monashEats.gotoCheckOut(user, monashEats.getStage().getScene()));
        cartEditButton.setOnAction(event -> System.out.println("To Edit Cart"));
        backButton.setOnAction(event -> monashEats.getStage().setScene(previousScene));
    }
}
