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

    /*
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("eco_searcher_template"));
        stage.setScene(scene);
        stage.setTitle("アイテムカタログ(アクロニア) Ver.1.0");
        stage.setResizable(true);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        fxmlLoader.setController(new Controller());
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }
    */
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new  FXMLLoader(getClass().getResource("eco_searcher_template.fxml"));
        loader.setController(new Controller());
        Parent root = loader.load();
        primaryStage.setTitle("アイテムカタログ(アクロニア) Ver.1.0");
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