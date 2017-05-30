/*
 * Name: Robert Ciborowski
 * Date: 2017-02-06
 * Assignment: PongFrame Game
 * Description: A simple model class that stores mouse data.
*/

package RC.Age_of_Aces.Model_Classes.Controls;

// These are the imports.
import java.awt.*;

// This is the MouseTracker class.
public class MouseTracker {
    // These are the properties of the class.
    boolean mousePressed = false, mouseReleased = false, mouseEntered = false, mouseExited = false, mouseClicked = false;
    boolean leftPressed = false, rightPressed = false, leftClicked = false, rightClicked = false;
    int mouseClickCount = 0;
    Point mouseLocation;
    long timeOnLastClick = 0;

    // This is the ony constructor.
    public MouseTracker() {

    }

    // This functions resets the values and should be used
    // after every frame update.
    public void resetValues() {
        mousePressed = false;
        mouseReleased = false;
        mouseEntered = false;
        mouseExited = false;
        mouseClicked = false;
        mouseClickCount = 0;
        leftPressed = false;
        rightPressed = false;
    }

    // These are some simple getters and setters.
    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public boolean isMouseReleased() {
        return mouseReleased;
    }

    public void setMouseReleased(boolean mouseReleased) {
        this.mouseReleased = mouseReleased;
    }

    public boolean isMouseEntered() {
        return mouseEntered;
    }

    public void setMouseEntered(boolean mouseEntered) {
        this.mouseEntered = mouseEntered;
    }

    public boolean isMouseExited() {
        return mouseExited;
    }

    public void setMouseExited(boolean mouseExited) {
        this.mouseExited = mouseExited;
    }

    public boolean isMouseClicked() {
        return mouseClicked;
    }

    public void setMouseClicked(boolean mouseClicked) {
        this.mouseClicked = mouseClicked;
    }

    public int getMouseClickCount() {
        return mouseClickCount;
    }

    public void setMouseClickCount(int mouseClickCount) {
        this.mouseClickCount = mouseClickCount;
    }

    public Point getMouseLocationAsJavaPoint() {
        return mouseLocation;
    }

    public RC.Age_of_Aces.Model_Classes.Math.Point getMouseLocationAsRCPoint() {
        RC.Age_of_Aces.Model_Classes.Math.Point returnValue = new RC.Age_of_Aces.Model_Classes.Math.Point();
        returnValue.convertFromJavaPoint(mouseLocation);
        return returnValue;
    }

    public void setMouseLocation(Point mouseLocation) {
        this.mouseLocation = mouseLocation;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    public boolean isLeftClicked() {
        return leftClicked;
    }

    public void setLeftClicked(boolean leftClicked) {
        this.leftClicked = leftClicked;
    }

    public boolean isRightClicked() {
        return rightClicked;
    }

    public void setRightClicked(boolean rightClicked) {
        this.rightClicked = rightClicked;
    }

    public long getTimeOnLastClick() {
        return timeOnLastClick;
    }

    public void setTimeOnLastClick(long timeOnLastClick) {
        this.timeOnLastClick = timeOnLastClick;
    }
}
