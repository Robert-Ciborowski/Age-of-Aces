/*
 * Name: Robert Ciborowski
 * Date: 23-01-2017
 * Assignment: Triangle-Bouncer
 * Description: A model class that manages the rules of the game, including physics.
*/

package RC.Age_of_Aces.Model_Classes;

import RC.Age_of_Aces.Model_Classes.Math.*;
import RC.Age_of_Aces.Model_Classes.Sprites.*;

import java.util.ArrayList;

/**
 * The <code>RuleManager</code> class is used to modify gameplay
 * elements such as sprites based on rules. The modifications
 * include translations and rotations. Rules include collisions
 * with the edges of the screen as well collisions with other sprites.
 *
 * @date 23-01-2017
 * @author Robert Ciborowski
 */
public class RuleManager {
    /**
     * An enum used to add rules.
     */
    public enum Rule {SPRITE_COLLISION, WINDOW_TOP_BORDER_COLLISION, WINDOW_BOTTOM_BORDER_COLLISION, WINDOW_LEFT_BORDER_COLLISION, WINDOW_RIGHT_BORDER_COLLISION};

    /**
     * The list of rules that are active.
     */
    ArrayList<Rule> ruleList = new ArrayList();

    /**
     * These are some constants used for angles (in radians, of course). Right = 0 radians (or 2 * PI radians).
     */
    final double angleOfRight = 0, angleOfDown = Math.PI / 2, angleOfLeft = Math.PI, angleOfUp = Math.PI / 2 * 3, anglesInACircle = Math.PI * 2;

    /**
     * This is the default constructor.
     */
    public RuleManager() {

    }

    /**
     * A method that adds a rule.
     * @param rule The rule to add.
     */
    public void addRule(Rule rule) {
        boolean added = false;
        for (Rule rule2 : ruleList) {
            if (rule2 == rule) {
                added = true;
            }
        }
        if (!added) {
            ruleList.add(rule);
        }
    }

    /**
     * A method that removes a rule.
     * @param rule The rule to remove.
     */
    public void removeRule(Rule rule) {
        for (int i = 0; i < ruleList.size(); i++) {
            if (rule == ruleList.get(i)) {
                ruleList.remove(i);
            }
        }
    }

    /**
     * A method that updates sprite position based on the current rules.
     * @param time The time since epoch in milliseconds.
     * @param sprites The list of sprites to modify.
     */
    public void modifySpritePositions(long time, ArrayList<Sprite> sprites) {
        // This loops through all sprites of the sprite lsit.
        for (int i = 0; i < sprites.size(); i++) {
            Sprite s = sprites.get(i);
            PathSegment movement = s.getMovement();
            if (movement != null && movement.getSpeed() != 0) {
                if (ruleList.contains(Rule.SPRITE_COLLISION)) {
                    // This section updates sprite-to-sprite collision.
                    for (int j = 0; j < sprites.size(); j++) {
                        if (j != i && sprites.get(j).isCollidable()) {
                            Sprite collisionSprite = sprites.get(j);
                            if (collisionSprite instanceof ImageSprite) {

                            } else if (collisionSprite instanceof BallSprite) {

                            } else if (collisionSprite instanceof LineSprite) {
                                collideWithLineSprite(sprites.get(i), (LineSprite) collisionSprite, time);
                            } else if (collisionSprite instanceof TriangleSprite) {
                                collideWithLineSprite(sprites.get(i), (LineSprite) ((TriangleSprite) collisionSprite).getSides()[0], time);
                                collideWithLineSprite(sprites.get(i), (LineSprite) ((TriangleSprite) collisionSprite).getSides()[1], time);
                                collideWithLineSprite(sprites.get(i), (LineSprite) ((TriangleSprite) collisionSprite).getSides()[2], time);
                            }
                        }
                    }
                } else {
                    // If there is no sprite collision enabled. this simply displaces the sprite based on its path.
                    movement.setMagnitudeUsingTime(time);
                    int x = (int) s.getOriginalPosition().getProperty(Rect.RectProperties.X_POSITION) + (int) (movement.getXDisplacement(time - (long) movement.getProperty(PathSegment.PathSegmentProperty.MOVEMENT_START_TIME)));
                    int y = (int) s.getOriginalPosition().getProperty(Rect.RectProperties.Y_POSITION) + (int) (movement.getYDisplacement(time - (long) movement.getProperty(PathSegment.PathSegmentProperty.MOVEMENT_START_TIME)));
                    double width = s.getBoundingBox().getProperty(Rect.RectProperties.WIDTH), height = s.getBoundingBox().getProperty(Rect.RectProperties.HEIGHT);
                    // This modifies the sprite's position.
                    s.setBoundingBox(x, y, width, height);
                }

            }

            // This rotates any sprites with rotation.
            if (s.getRotation() != null && s.getRotation().getMagnitude() != 0) {
                if (s instanceof ImageSprite) {

                } else if (s instanceof BallSprite) {

                } else if (s instanceof LineSprite) {

                } else if (s instanceof TriangleSprite) {
                    rotateTriangle((TriangleSprite) s, time);
                }
            }
        }
    }

