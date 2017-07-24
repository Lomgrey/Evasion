package sample.Cast;


import javafx.scene.shape.Shape;

public interface ObjectInteraction {
    void generatePosition();// Генирация начальной позиции
    void move(double speed);// Движение объекта
    int getDamage();// Возвращает наносимый урон
    Shape getShape();// Возвращает фигуру
}
