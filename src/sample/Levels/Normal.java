package sample.Levels;


import sample.Cast.ObjectInteraction;

import java.util.List;

public class Normal implements Level{
    // Начальное количество очков (увеличивается на 0.01 за кадр)
    private double score = 0;
    private double speed = 6.5;// Скорость перемещения объектов

    @Override
    public void moveEnemies(List<ObjectInteraction> enemies) {
        for (ObjectInteraction o : enemies)
            o.move(speed);
        score += 0.04;
    }

    @Override
    public double probability() {
        return 0.075;
    }

    @Override
    public double getScore() {
        return score;
    }

    @Override
    public double getSpeed() {
        return speed;
    }

    @Override
    public void reset() {
        score = 0;
    }
}
