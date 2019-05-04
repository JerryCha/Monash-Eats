package monasheats.java;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller {

    // Reference to main application
    private Main main;

    /**
     * Set reference of main.
     * @param main Main instance.
     */
    public void setMain(Main main) {
        this.main = main;
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
        loginButton.setOnAction(event -> main.gotoLogin());
        guestButton.setOnAction(event -> main.gotoSearchRestaurant(null));
    }

}
