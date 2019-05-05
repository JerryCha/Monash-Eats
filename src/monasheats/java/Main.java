package monasheats.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("home_page.fxml"));

        stage.setTitle("Monash Eats");
        stage.setScene(new Scene(loader.load()));
        stage.show();

        Controller mainController = loader.getController();
        mainController.setMain(this);
    }

    public Stage getStage() {
        return stage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void gotoLogin() {
        try {
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(getClass().getResource("login.fxml"));

           stage.setScene(new Scene(loader.load()));

           LoginController loginController = loader.getController();
           loginController.setMain(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gotoSearchRestaurant(String username) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("restaurant_list.fxml"));

            Parent parent = loader.load();
            stage.setScene(new Scene(parent));

            RestaurantSearchController restaurantSearchController = loader.getController();
            restaurantSearchController.setUser(username);
            restaurantSearchController.setMain(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gotoRestaurant(String restaurant, String user) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("restaurant.fxml"));

            Parent parent = loader.load();
            stage.setScene(new Scene(parent));

            RestaurantController restaurantController = loader.getController();
            restaurantController.setRestaurant(restaurant);
            restaurantController.setUser(user);
            restaurantController.setMain(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gotoCart(String user) {
        CartController cartController = sceneLoader("cart.fxml").getController();
        cartController.setMain(this);
        cartController.setUser(user);
    }

    public void gotoCheckOut(String user) {
        CheckOutController checkOutController = sceneLoader("confirm_order.fxml").getController();
        checkOutController.setMain(this);
        checkOutController.setUser(user);
    }

    // Temporary
    public void gotoHistoryOrder(String user) {
        OrderViewController orderViewController = sceneLoader("view_past_order.fxml").getController();
        orderViewController.setMain(this);
        orderViewController.setUser(user);
    }

    public void gotoRegister() {
        RegisterController registerController = sceneLoader("register.fxml").getController();
        registerController.setMain(this);
    }

    public void gotoCustomerManagement(String user) {
        CustomerManagementController cstmManageController = sceneLoader("user_manage.fxml").getController();
        cstmManageController.setMain(this);
        cstmManageController.setUser(user);
    }

    public void gotoRestaurantManagement(String user) {
        RestaurantManagementController rtrMngmController = sceneLoader("restaurant_list_owner.fxml").getController();
        rtrMngmController.setMain(this);
        rtrMngmController.setUser(user);
    }

    private FXMLLoader sceneLoader(String resourcePath) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(resourcePath));

            Parent parent = loader.load();

            stage.setScene(new Scene(parent));

            return loader;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
