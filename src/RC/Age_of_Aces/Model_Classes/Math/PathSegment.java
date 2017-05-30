 /*
 * Name: Robert Ciborowski
 * Date: 25-01-2017
 * Assignment: Various (This is a generic class.)
 * Description: A model class that is like a regular vector, but with speed.
 *              Some could argue that adding speed to a vector does not make
 *              it a vector at all but instead makes it a movement path.
 *
 *              Please note that this class uses radians!
*/

package RC.Age_of_Aces.Model_Classes.Math;

import RC.Age_of_Aces.Model_Classes.Math.*;
import RC.Age_of_Aces.Model_Classes.Math.Point;

 /**
  * The <code>PathSegment</code> class is used to represent a path.
  * <p>WARNING! This class uses radians!</p>
  *
  * @date 25-01-2017
  * @author Robert Ciborowski
  */
 public class PathSegment {
     /**
      * The basic properties that make up the path.
      */
     private double magnitude, direction, speed;

     /**
      * The time at which the movement was started. This
      * property is not necessary to use but may become
      * useful in certain situations.
      */
     private long startTimeOfMovement = 0;

     /**
      * The line equation of the path. This object is not
      * necessary to use but may become useful in certain
      * situations.
      */
     private LineEquation lineEquation = new LineEquation();

     /**
      * An enum used for getters and setters.
      */
     public enum PathSegmentProperty {MAGNITUDE, DIRECTION, SPEED, MOVEMENT_START_TIME};

     /**
      * This is the default constructor.
      */
     public PathSegment() {
         magnitude = 1;
         changeDirection(0);
         speed = 1;
     }

     /**
      * A constructor that sets up the path's essential properties.
      * @param setMagnitude The magnitude to be used for the <code>PathSegment</code>.
      * @param setDirection The direction to be used for the <code>PathSegment</code>,
      *                     which is in radians.
      * @param setSpeed The speed value to be used for the <code>PathSegment</code>.
      */
     public PathSegment(double setMagnitude, double setDirection, double setSpeed) {
         magnitude = setMagnitude;
         speed = setSpeed;
         changeDirection(setDirection);
     }

     /**
      * A method used internally to safely change the path's direction (in radians).
      * @param newDirection The new direction to be used.
      */
     private void changeDirection(double newDirection) {
         direction = newDirection;
         RC.Age_of_Aces.Model_Classes.Math.Point pointA = new RC.Age_of_Aces.Model_Classes.Math.Point(getXDisplacement(1), getYDisplacement(1)), pointB = new RC.Age_of_Aces.Model_Classes.Math.Point(getXDisplacement(2), getYDisplacement(2));
         lineEquation.calculate(pointA, pointB);
     }

     /**
      * A method used to recalculate the line equation of the path.
      * @param positionOfObject The position of the object using the movement path.
      */
     public void recalculateLineEquation(RC.Age_of_Aces.Model_Classes.Math.Point positionOfObject) {
         RC.Age_of_Aces.Model_Classes.Math.Point pointA = new RC.Age_of_Aces.Model_Classes.Math.Point(getXDisplacement(1), getYDisplacement(1)), pointB = new RC.Age_of_Aces.Model_Classes.Math.Point(getXDisplacement(2), getYDisplacement(2));
         RC.Age_of_Aces.Model_Classes.Math.Point pointAModified = new RC.Age_of_Aces.Model_Classes.Math.Point(pointA.getX() + positionOfObject.getX(), pointA.getY() + positionOfObject.getY());
         RC.Age_of_Aces.Model_Classes.Math.Point pointBModified = new RC.Age_of_Aces.Model_Classes.Math.Point(pointB.getX() + positionOfObject.getX(), pointB.getY() + positionOfObject.getY());
         lineEquation.calculate(pointAModified, pointBModified);
     }

     /**
      * A method used to change the path to go to a certain point.
      * @param spritePosition The position of the object using the movement path.
      * @param target The target point to move towards.
      */
     public void moveToPoint(RC.Age_of_Aces.Model_Classes.Math.Point spritePosition, RC.Age_of_Aces.Model_Classes.Math.Point target) {
         double tanRatio = Math.abs((spritePosition.getY() - target.getY()) / (spritePosition.getX() - target.getX()));
         double newDirection = Math.atan(tanRatio);
         if (spritePosition.getX() < target.getX() && spritePosition.getY() < target.getY()) {
             newDirection = Math.abs(newDirection);
         }
         if (spritePosition.getX() > target.getX() && spritePosition.getY() < target.getY()) {
             newDirection = Math.PI - newDirection;
         }
         if (spritePosition.getX() > target.getX() && spritePosition.getY() > target.getY()) {
             newDirection = Math.abs(newDirection) + Math.PI;
         } if (spritePosition.getX() < target.getX() && spritePosition.getY() > target.getY()) {
             newDirection = 2 * Math.PI - newDirection;
         }
         changeDirection(newDirection);
     }

     // These are some simple getters and setters.
     public void setMagnitudeUsingTime(double time) {
         magnitude = speed * time;
     }

      /**
       * A function for setting any property of the <code>PathSegment</code>.
       * @param value The value that the property should be set to.
       * @param property The property to be modified.
       */
      public void setProperty(double value, PathSegmentProperty property) {
          switch (property) {
              case MAGNITUDE:
                  magnitude = value;
                  break;
              case DIRECTION:
                  changeDirection(value);
                  break;
              case SPEED:
                  speed = value;
                  break;
              case MOVEMENT_START_TIME:
                  startTimeOfMovement = (long) value;
                  break;
          }
      }

      /**
       * A function for getting any property of the <code>PathSegment</code>.
       * @param property The property to be obtained.
       * @return The value of the property.
       */
      public double getProperty(PathSegmentProperty property) {
          switch (property) {
              case MAGNITUDE:
                  return magnitude;
              case DIRECTION:
                  return direction;
              case SPEED:
                  return speed;
              case MOVEMENT_START_TIME:
                  return startTimeOfMovement;
              default:
                  return 0;
          }
      }

     /**
      * This returns the x displacement based on time.
      * @return The x displacement.
      */
     public double getXDisplacement(long time) {
         double vectDisplacement = time * speed;
         double displacement = Math.cos(direction) * vectDisplacement;
         return displacement;
     }

     /**
      * This returns the y displacement based on time.
      * @return The y displacement.
      */
     public double getYDisplacement(long time) {
         double vectDisplacement = time * speed;
         double displacement = Math.sin(direction) * vectDisplacement;
         return displacement;
     }

     public boolean hasReachedMagnitude(long time) {
         return (time - startTimeOfMovement) * speed >= magnitude;
     }

     /**
      * This returns the magnitude.
      * @return The magnitude.
      */
     public double getMagnitude() {
         return magnitude;
     }

     /**
      * This sets the magnitude of the line.
      * @param magnitude The value to be used for the magnitude.
      */
     public void setMagnitude(double magnitude) {
         this.magnitude = magnitude;
     }

     /**
      * This returns the direction.
      * @return The direction.
      */
     public double getDirection() {
         return direction;
     }

     /**
      * This sets the direction of the line.
      * @param direction The value to be used for the direction.
      */
     public void setDirection(double direction) {
         changeDirection(direction);
     }

     /**
      * This returns the speed.
      * @return The speed.
      */
     public double getSpeed() {
         return speed;
     }

     /**
      * This sets the speed of the line.
      * @param speed The value to be used for the speed.
      */
     public void setSpeed(double speed) {
         this.speed = speed;
     }

     /**
      * This returns the start time of the movement.
      * @return The start time of the movement.
      */
     public long getStartTimeOfMovement() {
         return startTimeOfMovement;
     }

     /**
      * This sets the start time of the movement.
      * @param startTimeOfMovement The value to be used for the start time of the movement.
      */
     public void setStartTimeOfMovement(long startTimeOfMovement) {
         this.startTimeOfMovement = startTimeOfMovement;
     }

     /**
      * This returns the line equation.
      * @return The line equation.
      */
     public LineEquation getLineEquation() {
         return lineEquation;
     }
 }
