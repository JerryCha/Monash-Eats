package monasheats.java;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ItemDetailController {

    private int itemQty;

    private String item;

    private Stage stage;

    public ItemDetailController() {

    }

    public void setItem(String item) {
        this.item = item;
        itemNameLabel.setText(item);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private Label itemNameLabel;
    @FXML
    private Label itemDescLabel;
    @FXML
    private Label itemPriceLabel;

    @FXML
    private Button decreaseButton;
    @FXML
    private Button increaseButton;
    @FXML
    private TextField qtyTextField;
    @FXML
    private Button addCartButton;

    @FXML
    private void initialize() {
        itemQty = 1;
        qtyTextField.setText(Integer.toString(itemQty));

        decreaseButton.setOnAction(event -> {
            if (itemQty != 1) {
                itemQty -= 1;
                qtyTextField.setText(Integer.toString(itemQty));
            }
        });

        increaseButton.setOnAction(event -> {
            itemQty += 1;
            qtyTextField.setText(Integer.toString(itemQty));
        });

        itemPriceLabel.setText("$1.14");

        addCartButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Added successfully");
            alert.setContentText(item + "x" + itemQty + " has been added to your cart");
            alert.showAndWait();
            stage.close();
        });

        // Restrict phoneTextField to number only.
        qtyTextField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*") == false)
                qtyTextField.setText(newValue.replaceAll("\\D*", ""));

            itemQty = Integer.parseInt(newValue);
        }));
    }
}
