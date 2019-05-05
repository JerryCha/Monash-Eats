package monasheats.java;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerManagementController {

    private Main main;

    private String user;

    public CustomerManagementController() {

    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setUser(String user) {
        this.user = user;
        accountButton.setText(user);
    }

    private ObservableList<String> customerList = FXCollections.observableArrayList();

    private void addSampleData() {
        customerList.add("李田所");
        customerList.add("田所浩二");
        customerList.add("远野");
        customerList.add("TNOK");
        customerList.add("中田");
        customerList.add("Billy Herrington");
        customerList.add("VAN");
        customerList.add("蔡徐坤");
        customerList.add("孙笑川");
        customerList.add("卢本伟");
        customerList.add("梁非凡");
        customerList.add("刘醒");
        customerList.add("王境泽");
        customerList.add("草彅刚");
        customerList.add("徐逸");
        customerList.add("陈睿");
    }

    @FXML
    private MenuButton accountButton;

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

        nameTableColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
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
