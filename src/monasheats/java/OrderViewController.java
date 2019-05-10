package monasheats.java;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public class OrderViewController {

    private String user;
    private MonashEats monashEats;

    public OrderViewController() {}

    public void setUser(String user) {
        this.user = user;
        accountButton.setText(user);
    }

    public void setMonashEats(MonashEats monashEats) {
        this.monashEats = monashEats;
    }

    @FXML
    private MenuButton accountButton;
    @FXML
    private MenuItem homepageButton;
    @FXML
    private MenuItem logoutOpt;

    @FXML
    private void initialize() {

        // Temporary button going to homepage
        homepageButton.setOnAction(event -> monashEats.gotoSearchRestaurant(user));

        logoutOpt.setOnAction(event -> {
            user = null;
            monashEats.gotoLogin();
        });
    }
}
