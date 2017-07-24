package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //размеры окна
        int W = 600;
        int H = 600;

        Pane root = new Pane();// корневая панель для отрисовки
        Scene scene = new Scene(root, W, H);// Установка окна игры
        // вызов метоода инициализации игры в классе Controller
        new Controller().init(scene);

        primaryStage.setTitle("To Evade");// Оглавление окна
        primaryStage.setResizable(false);// Запрещение изменения размеров окна
        primaryStage.setScene(scene); // Установка сцены
        primaryStage.show();// Показ окна
    }


    public static void main(String[] args) {
        launch(args);
    }
}
