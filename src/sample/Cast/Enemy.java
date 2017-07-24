package sample.Cast;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;


public class Enemy implements ObjectInteraction{
    private int W;
    private int H;
    private int size = 50;// Размер стороны
    private int damage = -1;// Урон

    Point vector;   //определяет направление движения
    Rectangle rect;

    public Enemy(Point vector, int W, int H){
        this.W = W;             // Установка высоты и ширены окна
        this.H = H;             // для генирации начальной позиции
        this.vector = vector;   // вектор движения
        rect = new Rectangle(size, size, Color.DARKORANGE);
    }

    //Генерирует начальную позицию объекта
    public void generatePosition(){
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

    public void move(double speed){
        // Скорость умножается на вктор.
        // Т.к. одно из значений будет 0
        // => движение будет происходить толькко по одной оси
        rect.setTranslateX(rect.getTranslateX() + speed * vector.getX());
        rect.setTranslateY(rect.getTranslateY() + speed * vector.getY());
    }

    public int getDamage(){
        return damage;
    }

    public Rectangle getShape(){
        return rect;
    }

}
