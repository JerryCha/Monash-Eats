package monasheats.java;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;

public class LoginController {

    // Reference to monashEats
    private MonashEats monashEats;

    public void setMonashEats(MonashEats monashEats) {
        this.monashEats = monashEats;
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
        // Temporary hint
        emailTextField.setPromptText("Currently, only \"abc\" with pwd \"123\" could login.");

        loginButton.setOnAction(event -> login());

        // Press 'ENTER' to login
        loginButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER)
                login();
        });
        emailTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER)
                login();
        });
        pwdTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER)
                login();
        });

        guestButton.setOnAction(event -> monashEats.gotoSearchRestaurant(null));

        registerButton.setOnAction(event -> monashEats.gotoRegister());
    }

    private void login() {
        String account = emailTextField.getText().trim();
        // TODO: Replace with formal login workflow after backend implemented
        if (!account.equals("abc") && !pwdTextField.getText().equals("123")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Authorization failed");
            alert.setContentText("Your credential is correct");
            alert.showAndWait();
            emailTextField.setText("");
            pwdTextField.setText("");
        } else {
            monashEats.gotoSearchRestaurant(account);
        }
    }
}
