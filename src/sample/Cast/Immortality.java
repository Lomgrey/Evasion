package sample.Cast;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.awt.*;


public class Immortality implements ObjectInteraction{
    private int W;
    private int H;
    private int size = 50;// Размер стороны
    private int damage = 0;// Урон

    Point vector;   //определяет направление движения
    Rectangle rect;

    public Immortality(Point vector, int W, int H) {
        this.W = W;             // Установка высоты и ширены окна
        this.H = H;             // для генерации начальной позиции
        this.vector = vector;   // вектор движения
        rect = new javafx.scene.shape.Rectangle(size, size, javafx.scene.paint.Color.DARKBLUE);
    }

    @Override
    public void generatePosition() {
        if(vector.getX() != 0){
            if(vector.getX() > 0)
                rect.setTranslateX(0);
            else
                rect.setTranslateX(W);
            rect.setTranslateY((int)(Math.random() * 12) * size);
        }else{
            if(vector.getY() > 0)
                rect.setTranslateY(0);
            else
                rect.setTranslateY(H);
            rect.setTranslateX((int)(Math.random() * 12) * size);
        }

    }

    @Override
    public void move(double speed){
        // Скорость умножается на вектор.
        // Т.к. одно из значений будет 0
        // => движение будет происходить только по одной оси
        rect.setTranslateX(rect.getTranslateX() + speed * 0.6 * vector.getX());
        rect.setTranslateY(rect.getTranslateY() + speed * 0.6 * vector.getY());
    }

    @Override
    public int getDamage(){
        return damage;
    }

    @Override
    public Shape getShape() {
        return rect;
    }
}
