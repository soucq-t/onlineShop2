package app;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class OnlineShop extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root= FXMLLoader.load(getClass().getResource("/onlineShop.fxml"));
        Scene scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("OnlineShop");
        stage.setResizable(true);
        stage.show();
    }
}


