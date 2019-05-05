package monasheats.java;

import javafx.fxml.FXML;
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
            stage.close();
        });
    }
}
