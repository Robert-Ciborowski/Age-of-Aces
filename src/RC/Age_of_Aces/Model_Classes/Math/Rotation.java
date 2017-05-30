/*
 * Name: Robert Ciborowski
 * Date: 16-04-2017
 * Assignment: Various (This is a generic class.)
 * Description: A model class used to represent a rotation using a center point,
 *              magnitude and angle.
*/

package RC.Age_of_Aces.Model_Classes.Math;

import RC.Age_of_Aces.Model_Classes.Math.Point;

/**
 * The <code>Rotation</code> class is used to represent a rotation.
 * It uses a center point to rotate around, a magnitude to store the
 * angle of rotation per unit of time and the current angle that the
 * object using the rotation is currently at.
 *
 * @date 16-04-2017
 * @author Robert Ciborowski
 */
public class Rotation {
    /**
     * The center of rotation.
     */
    private RC.Age_of_Aces.Model_Classes.Math.Point centerOfRotation;

    /**
     * The angle to rotate at per unit of time.
     */
    private double magnitude;

    /**
     * The current angle of the object using the <code>Rotation</code>.
     */
    private double angle;

    /**
     * The last time the rotation was used.
     */
    private long lastRotationTime = System.currentTimeMillis();

    /**
     * This is the default constructor.
     */
    public Rotation() {
        centerOfRotation = new RC.Age_of_Aces.Model_Classes.Math.Point(0, 0);
        magnitude = 0;
    }

    /**
     * A constructor that sets up the rotation's essential properties.
     * @param centerOfRotation The center of rotation to be used by the <code>Rotation</code>.
     * @param magnitude The magnitude to be used by the <code>Rotation</code>.
     */
    public Rotation(RC.Age_of_Aces.Model_Classes.Math.Point centerOfRotation, double magnitude) {
        this.centerOfRotation = centerOfRotation;
        this.magnitude = magnitude;
    }

    /**
     * This returns the center of rotation.
     * @return The center of rotation of the <code>Rotation</code>
     */
    public RC.Age_of_Aces.Model_Classes.Math.Point getCenterOfRotation() {
        return centerOfRotation;
    }

    /**
     * This sets the center of rotation.
     * @param centerOfRotation The value to be used for the center of rotation.
     */
    public void setCenterOfRotation(RC.Age_of_Aces.Model_Classes.Math.Point centerOfRotation) {
        this.centerOfRotation = centerOfRotation;
    }

    /**
     * This returns the magnitude.
     * @return The magnitude of the <code>Rotation</code>
     */
    public double getMagnitude() {
        return magnitude;
    }

    /**
     * This sets the magnitude.
     * @param magnitude The value to be used for the magnitude.
     */
    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    /**
     * This returns the angle.
     * @return The angle of the <code>Rotation</code>
     */
    public double getAngle() {
        return angle;
    }

    /**
     * This sets the angle.
     * @param angle The value to be used for the angle.
     */
    public void setAngle(double angle) {
        this.angle = angle;
    }

    /**
     * This returns the last rotation time.
     * @return The last rotation time of the <code>Rotation</code>
     */
    public long getLastRotationTime() {
        return lastRotationTime;
    }

    /**
     * This sets the last rotation time.
     * @param lastRotationTime The value to be used for the last rotation time.
     */
    public void setLastRotationTime(long lastRotationTime) {
        this.lastRotationTime = lastRotationTime;
    }
}
