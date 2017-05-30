 /*
 * Name: Robert Ciborowski
 * Date: 23-01-2017
 * Assignment: Various (This is a generic class.)
 * Description: A simple rectangle class.
*/

package RC.Age_of_Aces.Model_Classes.Math;

/**
 * The <code>Rect</code> class is used to represent a rectangle.
 *
 * @date 23-01-2017
 * @author Robert Ciborowski
 */
public class Rect {
    /**
     * An enum used for getters and setters.
     */
    public enum RectProperties {X_POSITION, Y_POSITION, WIDTH, HEIGHT};

    /**
     * The dimensions that make up the rectangle.
     */
    private double xPosition, yPosition, width, height;

    /**
     * This is the default constructor.
     */
    public Rect() {
        xPosition = 0;
        yPosition = 0;
        width = 0;
        height = 0;
    }

    /**
     * A constructor that sets up the rectangle using dimension values.
     * @param setX The x value to be used for the <code>Rectangle</code>.
     * @param setY The y value to be used for the <code>Rectangle</code>.
     * @param setWidth The width value to be used for the <code>Rectangle</code>.
     * @param setHeight The height value to be used for the <code>Rectangle</code>.
     */
    public Rect(double setX, double setY, double setWidth, double setHeight) {
        xPosition = setX;
        yPosition = setY;
        width = setWidth;
        height = setHeight;
    }

    /**
     * A function for setting any property of the <code>Rectangle</code>.
     * @param value The value that the property should be set to.
     * @param property The property to be modified.
     */
    public void setProperty(double value, RectProperties property) {
        switch (property) {
            case X_POSITION:
                xPosition = value;
                break;
            case Y_POSITION:
                yPosition = value;
                break;
            case WIDTH:
                width = value;
                break;
            case HEIGHT:
                height = value;
                break;
        }
    }

    /**
     * A function for getting any property of the <code>Rectangle</code>.
     * @param property The property to be obtained.
     * @return The value of the property.
     */
    public double getProperty(RectProperties property) {
        switch (property) {
            case X_POSITION:
                return xPosition;
            case Y_POSITION:
                return yPosition;
            case WIDTH:
                return width;
            case HEIGHT:
                return height;
            default:
                return 0;
        }
    }

    /**
     * This returns the x position of the line.
     * @return The x position of the <code>Rectangle</code>.
     */
    public double getXPosition() {
        return xPosition;
    }

    /**
     * This sets the x position of the line.
     * @param xPosition The value to be used for the x position.
     */
    public void setXPosition(double xPosition) {
        this.xPosition = xPosition;
    }

    /**
     * This returns the y position of the line.
     * @return The y position of the <code>Rectangle</code>.
     */
    public double getYPosition() {
        return yPosition;
    }

    /**
     * This sets the y position of the line.
     * @param yPosition The value to be used for the y position.
     */
    public void setYPosition(double yPosition) {
        this.yPosition = yPosition;
    }

    /**
     * This returns the width of the line.
     * @return The width of the <code>Rectangle</code>.
     */
    public double getWidth() {
        return width;
    }

    /**
     * This sets the width of the line.
     * @param width The value to be used for the width.
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * This returns the height of the line.
     * @return The height of the <code>Rectangle</code>.
     */
    public double getHeight() {
        return height;
    }

    /**
     * This sets the height of the line.
     * @param height The value to be used for the height.
     */
    public void setHeight(double height) {
        this.height = height;
    }
}