    /**
     * A method that collides a sprite with a line sprite. This method uses the collision point between
     * the two sprites and makes multiple checks to see if there is a collision. If there is a collision,
     * the path of spriteA is changed. The displacement of spriteA is modified based on its path, even if
     * there is no collision.
     *
     * <p>Note: This method is large. This is done to easily read its mathematical calculations. Som people
     * say that methods should not be more than 25 lines, but in scenarios in which a method implements a
     * large algorithm, that makes life more diffcult for anyone that is reading the math behind the algorithm.</p>
     *
     * @param spriteA The first sprite in the collision.
     * @param spriteB The second sprite in the collision.
     * @param time The time since epoch in milliseconds.
     */
    private void collideWithLineSprite(Sprite spriteA, LineSprite spriteB, long time) {
        // lineA represents the movement line of Sprite spriteA while
        // lineB represents the line of LineSprite spriteB.
        LineEquation lineA = spriteA.getMovement().getLineEquation(), lineB = spriteB.getLineEquation(),
                lineC = lineB.getPerpendicular();

        RC.Age_of_Aces.Model_Classes.Math.Point collisionPoint = new RC.Age_of_Aces.Model_Classes.Math.Point();
        if (lineA.getM() - lineB.getM() != 0){
            if (spriteA instanceof BallSprite) {
                // This is collision if spriteA is a BallSprite.
                BallSprite ball = (BallSprite) spriteA;
                PathSegment movement = ball.getMovement();
                RC.Age_of_Aces.Model_Classes.Math.Point middleOfBall = new RC.Age_of_Aces.Model_Classes.Math.Point(ball.getBoundingBox().getXPosition() + ball.getRadius(), ball.getBoundingBox().getYPosition() + ball.getRadius());
                lineC.setB(-(lineC.getM() * middleOfBall.getX() - middleOfBall.getY()));
                collisionPoint.setX((lineC.getB() - lineB.getB()) / (lineB.getM() - lineC.getM()));
                collisionPoint.setY(lineB.getM() * collisionPoint.getX() + lineB.getB());
                RC.Age_of_Aces.Model_Classes.Math.Point collisionOnBall = getCollisionPointOnBall(ball, collisionPoint);

                double x = ball.getBoundingBox().getXPosition(), y = ball.getBoundingBox().getYPosition();
                movement.setMagnitudeUsingTime(time);
                double newX = ball.getOriginalPosition().getProperty(Rect.RectProperties.X_POSITION) + (movement.getXDisplacement(time - (long) movement.getProperty(PathSegment.PathSegmentProperty.MOVEMENT_START_TIME)));
                double newY = ball.getOriginalPosition().getProperty(Rect.RectProperties.Y_POSITION) + (movement.getYDisplacement(time - (long) movement.getProperty(PathSegment.PathSegmentProperty.MOVEMENT_START_TIME)));
                double width = ball.getBoundingBox().getProperty(Rect.RectProperties.WIDTH), height = ball.getBoundingBox().getProperty(Rect.RectProperties.HEIGHT);

                // This modifies the sprite's position.
                ball.setBoundingBox(newX, newY, width, height);
                RC.Age_of_Aces.Model_Classes.Math.Point newCollisionOnBall = getCollisionPointOnBall(ball, collisionPoint);
                double distance = Math.sqrt(Math.pow(newCollisionOnBall.getX(), 2) + Math.pow(newCollisionOnBall.getY(), 2));
                double distanceBetweenLineEndAndBallA = Math.sqrt(Math.pow(spriteB.getPointA().getX() - middleOfBall.getX(), 2) + Math.pow(spriteB.getPointA().getY() - middleOfBall.getY(), 2));
                double distanceBetweenLineEndAndBallB = Math.sqrt(Math.pow(spriteB.getPointB().getX() - middleOfBall.getX(), 2) + Math.pow(spriteB.getPointB().getY() - middleOfBall.getY(), 2));

                if ((((round(-newCollisionOnBall.getX(), 1) == round(collisionOnBall.getX(), 1) && round(-newCollisionOnBall.getY(), 1) == round(collisionOnBall.getY(), 1)) ||
                        Math.ceil(distance) < ball.getRadius()) && spriteB.isWithinLine(collisionPoint)) ||
                        Math.ceil(distanceBetweenLineEndAndBallA) < ball.getRadius() || Math.ceil(distanceBetweenLineEndAndBallB) < ball.getRadius()) {
                    // If this section is reachedm, one of the collision checks for one of the collision scenarios has been triggered
                    // and a collision needs to be performed.
                    double angleOfLineSprite = (spriteB.getPointB().getY() - spriteB.getPointA().getY()) / (spriteB.getPointB().getX() - spriteB.getPointA().getX());
                    double differenceOfAngles = spriteA.getMovement().getDirection() - Math.atan(angleOfLineSprite);
                    if (Math.ceil(distanceBetweenLineEndAndBallA) < ball.getRadius() || Math.ceil(distanceBetweenLineEndAndBallB) < ball.getRadius()) {
                        spriteA.getMovement().setDirection(spriteA.getMovement().getDirection() - Math.PI);
                    } else{
                        spriteA.getMovement().setDirection(spriteA.getMovement().getDirection() - Math.PI + 2 * ((Math.PI / 2) - differenceOfAngles));
                    }
                    if (spriteA.getMovement().getDirection() >= Math.PI * 2) {
                        spriteA.getMovement().setDirection(spriteA.getMovement().getDirection() - Math.PI * 2);
                    } else if (spriteA.getMovement().getDirection() < 0) {
                        spriteA.getMovement().setDirection(spriteA.getMovement().getDirection() + Math.PI * 2);
                    }
                    double newBoundingBoxX, newBoundingBoxY;
                    newBoundingBoxX = newX - (newCollisionOnBall.getX() - (distance / ball.getRadius()) * newCollisionOnBall.getX());
                    newBoundingBoxY = newY - (newCollisionOnBall.getY() - (distance / ball.getRadius()) * newCollisionOnBall.getY());
                    Rect newBoundingBox = new Rect(newBoundingBoxX, newBoundingBoxY,
                            spriteA.getBoundingBox().getWidth(), spriteA.getBoundingBox().getHeight());
                    spriteA.setBoundingBox(newBoundingBox);
                    spriteA.setOriginalPosition(spriteA.getBoundingBox());
                    spriteA.getMovement().setStartTimeOfMovement(time);
                }
            }
        } else {
            // Division by zero!
        }
    }

