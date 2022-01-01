package app;


import controller.OnlineShopController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.sql.SQLException;

public class OnlineShop extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/onlineShop.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("OnlineShop");
        stage.setResizable(true);
        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                try {
                    if (OnlineShopController.connection != null)
                        OnlineShopController.connection.close();
                } catch (SQLException e) {
                }
            }
        });
    }
}


