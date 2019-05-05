package monasheats.java;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public class OrderViewController {

    private String user;
    private Main main;

    public OrderViewController() {}

    public void setUser(String user) {
        this.user = user;
        accountButton.setText(user);
    }

    public void setMain(Main main) {
        this.main = main;
    }

    @FXML
    private MenuButton accountButton;
    @FXML
    private MenuItem historyOrderMenuItem;
    @FXML
    private MenuItem logoutOpt;

    @FXML
    private void initialize() {

    }
}
