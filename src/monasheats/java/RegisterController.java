package monasheats.java;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

public class RegisterController {

    private MonashEats monashEats;

    public RegisterController() {

    }

    public void setMonashEats(MonashEats monashEats) {
        this.monashEats = monashEats;
    }

    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField pwdField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField streetTextField;
    @FXML
    private TextField surburbTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private TextField ansTextField1;
    @FXML
    private TextField ansTextField2;
    @FXML
    private CheckBox customerCheckBox;
    @FXML
    private CheckBox ownerCheckBox;

    @FXML
    private Button submitButton;

    @FXML
    private void initialize() {
        // Restrict phoneTextField to number only.
        phoneTextField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*") == false)
                phoneTextField.setText(newValue.replaceAll("\\D*", ""));
        }));

        submitButton.setOnAction(event -> {
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Register successfully");
            successAlert.setContentText("You have created an account. Now bringing you to login.");

            validation();

            Optional<ButtonType> result = successAlert.showAndWait();
            if (result.get() == ButtonType.OK)
                monashEats.gotoLogin();
        });
    }

    private boolean[] validation() {
        boolean[] results = new boolean[4];
        for (int i = 0; i < results.length; i++)
            results[i] = true;

        // Processing email address

        // Validating password
        if (pwdField.getText().equals(""))
            results[1] = false;

        return results;
    }
}
