package monasheats.java;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller {

    // Reference to monashEats application
    private MonashEats monashEats;

    /**
     * Set reference of monashEats.
     * @param monashEats MonashEats instance.
     */
    public void setMonashEats(MonashEats monashEats) {
        this.monashEats = monashEats;
    }
    /**
     * Constructor
     */
    public Controller() {

    }

    // Widget
    @FXML
    private Button loginButton;
    @FXML
    private Button guestButton;
    @FXML
    private Label meTitle;

    @FXML
    private void initialize() {
        loginButton.setOnAction(event -> monashEats.gotoLogin());
        guestButton.setOnAction(event -> monashEats.gotoSearchRestaurant(null));
    }

}
