package sample.Levels;


import sample.Cast.ObjectInteraction;

import java.util.List;

public class Hard implements Level {
    // Начальное количество очков (увеличивается на 0.01 за кадр)
    private double score = 0;
    // Скорость перемещения объектов (увел. на 0.005 за кадр)
    private double speed = 5.2;
    // Вероятность появления врагов (увел. на 0.00001 за кадр)
    private double probability = 0.07;

    @Override
    public void moveEnemies(List<ObjectInteraction> enemies) {
        for (ObjectInteraction o : enemies)
            o.move(speed);
        if(speed <= 10) {
            speed += 0.005;
            probability += 0.0001;
        }
        score += 0.08;
    }

    @Override
    public double probability() {
        return probability;
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
        speed = 5.2;
    }
}
