package monasheats.java;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    // Reference to main
    private Main main;

    public void setMain(Main main) {
        this.main = main;
    }
    /**
     * Constructor
     */
    public LoginController() {

    }

    // Widgets
    @FXML
    private Label loginLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label pwdLabel;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField pwdTextField;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private Button guestButton;

    @FXML
    private void initialize() {
        loginButton.setOnAction(event -> {
            if (emailTextField.getText().trim().equals(""))
                login("田所浩二");
            else
                login(emailTextField.getText());
        });

        guestButton.setOnAction(event -> main.gotoSearchRestaurant(null));

        registerButton.setOnAction(event -> main.gotoRegister());
    }

    private void login(String username) {
        main.gotoSearchRestaurant(username);
    }
}