    /**
     * A method that returns the collision point on a ball if it were to contact a <code>RC.Age_of_Aces.Model_Classes.Math.Point</code>.
     * @param ball The ball in question.
     * @param collisionPoint The point that the ball collides with.
     * @return The point relative to the center of the ball that represents where on the ball the
     *         collision occurs.
     */
    private RC.Age_of_Aces.Model_Classes.Math.Point getCollisionPointOnBall(BallSprite ball, RC.Age_of_Aces.Model_Classes.Math.Point collisionPoint) {
        RC.Age_of_Aces.Model_Classes.Math.Point middleOfBall = new RC.Age_of_Aces.Model_Classes.Math.Point(ball.getBoundingBox().getXPosition() + ball.getRadius(), ball.getBoundingBox().getYPosition() + ball.getRadius());
        double distanceFromBallCentreToPoint = Math.sqrt(Math.pow((collisionPoint.getX() - middleOfBall.getX()), 2) + Math.pow((collisionPoint.getY() - middleOfBall.getY()), 2));
        double factor = ball.getRadius() / distanceFromBallCentreToPoint;
        if (factor <= 1) {
            RC.Age_of_Aces.Model_Classes.Math.Point collisionPointOnBall = new RC.Age_of_Aces.Model_Classes.Math.Point((collisionPoint.getX() - middleOfBall.getX()) * factor, (collisionPoint.getY() - middleOfBall.getY()) * factor);
            return collisionPointOnBall;
        } else {
            // If this section is reached, the collision point passed as a parameter is inside of the ball!
            RC.Age_of_Aces.Model_Classes.Math.Point collisionPointOnBall = new RC.Age_of_Aces.Model_Classes.Math.Point((collisionPoint.getX() - middleOfBall.getX()), (collisionPoint.getY() - middleOfBall.getY()));
            return collisionPointOnBall;
        }
    }

