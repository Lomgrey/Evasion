package sample.Collide;


public class Vector2{
    private double y;
    private double x;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distSquared(double x, double y){
        double distX = this.x - x;
        double distY = this.y - y;

        return distX*distX + distY*distY;
    }
}
