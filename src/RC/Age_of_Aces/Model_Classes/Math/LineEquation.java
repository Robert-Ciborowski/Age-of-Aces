/*
 * Name: Robert Ciborowski
 * Date: 30-03-2017
 * Assignment: Various (This is a generic class.)
 * Description: A class used to represent a line equation.
*/

package RC.Age_of_Aces.Model_Classes.Math;

/**
 * The <code>LineEquation</code> class is used to represent a line
 * using the line equation <i>y = mx + b</i>.
 *
 * @date 30-03-2017
 * @author Robert Ciborowski
 */
public class LineEquation {
    /**
     * The m and b properties of the line equation.
     * <p> The line equation is mathematically represented as
     * y = mx + b.</p>
     */
    private double m = 1, b = 0;

    /**
     * This is the default constructor.
     */
    public LineEquation() {

    }

    /**
     * This calculates the line equation based on 2 points on the line.
     * @param pointA The first point on the line.
     * @param pointB The second point on the line.
     */
    public void calculate(RC.Age_of_Aces.Model_Classes.Math.Point pointA, RC.Age_of_Aces.Model_Classes.Math.Point pointB) {
        m = (pointB.getY() - pointA.getY()) / (pointB.getX() - pointA.getX());
        b = -(m * pointA.x - pointA.y);
    }

    /**
     * This returns the slope value (m) of the line.
     * @return The m value of the equation.
     */
    public double getM() {
        return m;
    }

    /**
     * This sets the slope value (m) of the line.
     * @param m The value to be used for m.
     */
    public void setM(double m) {
        this.m = m;
    }

    /**
     * This returns the y-intercept value (b) of the line.
     * @return The b value of the equation.
     */
    public double getB() {
        return b;
    }

    /**
     * This sets the y-intercept value (b) of the line.
     * @param b The value to be used for b.
     */
    public void setB(double b) {
        this.b = b;
    }

    /**
     * This returns a new <code>LineEquation</code> that represents
     * a perpendicular to the current line.
     * @return A new object for the perpendicular line.
     */
    public LineEquation getPerpendicular() {
        LineEquation perpendicular = new LineEquation();
        perpendicular.setB(b);
        perpendicular.setM(-(1 / m));
        return perpendicular;
    }

    public Point getIntersection(LineEquation equation2) {
        Point intersection = new Point();
        intersection.setX((equation2.getB() - b) / (m - equation2.getM()));
        intersection.setY(m * intersection.getX() + b);
        return intersection;
    }

    public void setBUsingPoint(Point point) {
        b = -(m * point.getX()) + point.getY();
    }
}
