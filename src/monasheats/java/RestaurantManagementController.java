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

    private MonashEats monashEats;
    private String user;

    public RestaurantManagementController() {

    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setMonashEats(MonashEats monashEats) {
        this.monashEats = monashEats;
    }

    private ObservableList<String> myRestaurants = FXCollections.observableArrayList();

    private void addSampleData() {
        myRestaurants.add("Restaurant A");
        myRestaurants.add("Restaurant B");
        myRestaurants.add("Restaurant C");
        myRestaurants.add("Restaurant D");
        myRestaurants.add("Restaurant E");
        myRestaurants.add("Restaurant F");
        myRestaurants.add("Restaurant G");
        myRestaurants.add("Restaurant H");
        myRestaurants.add("Restaurant I");
        myRestaurants.add("Restaurant J");
        myRestaurants.add("Restaurant K");
        myRestaurants.add("Restaurant L");
        myRestaurants.add("Restaurant M");
        myRestaurants.add("Restaurant N");
        myRestaurants.add("Restaurant O");
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
            monashEats.gotoCustomerManagement(user);
        });

        logoutOpt.setOnAction(event -> {
            user = null;
            monashEats.gotoSearchRestaurant(user);
        });

        historyOrderMenuItem.setOnAction(event -> {
            monashEats.gotoHistoryOrder(user);
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
            stage.setX(monashEats.getStage().getX()+150);
            stage.setY(monashEats.getStage().getY()+150);
            rstDetailController.setStage(stage);
            stage.setScene(new Scene(parent));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
