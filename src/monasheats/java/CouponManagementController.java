package monasheats.java;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CouponManagementController {

    private Stage stage;

    public CouponManagementController() {

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private Button saveButton;

    @FXML
    private void initialize() {
        saveButton.setOnAction(event -> {
            stage.close();
        });
    }
}
