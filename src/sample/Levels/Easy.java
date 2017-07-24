package sample.Levels;


import sample.Cast.ObjectInteraction;

import java.util.List;

public class Easy implements Level{
    private double score = 0;   // Начальное количество очков (увеличивается на 0.01 за кадр)
    private double speed = 4.4; // Скорость перемещения объектов

    @Override
    public void moveEnemies(List<ObjectInteraction> enemies) {
        for (ObjectInteraction o : enemies)
            o.move(speed);
        score += 0.01;// Увеличивает количество очков на 0.01 за каждый кадр
    }

    @Override
    public double probability() {
        return 0.06;
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
