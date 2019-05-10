package monasheats.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MonashEats extends Application {

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
        mainController.setMonashEats(this);
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
           loginController.setMonashEats(this);
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
            restaurantSearchController.setMonashEats(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gotoRestaurant(String restaurant, String user, Scene currentScene) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("restaurant.fxml"));

            Parent parent = loader.load();
            stage.setScene(new Scene(parent));

            RestaurantController restaurantController = loader.getController();
            restaurantController.setRestaurant(restaurant);
            restaurantController.setUser(user);
            restaurantController.setMonashEats(this);
            restaurantController.setPreviousScene(currentScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gotoCart(String user, Scene currentScene) {
        CartController cartController = sceneLoader("cart.fxml").getController();
        cartController.setMonashEats(this);
        cartController.setUser(user);
        cartController.setPreviousScene(currentScene);
    }

    public void gotoCheckOut(String user, Scene currentScene) {
        CheckOutController checkOutController = sceneLoader("confirm_order.fxml").getController();
        checkOutController.setMonashEats(this);
        checkOutController.setUser(user);
        checkOutController.setPreviousScene(currentScene);
    }

    // Temporary
    public void gotoHistoryOrder(String user) {
        OrderViewController orderViewController = sceneLoader("view_past_order.fxml").getController();
        orderViewController.setMonashEats(this);
        orderViewController.setUser(user);
    }

    public void gotoRegister() {
        RegisterController registerController = sceneLoader("register.fxml").getController();
        registerController.setMonashEats(this);
    }

    public void gotoCustomerManagement(String user) {
        CustomerManagementController cstmManageController = sceneLoader("user_manage.fxml").getController();
        cstmManageController.setMonashEats(this);
        cstmManageController.setUser(user);
    }

    public void gotoRestaurantManagement(String user) {
        RestaurantManagementController rtrMngmController = sceneLoader("restaurant_list_owner.fxml").getController();
        rtrMngmController.setMonashEats(this);
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
