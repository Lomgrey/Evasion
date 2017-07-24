package sample.Cast;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;


public class Player {
    private int size = 30;// радиус
    private int life = 3;// количество жизней
    private Pane root;

    private Image image;
    private List<ImageView> imageList;
    private Circle circle;

    public Player(Pane root){
        this.root = root;// Устанавливается панель куда будет рисоваться игрок
        circle = new Circle(size, Color.DARKCYAN);// Инициализация Круга (игрока)
        // Закрузка картинки сердца (показывает количество жизней)
        image = new Image(getClass().getResourceAsStream("hart1.png"));
        imageList = new ArrayList<>();
        startPosition();// Стартовая позиция: центр сцены
    }

    public void startPosition(){
        circle.setTranslateX(root.getWidth() / 2);
        circle.setTranslateY(root.getHeight() / 2);
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
        drawLife();
    }

    public int getSize() {
        return size;
    }

    private void drawLife(){
        // Если уже отрисованны жизни, то стираем их
        if(imageList.size() != 0) {
            root.getChildren().removeAll(imageList);
            imageList.clear();
        }
        int x = 50;// Х-координата первой картинки
        for (int i = 0; i < life; i++) {
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(21.25);   // Устанавливается размер
            imageView.setFitHeight(18.75);  // изображения
            imageView.setTranslateX(x + (i * 23));          // Устанавливается позиция
            imageView.setTranslateY(root.getHeight() - 25); // изображения
            imageList.add(imageView);// Добовляется в список всех изображений
            root.getChildren().add(imageView);// Изображение добовляется на форму
        }
    }

    public Circle getShape(){
        return circle;
    }
}
