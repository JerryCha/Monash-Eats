package monasheats.java;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AccountDetailController {

    private Stage stage;

    public AccountDetailController() {

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private Button deleteAccountButton;

    @FXML
    private void initialize() {
        deleteAccountButton.setOnAction(event -> {
            stage.close();
        });
    }
}
