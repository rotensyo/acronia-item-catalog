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
        loader.setController(new Controller());
        Parent root = loader.load();
        primaryStage.setTitle("アイテムカタログ(アクロニア) Ver.1.1");
        Image icon = new Image(getClass().getResourceAsStream("icon.png"));
        primaryStage.getIcons().add( icon );
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}