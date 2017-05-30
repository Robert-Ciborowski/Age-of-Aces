package RC.Age_of_Aces.Model_Classes.Maps;
 /*
 * Name: Robert Ciborowski
 * Date: 2017-05-12
 * Assignment:
 * Description:
*/

import RC.Age_of_Aces.Model_Classes.Math.PathSegment;
import RC.Age_of_Aces.Model_Classes.Math.Point;
import RC.Age_of_Aces.Model_Classes.Sprites.ImageSprite;

import java.util.ArrayList;

public class Unit {
    public enum UnitType {VILLAGER, MILITIA, SPEARMAN};
    private UnitType type;
    private ImageSprite image = new ImageSprite();
    private int imageID;
    private Point locationOnMap;
    private int health, attack, direction, step;
    private double speed;



    private ArrayList<Point> movements;
    private PathSegment currentMovement;

    public Unit() {
        type = UnitType.VILLAGER;
        imageID = 0;
        locationOnMap = new Point(0, 0);
        health = 10;
        attack = 1;
        speed = 1;
        direction = 0;
        step = 0;
    }

    public UnitType getType() {
        return type;
    }

    public void setType(UnitType type) {
        this.type = type;
    }

    public ImageSprite getImage() {
        return image;
    }

    public void setImage(ImageSprite image) {
        this.image = image;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public Point getLocationOnMap() {
        return locationOnMap;
    }

    public void setLocationOnMap(Point locationOnMap) {
        this.locationOnMap = locationOnMap;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public ArrayList<Point> getMovements() {
        return movements;
    }

    public void setMovements(ArrayList<Point> movements) {
        this.movements = movements;
    }

    public PathSegment getCurrentMovement() {
        return currentMovement;
    }

    public void setCurrentMovement(PathSegment currentMovement) {
        this.currentMovement = currentMovement;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public void switchStep() {
        switch (step) {
            case 0:
                step = 1;
                break;
            case 1:
                step = 2;
                break;
            case 2:
                step = 1;
                break;
        }
    }
}
