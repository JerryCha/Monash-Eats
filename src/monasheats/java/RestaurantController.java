package monasheats.java;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class RestaurantController {

    private MonashEats monashEats;

    private Scene previousScene;

    private String restaurant;

    private String user;

    public RestaurantController() {

    }

    public void setMonashEats(MonashEats monashEats) {
        this.monashEats = monashEats;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;

        restaurantLabel.setText(restaurant);
    }

    public void setUser(String user) {
        this.user = user;

        accountButton.setText(user);
    }

    public void setPreviousScene(Scene previousScene) {
        this.previousScene = previousScene;
    }

    private ObservableList<String> itemList = FXCollections.observableArrayList();
    private ObservableList<String> couponList = FXCollections.observableArrayList();

    // Demo purpose. Remove after backend implemented.
    private void addSampleRestaurant() {
        itemList.add("Sandwich");
        itemList.add("Apple");
        itemList.add("Banana");
        itemList.add("Tender");
        itemList.add("Cheese burger");
        itemList.add("Tuna sushi roll");
        itemList.add("Nihhon go");
        itemList.add("∑œ˙ß∫∂¬ƒ˙œ∑∂ƒ¬˚∑œÏ");
        itemList.add("ª•¡™¶¥©ßƒ∂∫eau¬∫ƒß");
    }

    // Demo purpose. Remove after backend implemented.
    private void addSampleCoupon() {
        couponList.add("114514");
        couponList.add("1919810");
        couponList.add("yarimasune");
        couponList.add("goodday");
        couponList.add("BeautyChick");
    }

    // TableView of menu and its corresponding columns
    @FXML
    private TableView<String> itemTableView;
    @FXML
    private TableColumn<String, String> itemNameColumn;
    @FXML
    private TableColumn<String, String> itemDescColumn;
    @FXML
    private TableColumn<String, String> itemPriceColumn;

    // TableView of coupon and its corresponding columns
    @FXML
    private TableView<String> couponTableView;
    @FXML
    private TableColumn<String, String> couponCodeColumn;

    // View cart button
    @FXML
    private Button cartButton;
    @FXML
    private Button backButton;

    @FXML
    private MenuButton accountButton;
    @FXML
    private MenuItem myRestaurantButton;
    @FXML
    private MenuItem manageCustomerButton;
    // RestaurantDisplay
    @FXML
    private Label restaurantLabel;

    @FXML
    private void initialize() {
        addSampleRestaurant();
        addSampleCoupon();

        // Bind menu to components
        itemTableView.setItems(itemList);
        itemTableView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> popupItemWindow((String) newValue)));

        itemNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        itemDescColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        itemPriceColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));

        // Bind coupon to components
        couponTableView.setItems(couponList);
        couponCodeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        // TODO: Copy coupon code to clipboard by clicking

        cartButton.setOnAction(event -> monashEats.gotoCart(user, monashEats.getStage().getScene()));
        backButton.setOnAction(event -> {
            monashEats.getStage().setScene(previousScene);
        });

        myRestaurantButton.setOnAction(event -> monashEats.gotoRestaurantManagement(user));
        manageCustomerButton.setOnAction(event -> monashEats.gotoCustomerManagement(user));
    }

    private void popupItemWindow(String item) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("item_detail.fxml"));

            Parent parent = loader.load();

            ItemDetailController itemDetailController = loader.getController();
            itemDetailController.setItem(item);

            Stage popupStage = new Stage();
            popupStage.setTitle(item);
            popupStage.setScene(new Scene(parent));
            itemDetailController.setStage(popupStage);

            popupStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
