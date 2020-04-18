package org.acronia;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.acronia.controller.Controller;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new  FXMLLoader(getClass().getResource("eco_searcher_template.fxml"));
        Controller controller = new Controller();
        controller.setThisStage(primaryStage);
        loader.setController(controller);
        Parent root = loader.load();
        primaryStage.setTitle("アイテムカタログ(アクロニア) Ver.1.0.0");
        Image icon = new Image(getClass().getResourceAsStream("new_icon.png"));
        primaryStage.getIcons().add( icon );
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

}