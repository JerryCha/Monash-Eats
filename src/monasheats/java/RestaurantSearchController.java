package monasheats.java;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RestaurantSearchController {

    private Main main;

    private String user;

    private ObservableList<String> restaurantList = FXCollections.observableArrayList();

    private void addSampleData() {
        restaurantList.add("A");
        restaurantList.add("B");
        restaurantList.add("C");
        restaurantList.add("D");
        restaurantList.add("E");
        restaurantList.add("F");
        restaurantList.add("G");
        restaurantList.add("H");
        restaurantList.add("I");
        restaurantList.add("J");
        restaurantList.add("K");
        restaurantList.add("L");
        restaurantList.add("M");
        restaurantList.add("N");
        restaurantList.add("O");
        restaurantList.add("P");
        restaurantList.add("Q");

    }

    public RestaurantSearchController() {

    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setUser(String user) {
        this.user = user;

        if (user == null) {
            accountButton.setText("Guest");
            logoutOpt.setText("Login");
            historyOrderMenuItem.setVisible(false);
        }
        else {
            accountButton.setText(this.user);
            logoutOpt.setText("Logout");
            historyOrderMenuItem.setVisible(true);
        }
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
    private MenuItem myRestaurantButton;
    @FXML
    private MenuItem manageCustomerButton;

    @FXML
    private void initialize() {
        addSampleData();    // For demo purpose. Remove if backend is available.

        restaurantTable.setItems(restaurantList);
        restaurantTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (user != null)
                main.gotoRestaurant(newValue, user);
            else
                main.gotoLogin();
        });

        restaurantNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        restaurantSurburbColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));

        accountButton.setOnAction(event -> {
            if (user == null)
                main.gotoLogin();
        });

        logoutOpt.setOnAction(event -> {
            if (user != null)
                setUser(null);
            else
                main.gotoLogin();
        });

        historyOrderMenuItem.setOnAction(event -> main.gotoHistoryOrder(user));

        manageCustomerButton.setOnAction(event -> {
            main.gotoCustomerManagement(user);
        });

        myRestaurantButton.setOnAction(event -> main.gotoRestaurantManagement(user));
    }
}
