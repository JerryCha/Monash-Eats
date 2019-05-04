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

    public void gotoRestaurant(String restaurant) {
        System.out.println(restaurant);
    }

}
