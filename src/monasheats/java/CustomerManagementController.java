package monasheats.java;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerManagementController {

    private MonashEats monashEats;

    private String user;

    public CustomerManagementController() {

    }

    public void setMonashEats(MonashEats monashEats) {
        this.monashEats = monashEats;
    }

    public void setUser(String user) {
        this.user = user;
        accountButton.setText(user);
    }

    private ObservableList<String> customerList = FXCollections.observableArrayList();

    private void addSampleData() {
        customerList.add("aaaaa");
        customerList.add("bbbb");
        customerList.add("cccc");
        customerList.add("ddddd");
        customerList.add("eeeee");
        customerList.add("fffffff");
        customerList.add("ggggggg");
        customerList.add("hhhhh");
        customerList.add("iiiiiii");
        customerList.add("jjjjjjjjjj");
        customerList.add("kkkkkkkkkkk");
        customerList.add("lllllllllll");
        customerList.add("mmmmmmmmmmmm");
        customerList.add("nnnnnnnnn");
        customerList.add("ooooooo");
        customerList.add("ppppppp");
        customerList.add("qqqqqqqqq");
        customerList.add("uuuuuuuuuuuu");
        customerList.add("vvvvvvvvvvvvvvvv");
        customerList.add("wwwwwww");
        customerList.add("xxxxxxx");
        customerList.add("yyyyyy");
        customerList.add("zzzzzzzz");
        customerList.add("rrrrrrrrrr");
        customerList.add("ssssssss");
        customerList.add("ttttttttt");
    }

    @FXML
    private MenuButton accountButton;
    @FXML
    private MenuItem myRestaurantButton;
    @FXML
    private MenuItem logoutOpt;

    @FXML
    private TableView<String> customerTableView;
    @FXML
    private TableColumn<String, String> accountTableColumn;
    @FXML
    private TableColumn<String, String> nameTableColumn;
    @FXML
    private TableColumn<String, String> surburbTableColumn;
    @FXML
    private TableColumn<String, String> phoneTableColumn;

    @FXML
    private void initialize() {
        addSampleData();

        customerTableView.setItems(customerList);
        customerTableView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            popupCustomerDetail((String)newValue);
        }));

        accountTableColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        nameTableColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        surburbTableColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        phoneTableColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));

        // Set MenuButton and sub-buttons action
        // TODO: Determine visibility based on user role.
        myRestaurantButton.setOnAction(event -> monashEats.gotoRestaurantManagement(user));
        logoutOpt.setOnAction(event -> {
            user = null;
            monashEats.gotoLogin();
        });
    }

    private void popupCustomerDetail(String account) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("account_detail.fxml"));

            Parent parent = loader.load();

            AccountDetailController actDetailController = loader.getController();

            Stage popupStage = new Stage();
            popupStage.setTitle(account);
            popupStage.setScene(new Scene(parent));
            actDetailController.setStage(popupStage);

            popupStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
