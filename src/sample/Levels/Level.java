package sample.Levels;


import sample.Cast.ObjectInteraction;

import java.util.List;

public interface Level {
    void moveEnemies(List<ObjectInteraction> enemies);// Движение объектов
    double probability();// Вероятность появления объектов взаимодействия
    double getScore();// Возвращает количество очков в конкретный момен времени
    double getSpeed();// Возвращает текущую скорость объектов
    void reset();// Востанавливает начатльные настроки объектов
}
