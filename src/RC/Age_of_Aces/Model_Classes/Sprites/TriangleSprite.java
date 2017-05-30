/*
 * Name: Robert Ciborowski
 * Date: 2017-04-17
 * Assignment:
 * Description:
*/

package RC.Age_of_Aces.Model_Classes.Sprites;

import RC.Age_of_Aces.Model_Classes.Math.LineEquation;
import RC.Age_of_Aces.Model_Classes.Math.Rect;
import RC.Age_of_Aces.Model_Classes.Math.Rotation;

import java.awt.*;

public class TriangleSprite extends Sprite {
    private LineSprite sides[] = new LineSprite[3];

    public TriangleSprite() {
        super();
        sides[0] = new LineSprite();
        sides[1] = new LineSprite();
        sides[2] = new LineSprite();
    }

    public TriangleSprite(RC.Age_of_Aces.Model_Classes.Math.Point vertex1, RC.Age_of_Aces.Model_Classes.Math.Point vertex2, RC.Age_of_Aces.Model_Classes.Math.Point vertex3) {
        super();
        sides[0] = new LineSprite();
        sides[1] = new LineSprite();
        sides[2] = new LineSprite();
        setVertexes(vertex1, vertex2, vertex3);
    }

    public void setVertexes(RC.Age_of_Aces.Model_Classes.Math.Point vertex1, RC.Age_of_Aces.Model_Classes.Math.Point vertex2, RC.Age_of_Aces.Model_Classes.Math.Point vertex3) {
        sides[0].setPointA(vertex1);
        sides[0].setPointB(vertex2);
        sides[1].setPointA(vertex2);
        sides[1].setPointB(vertex3);
        sides[2].setPointA(vertex3);
        sides[2].setPointB(vertex1);
        for (int i = 0; i < 3; i++) {
            sides[i].recalculateBoundingBoxUsingPoints();
        }
    }

    public void setColours(Color colour1, Color colour2, Color colour3) {
        sides[0].setColour(colour1);
        sides[1].setColour(colour2);
        sides[2].setColour(colour3);
    }

    public void setColour(Color colour) {
        setColours(colour, colour, colour);
    }

    public Rotation getRotation() {
        return rotation;
    }

    public LineSprite[] getSides() {
        return sides;
    }

    public void setRotation(Rotation rotation) {
        this.rotation = rotation;
        // Due to the fact that Java passes objects by reference, a new Rotation object
        // must be made for each side. Otherwise, the RuleManager will be modifying the
        // same rotation object 3 times when rotating the lines, causing a cool but unwanted
        // effect. This is another reason as to why Java is bad.
        for (int i = 0; i < 3; i++) {
            sides[i].setRotation(new Rotation(rotation.getCenterOfRotation(), rotation.getMagnitude()));
        }
    }

    @Override
    public void setBoundingBox(Rect boundingBox) {
        System.out.println("setBoundingBox(Rect) is not applicable to this sprite!");
    }

    @Override
    public void setBoundingBox(double setX, double setY, double setWidth, double setHeight) {
        System.out.println("setBoundingBox(double, double, double, double) is not applicable to this sprite!");
    }

