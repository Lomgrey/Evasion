package sample.Collide;


import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Collide {

    public static boolean isColliding(Circle c, Rectangle r){
        double closestX = c.getTranslateX();
        double closestY = c.getTranslateY();

        if (closestX < r.getTranslateX())
            closestX = r.getTranslateX();
        else if (closestX > r.getTranslateX() + r.getWidth())
            closestX = r.getTranslateX() + r.getWidth();

        if (closestY < r.getTranslateY())
            closestY = r.getTranslateY();
        else if (closestY > r.getTranslateY() + r.getHeight())
            closestY = r.getTranslateY() + r.getHeight();

        return new Vector2(c.getTranslateX(), c.getTranslateY())
                .distSquared(closestX, closestY)
                < c.getRadius() * c.getRadius();
    }
}
