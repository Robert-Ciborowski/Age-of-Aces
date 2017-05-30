package RC.Age_of_Aces.Model_Classes.Sprites;
 /*
 * Name: Robert Ciborowski
 * Date: 2017-03-27
 * Assignment:
 * Description:
*/

import RC.Age_of_Aces.Model_Classes.Math.*;

import java.awt.*;

public class LineSprite extends Sprite {
    private LineEquation lineEquation = new LineEquation();
    private RC.Age_of_Aces.Model_Classes.Math.Point pointA = new RC.Age_of_Aces.Model_Classes.Math.Point(), pointB = new RC.Age_of_Aces.Model_Classes.Math.Point();

    private Color colour;
    private int thickness;


    public LineSprite() {
        super();
    }

    public LineSprite(Rect setBoundingBox) {
        setBoundingBox(setBoundingBox);
    }

    public LineSprite(double setX, double setY, double setWidth, double setHeight) {
        setBoundingBox(setX, setY, setWidth, setHeight);
    }

    @Override
    public void setBoundingBox(Rect boundingBox) {
        this.boundingBox = boundingBox;
        if (originalPosition == null) {
            originalPosition = boundingBox;
        }
        pointA.setLocation(boundingBox.getXPosition(), boundingBox.getYPosition());
        pointB.setLocation(boundingBox.getXPosition() + boundingBox.getWidth(), boundingBox.getYPosition() + boundingBox.getHeight());
        lineEquation.calculate(pointA, pointB);
    }

    @Override
    public void setBoundingBox(double setX, double setY, double setWidth, double setHeight) {
        boundingBox = new Rect(setX, setY, setWidth, setHeight);
        if (originalPosition == null) {
            originalPosition  = new Rect(setX, setY, setWidth, setHeight);
        }
        pointA.setLocation(setX, setY);
        pointB.setLocation(setX + setWidth, setY + setHeight);
        lineEquation.calculate(pointA, pointB);
    }

    public void recalculateBoundingBoxUsingPoints() {
        setBoundingBox(pointA.getX(), pointA.getY(), pointB.getX() - pointA.getX(), pointB.getY() - pointA.getY());
    }

    public boolean isWithinLine(RC.Age_of_Aces.Model_Classes.Math.Point point) {
        // This function returns whether or not a point is within the rectangle that the line forms.
        if (point.getX() >= Math.min(pointA.getX(), pointB.getX()) && point.getX() <= Math.max(pointA.getX(), pointB.getX()) &&
                point.getY() >= Math.min(pointA.getY(), pointB.getY()) && point.getY() <= Math.max(pointA.getY(), pointB.getY())) {
            return true;
        }
        return false;
    }

    public Color getColour() {
        return colour;
    }

    public void setColour(Color colour) {
        this.colour = colour;
    }

    public int getThickness() {
        return thickness;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    public LineEquation getLineEquation() {
        return lineEquation;
    }

    public RC.Age_of_Aces.Model_Classes.Math.Point getPointA() {
        return pointA;
    }

    public RC.Age_of_Aces.Model_Classes.Math.Point getPointB() {
        return pointB;
    }

    public void setPointA(RC.Age_of_Aces.Model_Classes.Math.Point pointA) {
        this.pointA = pointA;
    }

    public void setPointB(RC.Age_of_Aces.Model_Classes.Math.Point pointB) {
        this.pointB = pointB;
    }
}
