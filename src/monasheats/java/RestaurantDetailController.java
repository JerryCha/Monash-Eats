package monasheats.java;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class RestaurantDetailController {

    private Stage stage;

    private String restaurant;

    public RestaurantDetailController() {

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;

        nameTextField.setText(restaurant);
    }

    @FXML
    private Button cancelButton;
    @FXML
    private Button submitButton;

    @FXML
    private Button editCouponButton;
    @FXML
    private Button editMenuButton;

    @FXML
    private TextField nameTextField;

    @FXML
    private void initialize() {
        cancelButton.setOnAction(event -> {
            System.out.println("Cancel pressed @ Restaurant Detail");
            stage.close();
        });

        submitButton.setOnAction(event -> {
            // Debug print.
            System.out.println("Submit pressed @ Restaurant Detail");

            // TODO: Pass edited restaurant to backend.

            // Indicate success once received the success message.
            if (true) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Your modification has been successfully saved.");
                alert.showAndWait();
                stage.close();
            }

        });

        editCouponButton.setOnAction(event -> {
            System.out.println("Edit coupon");
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("edit_coupon.fxml"));
                Parent parent = loader.load();

                CouponManagementController cpMgntController = loader.getController();

                Stage stage = new Stage();
                stage.setTitle("Coupon Management");
                stage.setX(this.stage.getX()+50);
                stage.setY(this.stage.getY()+50);
                cpMgntController.setStage(stage);
                stage.setScene(new Scene(parent));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        editMenuButton.setOnAction(event -> {
            System.out.println("Edit menu");
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("edit_menu.fxml"));
                Parent parent = loader.load();

                MenuManagementController menuMgntController = loader.getController();

                Stage stage = new Stage();
                stage.setTitle("Edit Menu");
                stage.setX(this.stage.getX()+50);
                stage.setY(this.stage.getY()+50);
                menuMgntController.setStage(stage);
                stage.setScene(new Scene(parent));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
