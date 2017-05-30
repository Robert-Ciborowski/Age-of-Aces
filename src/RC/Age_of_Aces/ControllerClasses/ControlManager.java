/*
 * Name: Robert Ciborowski
 * Date: 23-01-2017
 * Assignment: Various (This is a generic class.)
 * Description: A controller class that manages controls.
*/

package RC.Age_of_Aces.ControllerClasses;

import RC.Age_of_Aces.Model_Classes.Controls.MouseTracker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * The <code>ControlManager</code> class is used to manage user input. It
 * implements <code>KeyListener</code> for access to the user's keyboard
 * inputs.
 *
 * @date 23-01-2017
 * @author Robert Ciborowski
 */
public class ControlManager implements MouseListener, MouseMotionListener, KeyListener {
    /**
     * The actions to be performed during inputs.
     *
     * @see #addActionListener(ActionListener)
     * @see #keyTyped(KeyEvent)
     * @see #keyPressed(KeyEvent)
     * @see #keyReleased(KeyEvent)
     */
    private ArrayList<ActionListener> actions = new ArrayList();

    boolean mouseWasClickedThisFrame = false;

    private static final int CLICK_INTERVAL = 200;

    // These are the class's objects.
    private MouseTracker mouseTracker = new MouseTracker();

    /**
     * This is the default constructor.
     */
    public ControlManager() {
        mouseTracker.setMouseLocation(new Point(0, 0));
    }

    /**
     * This binds the class to a JFrame.
     *
     * @param frame The frame to bind to.
     */
    public void bindToFrame(JFrame frame) {
        frame.addKeyListener(this);
        frame.addMouseListener(this);
        frame.addMouseMotionListener(this);
    }

    // These are functions that dea with the mouseTracker class (which stores data
    // about the cursor).
    public MouseTracker getMouse() {
        return mouseTracker;
    }

    public void resetMouseValues() {
        mouseTracker.resetValues();
    }

    // These are methods for mouse events.
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseTracker.setMousePressed(true);
        mouseTracker.setMouseClickCount(e.getClickCount());
        mouseTracker.setMouseLocation(e.getPoint());
        mouseTracker.setLeftPressed(SwingUtilities.isLeftMouseButton(e));
        mouseTracker.setRightPressed(SwingUtilities.isRightMouseButton(e));
        mouseTracker.setTimeOnLastClick(System.currentTimeMillis());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (mouseTracker.getTimeOnLastClick() + CLICK_INTERVAL >= System.currentTimeMillis()) {
            mouseClick(e);
        }
        mouseTracker.setMouseReleased(true);
        mouseTracker.setMouseClickCount(e.getClickCount());
        mouseTracker.setMouseLocation(e.getPoint());
        mouseTracker.setLeftPressed(false);
        mouseTracker.setRightPressed(false);
    }

    private void mouseClick(MouseEvent e) {
        mouseTracker.setMouseClicked(true);
        mouseTracker.setMouseClickCount(e.getClickCount());
        mouseTracker.setMouseLocation(e.getPoint());
        mouseTracker.setLeftClicked(mouseTracker.isLeftPressed());
        mouseTracker.setRightClicked(mouseTracker.isRightPressed());
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouseTracker.setMouseEntered(true);
        mouseTracker.setMouseLocation(e.getPoint());
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseTracker.setMouseExited(true);
        mouseTracker.setMouseLocation(e.getPoint());
    }

    /**
     * This adds an action.
     * @param a The <code>ActionListener</code> to add.
     */
    public void addActionListener(ActionListener a) {
        actions.add(a);
    }

    /**
     * This checks through KEY_TYPED events.
     * @param e the <code>KeyEvent</code> to check for.
     */
    @Override
    public void keyTyped(KeyEvent e) {
        for (ActionListener a : actions) {
            a.actionPerformed(new ActionEvent(this, e.getKeyCode(), "KEY_TYPED"));
        }
    }

    /**
     * This checks through KEY_PRESSED events.
     * @param e the <code>KeyEvent</code> to check for.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        for (ActionListener a : actions) {
            a.actionPerformed(new ActionEvent(this, e.getKeyCode(), "KEY_PRESSED"));
        }
    }

    /**
     * This checks through KEY_RELEASED events.
     * @param e the <code>KeyEvent</code> to check for.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        for (ActionListener a : actions) {
            a.actionPerformed(new ActionEvent(this, e.getKeyCode(), "KEY_RELEASED"));
        }
    }

    // These are mouse movement commands.
    @Override
    public void mouseDragged(MouseEvent e) {
        mouseTracker.setMouseLocation(e.getPoint());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseTracker.setMouseLocation(e.getPoint());
    }

    public void updateControlsAtEndOfFrame() {
        if (mouseTracker.isMouseClicked() && !mouseWasClickedThisFrame) {
            mouseWasClickedThisFrame = true;
        } else if (mouseTracker.isMouseClicked() && mouseWasClickedThisFrame) {
            mouseTracker.setMouseClicked(false);
            mouseTracker.setLeftClicked(false);
            mouseTracker.setRightClicked(false);
            mouseWasClickedThisFrame = false;
        }
    }
}