    /**
     * A method that rotates a <code>RC.Age_of_Aces.Model_Classes.Math.Point</code>.
     * @param point The ball to rotate.
     * @param rotation The rotation for the point to follow.
     * @param time The current time (in milliseconds).
     */
    private void rotatePoint(RC.Age_of_Aces.Model_Classes.Math.Point point, Rotation rotation, long time) {
        double distance, sine, cosine, currentAngle;

        distance = Math.sqrt(Math.pow(point.getX() - rotation.getCenterOfRotation().getX(), 2) + Math.pow(point.getY() - rotation.getCenterOfRotation().getY(), 2));
        sine = (point.getY() - rotation.getCenterOfRotation().getY()) / distance;
        cosine = (point.getX() - rotation.getCenterOfRotation().getX()) / distance;

        // This findd the current angle.
        if (sine > 0 && cosine > 1) {
            currentAngle = Math.asin(sine);
        } else if (sine > 0 && cosine < 0) {
            currentAngle = Math.PI - Math.asin(sine);
        } else if (sine < 0 && cosine < 0) {
            currentAngle = Math.PI + Math.asin(sine * -1);
        } else {
            currentAngle = 2 * Math.PI - Math.asin(sine * -1);
        }

        // This sets the new angle.
        rotation.setAngle(currentAngle + (rotation.getMagnitude()) * (time - rotation.getLastRotationTime()));
        if (rotation.getAngle() >= 2 * Math.PI) {
            rotation.setAngle(rotation.getAngle() - 2 * Math.PI);
        } else if (rotation.getAngle() < 0) {
            rotation.setAngle(rotation.getAngle() + 2 * Math.PI);
        }

        point.setX(rotation.getCenterOfRotation().getX() + distance * Math.cos(rotation.getAngle()));
        point.setY(rotation.getCenterOfRotation().getY() + distance * Math.sin(rotation.getAngle()));
    }

    /**
     * A method that rotates a <code>TriangleSprite</code>.
     * @param sprite The triangle to rotate.
     * @param time The current time (in milliseconds).
     */
    private void rotateTriangle(TriangleSprite sprite, long time) {
        for (int i = 0; i < 3; i++) {
            rotatePoint(sprite.getSides()[i].getPointA(), sprite.getRotation(), time);
        }
        for (int i = 0; i < 3; i++) {
            sprite.getSides()[i].recalculateBoundingBoxUsingPoints();
        }
        sprite.getRotation().setLastRotationTime(time);
    }

    /**
     * A method that rounds to a specified amount of decimal places.
     * @param number The number to round.
     * @param places The amount of places to round to.
     */
    private double round(double number, int places) {
        for (int i = 0; i < places; i++) {
            number *= 10.0;
        }
        double newNumber = Math.round(number);
        for (int i = 0; i < places; i++) {
            newNumber /= 10.0;
        }
        return newNumber;
    }
}
