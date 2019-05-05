package monasheats.java;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class RestaurantController {

    private Main main;

    private String restaurant;

    private String user;

    public RestaurantController() {

    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;

        restaurantLabel.setText(restaurant);
    }

    public void setUser(String user) {
        this.user = user;

        accountButton.setText(user);
    }

    private ObservableList<String> itemList = FXCollections.observableArrayList();

    private void addSampleData() {
        itemList.add("林檎1");
        itemList.add("林檎2");
        itemList.add("林檎3");
        itemList.add("林檎4");
        itemList.add("香蕉1");
        itemList.add("香蕉2");
        itemList.add("香蕉3");
        itemList.add("香蕉4");
    }

    // Tableview and its corresponding columns
    @FXML
    private TableView itemTableView;
    @FXML
    private TableColumn<String, String> itemNameColumn;
    @FXML
    private TableColumn<String, String> itemDescColumn;
    @FXML
    private TableColumn<String, String> itemPriceColumn;
    // View cart button
    @FXML
    private Button cartButton;
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
        addSampleData();

        itemTableView.setItems(itemList);
        itemTableView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> popupItemWindow((String) newValue)));

        itemNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        itemDescColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        itemPriceColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));

        cartButton.setOnAction(event -> main.gotoCart(user));

        myRestaurantButton.setOnAction(event -> main.gotoRestaurantManagement(user));
        manageCustomerButton.setOnAction(event -> main.gotoCustomerManagement(user));
    }

    @FXML
    private Button testButton;

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
