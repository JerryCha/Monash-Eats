package monasheats.java;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {

    private Main main;

    public RegisterController() {

    }

    public void setMain(Main main) {
        this.main = main;
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
        submitButton.setOnAction(event -> {
            System.out.println(emailTextField.getText() + " registers as:"
                    + (customerCheckBox.selectedProperty().getValue()?"customer":"")
                    + ", "
                    + (ownerCheckBox.selectedProperty().getValue()?"owner":"")
            );
        });
    }
}
