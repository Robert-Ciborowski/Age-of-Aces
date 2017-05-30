package RC.Age_of_Aces.Model_Classes.Sprites;
 /*
 * Name: Robert Ciborowski
 * Date: 2017-01-23
 * Assignment: PongFrame Game
 * Description: A model class for sprites.
*/

// These are the imports.

import RC.Age_of_Aces.Model_Classes.Math.Rect;
import RC.Age_of_Aces.Model_Classes.Math.PathSegment;
import RC.Age_of_Aces.Model_Classes.Math.Rotation;

import java.util.Random;

// This is the Sprite class.
public class Sprite implements Cloneable {
    // These are the class's properties.
    protected Rect boundingBox;
    protected Rect originalPosition;
    protected PathSegment movement;
    protected Rotation rotation;
    protected boolean collidable = true;

    // This is the default constructor.
    public Sprite() {
        boundingBox = new Rect();
    }

    // These are some other constructors.
    public Sprite(Rect setBoundingBox) {
        setBoundingBox(setBoundingBox);
    }

    public Sprite(double setX, double setY, double setWidth, double setHeight) {
        setBoundingBox(setX, setY, setWidth, setHeight);
    }

    // These are some getters amd setters.
    public void setBoundingBox(Rect boundingBox) {
        this.boundingBox = boundingBox;
        if (originalPosition == null) {
            originalPosition = boundingBox;
        }
    }

    public void setBoundingBox(double setX, double setY, double setWidth, double setHeight) {
        boundingBox = new Rect(setX, setY, setWidth, setHeight);
        if (originalPosition == null) {
            originalPosition  = new Rect(setX, setY, setWidth, setHeight);
        }
    }

    public Rect getBoundingBox() {
        return boundingBox;
    }

    public Rect getOriginalPosition() {
        return originalPosition;
    }

    public void setOriginalPosition(Rect originalPosition) {
        this.originalPosition = originalPosition;
    }

    public void setOriginalPosition(double setX, double setY, double setWidth, double setHeight) {
        originalPosition  = new Rect(setX, setY, setWidth, setHeight);
    }

    public void setMovement(PathSegment m) {
        movement = m;
        movement.recalculateLineEquation(new RC.Age_of_Aces.Model_Classes.Math.Point(boundingBox.getXPosition(), boundingBox.getYPosition()));
    }

    public PathSegment getMovement() {
        return movement;
    }

    public Rotation getRotation() {
        return rotation;
    }

    public void setRotation(Rotation rotation) {
        this.rotation = rotation;
    }

    public boolean isCollidable() {
        return collidable;
    }

    public void setCollidable(boolean collidable) {
        this.collidable = collidable;
    }

    public void setRandomPositionAroundPoint(RC.Age_of_Aces.Model_Classes.Math.Point center, double distanceFromCentre) {
        Random random = new Random();
        double angle = random.nextFloat() * (Math.PI * 2);
        double newX, newY;
        newX = center.getX() + distanceFromCentre * Math.cos(angle);
        newY = center.getY() + distanceFromCentre * Math.sin(angle);
        setBoundingBox(newX, newY, boundingBox.getWidth(), boundingBox.getHeight());
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
