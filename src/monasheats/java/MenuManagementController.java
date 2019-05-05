package monasheats.java;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class MenuManagementController {

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public MenuManagementController() {

    }

    private ObservableList<String> menuList = FXCollections.observableArrayList();

    private void addSampleData() {
        menuList.add("林檎");
        menuList.add("香蕉");
        menuList.add("闸机");
        menuList.add("真猪奶茶");
    }

    @FXML
    private Button saveMenuButton;
    @FXML
    private Button addItemButton;
    @FXML
    private Button deleteItemButton;

    @FXML
    private TableView<String> menuTableView;
    @FXML
    private TableColumn<String, String> itemNameColumn;
    @FXML
    private TableColumn<String, String> itemDescColumn;
    @FXML
    private TableColumn<String, String> itemPriceColumn;

    @FXML
    private void initialize() {
        addSampleData();

        menuTableView.setItems(menuList);
        menuTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println((String)newValue);
        });

        itemNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));

        saveMenuButton.setOnAction(event -> {
            stage.close();
        });
    }
}
