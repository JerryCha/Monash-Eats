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

public class RestaurantManagementController {

    private Main main;
    private String user;

    public RestaurantManagementController() {

    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    private ObservableList<String> myRestaurants = FXCollections.observableArrayList();

    private void addSampleData() {
        myRestaurants.add("林檎风味");
        myRestaurants.add("香蕉风味");
        myRestaurants.add("滨滨风味");
        myRestaurants.add("下北泽Co↑co↓");
    }

    @FXML
    private TextField searchBox;
    @FXML
    private MenuButton accountButton;
    @FXML
    private TableView<String> restaurantTable;
    @FXML
    private TableColumn<String, String> restaurantNameColumn;
    @FXML
    private TableColumn<String, String> restaurantSurburbColumn;
    @FXML
    private MenuItem logoutOpt;
    @FXML
    private MenuItem historyOrderMenuItem;
    @FXML
    private MenuItem manageCustomerButton;

    @FXML
    private void initialize() {
        addSampleData();
        restaurantTable.setItems(myRestaurants);
        restaurantTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            popupRestaurantDetail((String) newValue);
        }));

        restaurantNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));

        manageCustomerButton.setOnAction(event -> {
            main.gotoCustomerManagement(user);
        });

        logoutOpt.setOnAction(event -> {
            user = null;
            main.gotoSearchRestaurant(user);
        });

        historyOrderMenuItem.setOnAction(event -> {
            main.gotoHistoryOrder(user);
        });
    }

    private void popupRestaurantDetail(String restaurant) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("edit_restaurant.fxml"));

            Parent parent = loader.load();

            RestaurantDetailController rstDetailController = loader.getController();
            rstDetailController.setRestaurant(restaurant);

            Stage stage = new Stage();
            stage.setTitle(restaurant);
            stage.setX(main.getStage().getX()+150);
            stage.setY(main.getStage().getY()+150);
            rstDetailController.setStage(stage);
            stage.setScene(new Scene(parent));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
