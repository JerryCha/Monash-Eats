package monasheats.java;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

public class RestaurantSearchController {

    private MonashEats monashEats;

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

    public void setMonashEats(MonashEats monashEats) {
        this.monashEats = monashEats;
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
    private TableColumn<String, String> restaurantRateColumn;
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
                monashEats.gotoRestaurant(newValue, user, monashEats.getStage().getScene());
            else {
                Alert loginAlert = new Alert(Alert.AlertType.CONFIRMATION);
                loginAlert.setTitle("Login required");
                loginAlert.setContentText("Only registered could access restaurant. Do you want to continue by logging in?");
                Optional<ButtonType> result = loginAlert.showAndWait();
                if (result.get() == ButtonType.OK)
                    monashEats.gotoLogin();
            }
        });

        restaurantNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        restaurantSurburbColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        restaurantRateColumn.setCellValueFactory(data -> new SimpleStringProperty("★" + String.format("%1$,.2f", Math.random()*5)));

        accountButton.setOnAction(event -> {
            if (user == null)
                monashEats.gotoLogin();
        });

        logoutOpt.setOnAction(event -> {
            if (user != null)
                setUser(null);
            else
                monashEats.gotoLogin();
        });

        historyOrderMenuItem.setOnAction(event -> monashEats.gotoHistoryOrder(user));

        manageCustomerButton.setOnAction(event -> {
            monashEats.gotoCustomerManagement(user);
        });

        myRestaurantButton.setOnAction(event -> monashEats.gotoRestaurantManagement(user));
    }
}
