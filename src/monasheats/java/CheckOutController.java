package monasheats.java;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CheckOutController {

    private MonashEats monashEats;

    private String user;

    private Scene previousScene;

    public CheckOutController() {

    }

    public void setMonashEats(MonashEats monashEats) {
        this.monashEats = monashEats;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPreviousScene(Scene scene) {
        this.previousScene = scene;
    }

    @FXML
    private Label totalPriceLabel;
    @FXML
    private Label deliveryFeeLabel;
    @FXML
    private Label discountedLabel;
    @FXML
    private Label totalPayLabel;
    @FXML
    private Button cancelButton;
    @FXML
    private Button placeButton;
    @FXML
    private Button couponApplyButton;
    @FXML
    private Button infoEditButton;
    @FXML
    private TextField couponTextField;

    @FXML
    private void initialize() {
        double samplePrice = Math.random()*114514;
        double discounted = Math.random()*114;

        deliveryFeeLabel.setText("Free");
        totalPriceLabel.setText("$" + String.format("%1$,.2f", samplePrice));
        totalPayLabel.setText(totalPriceLabel.getText());

        couponApplyButton.setOnAction(event -> {
            // Applying coupon if not applied yet.
            if (couponApplyButton.getText().equals("Apply")) {
                String coupon = couponTextField.getText().trim();   // Get coupon code
                // TODO: Validate coupon
                System.out.println("Coupon" + coupon);
                couponTextField.setDisable(true);
                couponApplyButton.setText("De-active");

                // Demo code
                discountedLabel.setText("$" + String.format("%1$,.2f", discounted));
                totalPayLabel.setText(String.format("$" + "%1$,.2f", samplePrice - discounted));
            } else {
                couponTextField.setDisable(false);
                couponTextField.setText("");
                couponApplyButton.setText("Apply");
            }
        });

        cancelButton.setOnAction(event -> monashEats.getStage().setScene(previousScene));
        infoEditButton.setOnAction(event -> System.out.println("To edit delivery information"));
        placeButton.setOnAction(event -> {
            // TODO: Passing to backend for generating order.
            // Hint success once backend responded success.
            if (true) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Order Success");
                alert.setContentText("Your order has been successfully placed");
                alert.showAndWait();
                // goto history order after acknowledged.
                monashEats.gotoHistoryOrder(user);
            } else {    // Failure handle.

            }

            System.out.println("To place order");
        });
    }
}
