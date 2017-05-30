/*
 * Name: Robert Ciborowski
 * Date: 31-03-2017
 * Assignment: Various (This is a generic class.)
 * Description: A class used to represent a point.
*/

package RC.Age_of_Aces.Model_Classes.Math;

/**
 * The <code>Point</code> class is used to represent a point.
 * It was made because Java's old <code>Point</code> class
 * used integers and was annoying to deal with.
 *
 * @date 31-03-2017
 * @author Robert Ciborowski
 */
public class Point {
    /**
     * The x and y coordinates of the <code>Point</code>.
     */
    double x = 0, y = 0;

    /**
     * This is the default constructor.
     */
    public Point() {

    }

    /**
     * A constructor that sets up the point using x and y values.
     * @param x The x position of the <code>Point</code>.
     * @param y The y position of the <code>Point</code>.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * A function that sets up the point using x and y values.
     * @param x The x position of the <code>Point</code>.
     * @param y The y position of the <code>Point</code>.
     */
    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * This returns the x position of the line.
     * @return The x position of the <code>Point</code>.
     */
    public double getX() {
        return x;
    }

    /**
     * This sets the x position of the line.
     * @param x The value to be used for x.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * This returns the y position of the line.
     * @return The y position of the <code>Point</code>.
     */
    public double getY() {
        return y;
    }

    /**
     * This sets the y position of the line.
     * @param y The value to be used for y.
     */
    public void setY(double y) {
        this.y = y;
    }

    public void convertFromJavaPoint(java.awt.Point javaPoint) {
        x = javaPoint.getX();
        y = javaPoint.getY();
    }
}
