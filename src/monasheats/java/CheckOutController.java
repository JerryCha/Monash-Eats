package monasheats.java;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CheckOutController {

    private Main main;

    private String user;

    public CheckOutController() {

    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setUser(String user) {
        this.user = user;
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
    private void initialize() {
        double samplePrice = Math.random()*114514;
        double discounted = Math.random()*114;

        deliveryFeeLabel.setText("Free");
        totalPriceLabel.setText("$" + String.format("%1$,.2f", samplePrice));
        totalPayLabel.setText(totalPriceLabel.getText());

        couponApplyButton.setOnAction(event -> {
            discountedLabel.setText("$" + String.format("%1$,.2f", discounted));
            totalPayLabel.setText(String.format("%1$,.2f", samplePrice - discounted));
        });

        //cancelButton.setOnAction(event -> main.gotoCart(user));
        infoEditButton.setOnAction(event -> System.out.println("To edit delivery information"));
        placeButton.setOnAction(event -> System.out.println("To place order"));
    }
}