    public void increaseSize(double amount) {
        for (int whichPoint = 0; whichPoint < 3; whichPoint++) {
            int pointA = whichPoint, pointB = whichPoint + 1;
            if (pointB >= 3) {
                pointB = 0;
            }

            LineEquation side0Perpendicular = sides[pointA].getLineEquation().getPerpendicular();
            RC.Age_of_Aces.Model_Classes.Math.Point middleOfSide0 = new RC.Age_of_Aces.Model_Classes.Math.Point((sides[pointA].getPointA().getX() + sides[pointA].getPointB().getX()) / 2, (sides[pointA].getPointA().getY() + sides[pointA].getPointB().getY()) / 2);
            // b = -(m * pointA.x - pointA.y);
            side0Perpendicular.setB(-(side0Perpendicular.getM() * middleOfSide0.getX() - middleOfSide0.getY()));

            LineEquation side1Perpendicular = sides[pointB].getLineEquation().getPerpendicular();
            RC.Age_of_Aces.Model_Classes.Math.Point middleOfSide1 = new RC.Age_of_Aces.Model_Classes.Math.Point((sides[pointB].getPointA().getX() + sides[pointB].getPointB().getX()) / 2, (sides[pointB].getPointA().getY() + sides[pointB].getPointB().getY()) / 2);
            // b = -(m * pointA.x - pointA.y);
            side1Perpendicular.setB(-(side1Perpendicular.getM() * middleOfSide1.getX() - middleOfSide1.getY()));

            RC.Age_of_Aces.Model_Classes.Math.Point intersection = new RC.Age_of_Aces.Model_Classes.Math.Point();
            intersection.setX((side1Perpendicular.getB() - side0Perpendicular.getB()) / (side0Perpendicular.getM() - side1Perpendicular.getM()));
            intersection.setY(side0Perpendicular.getM() * intersection.getX() + side0Perpendicular.getB());

            RC.Age_of_Aces.Model_Classes.Math.Point sharedPoint = new RC.Age_of_Aces.Model_Classes.Math.Point(0, 0);
            if (sides[pointA].getPointA() == sides[pointB].getPointA() || sides[pointA].getPointB() == sides[pointB].getPointA()) {
                sharedPoint = sides[pointB].getPointA();
            } else if (sides[pointA].getPointA() == sides[pointB].getPointB() || sides[pointA].getPointB() == sides[pointB].getPointB()) {
                sharedPoint = sides[pointB].getPointB();
            } else {
                // RC.Age_of_Aces.Model_Classes.Math.Pointers of triangle points have somehow become different?!?!?
            }
            
            double rise = sharedPoint.getY() - intersection.getY(), run = sharedPoint.getX() - intersection.getX();
            RC.Age_of_Aces.Model_Classes.Math.Point newPoint = new RC.Age_of_Aces.Model_Classes.Math.Point(sharedPoint.getX() + (amount * run), sharedPoint.getY() + (amount * rise));

            if (sides[pointA].getPointA() == sides[pointB].getPointA()) {
                sides[pointA].setPointA(newPoint);
                sides[pointB].setPointA(newPoint);
            } else if (sides[pointA].getPointB() == sides[pointB].getPointA()) {
                sides[pointA].setPointB(newPoint);
                sides[pointB].setPointA(newPoint);
            } else if (sides[pointA].getPointA() == sides[pointB].getPointB()) {
                sides[pointA].setPointA(newPoint);
                sides[pointB].setPointB(newPoint);
            } else if (sides[pointA].getPointB() == sides[pointB].getPointB()) {
                sides[pointA].setPointB(newPoint);
                sides[pointB].setPointB(newPoint);
            } else {
                // dfdfdf
            }
        }
        for (int i = 0; i < 3; i++) {
            sides[i].recalculateBoundingBoxUsingPoints();
        }
        rotation.setCenterOfRotation(getCentre());
    }
    
    public RC.Age_of_Aces.Model_Classes.Math.Point getCentre() {
        LineEquation side0Perpendicular = sides[0].getLineEquation().getPerpendicular();
        RC.Age_of_Aces.Model_Classes.Math.Point middleOfSide0 = new RC.Age_of_Aces.Model_Classes.Math.Point((sides[0].getPointA().getX() + sides[0].getPointB().getX()) / 2, (sides[0].getPointA().getY() + sides[0].getPointB().getY()) / 2);
        // b = -(m * 0.x - 0.y);
        side0Perpendicular.setB(-(side0Perpendicular.getM() * middleOfSide0.getX() - middleOfSide0.getY()));

        LineEquation side1Perpendicular = sides[1].getLineEquation().getPerpendicular();
        RC.Age_of_Aces.Model_Classes.Math.Point middleOfSide1 = new RC.Age_of_Aces.Model_Classes.Math.Point((sides[1].getPointA().getX() + sides[1].getPointB().getX()) / 2, (sides[1].getPointA().getY() + sides[1].getPointB().getY()) / 2);
        // b = -(m * 0.x - 0.y);
        side1Perpendicular.setB(-(side1Perpendicular.getM() * middleOfSide1.getX() - middleOfSide1.getY()));

        RC.Age_of_Aces.Model_Classes.Math.Point intersection = new RC.Age_of_Aces.Model_Classes.Math.Point();
        intersection.setX((side1Perpendicular.getB() - side0Perpendicular.getB()) / (side0Perpendicular.getM() - side1Perpendicular.getM()));
        intersection.setY(side0Perpendicular.getM() * intersection.getX() + side0Perpendicular.getB());
        return intersection;
    }
}
