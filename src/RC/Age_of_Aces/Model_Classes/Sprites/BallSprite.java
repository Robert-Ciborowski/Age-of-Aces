package RC.Age_of_Aces.Model_Classes.Sprites;
 /*
 * Name: Robert Ciborowski
 * Date: 2017-03-27
 * Assignment:
 * Description:
*/

import RC.Age_of_Aces.Model_Classes.Math.PathSegment;

import java.awt.*;

public class BallSprite extends Sprite {
    private double radius;
    private Color colour;

    public BallSprite() {
        super();
    }

    public BallSprite(double setX, double setY, double setRadius) {
        super(setX, setY, setRadius * 2, setRadius * 2);
        radius = setRadius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Color getColour() {
        return colour;
    }

    @Override
    public void setMovement(PathSegment m) {
        movement = m;
        movement.recalculateLineEquation(new RC.Age_of_Aces.Model_Classes.Math.Point(boundingBox.getXPosition() + radius, boundingBox.getYPosition() + radius));
    }

    public void setColour(Color colour) {
        this.colour = colour;
    }
}