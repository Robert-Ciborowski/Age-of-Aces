package RC.Age_of_Aces.Model_Classes.Menus;
 /*
 * Name: Robert Ciborowski
 * Date: 2017-01-31
 * Assignment: Pong Game
 * Description: A simple model class for buttons.
*/

// These are the imports.
import RC.Age_of_Aces.Model_Classes.Controls.MouseTracker;
import RC.Age_of_Aces.Model_Classes.Math.Rect;
import RC.Age_of_Aces.Model_Classes.Sprites.ImageSprite;
import RC.Age_of_Aces.Model_Classes.Sprites.Sprite;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// This is the MenuButton class.
public class MenuButton {
    // These are the objects of the class, including text, images and dimensions.
    MenuLabel buttonText;
    ImageSprite buttonImage, buttonImageHover;
    Rect dimensions;
    ArrayList<ActionListener> actions = new ArrayList();

    // These are properties of the button.
    String defaultImage = "resources/button.png", defaultImageHover = "resources/button2.png";
    boolean isHovered = false;

    // This is the default construcotr.
    public MenuButton() {
        buttonText = new MenuLabel("", new Color(0, 0, 0));
        dimensions = new Rect(0, 0, 100, 50);
        buttonImage = new ImageSprite(dimensions);
        buttonImageHover = new ImageSprite(dimensions);
        buttonImage.loadImage(defaultImage);
        buttonImageHover.loadImage(defaultImageHover);
    }

    // These are other constructors.
    public MenuButton(String text, Rect setDimensions) {
        buttonText = new MenuLabel(text, new Color(0, 0, 0));
        setDimensions(setDimensions);
        buttonImage = new ImageSprite(dimensions);
        buttonImageHover = new ImageSprite(dimensions);
        buttonImage.loadImage(defaultImage);
        buttonImageHover.loadImage(defaultImageHover);
    }

    public MenuButton(String text, Rect setDimensions, String buttonImagePath, String buttonImageHoverPath) {
        buttonText = new MenuLabel(text, new Color(0, 0, 0));
        setDimensions(setDimensions);
        buttonImage = new ImageSprite(dimensions);
        buttonImage.loadImage(buttonImagePath);
        buttonImageHover = new ImageSprite(dimensions);
        buttonImageHover.loadImage(buttonImageHoverPath);
    }

    // These are some basic getters and setters.
    public MenuLabel getButtonText() {
        return buttonText;
    }

    public String getButtonTextAsString() {
        return buttonText.getText();
    }

    public void setButtonText(MenuLabel buttonText) {
        this.buttonText = buttonText;
    }

    public ImageSprite getButtonImage() {
        return buttonImage;
    }

    public void setButtonImage(ImageSprite buttonImage) {
        this.buttonImage = buttonImage;
    }

    public ImageSprite getButtonImageHover() {
        return buttonImageHover;
    }

    public boolean isHovered() {
        return isHovered;
    }

    public void setButtonImageHover(ImageSprite buttonImageHover) {
        this.buttonImageHover = buttonImageHover;
    }

    public Rect getDimensions() {
        return dimensions;
    }

    public void setDimensions(Rect dimensions) {
        this.dimensions = dimensions;
        buttonImage.setBoundingBox(dimensions);
        buttonImageHover.setBoundingBox(dimensions);
    }

    public void addActionListener(ActionListener action) {
        actions.add(action);
    }

    // This changes the button's text size.
    public void changeTextSize(int newSize) {
        Font font = buttonText.getFont();
        Font font2 = new Font(font.getFontName(), font.getStyle(), newSize);
        buttonText.setFont(font2);
    }

    // This updates the button.
    public void update(MouseTracker menuMouse) {
        // This uses the cursor's position to figure out if the hover image should be
        // displayed and if the button was clicked. If the button was clicked then
        // all appropriate actions will be performed.
        Point mouseLocation = menuMouse.getMouseLocationAsJavaPoint();
        if (mouseLocation.getX() >= dimensions.getProperty(Rect.RectProperties.X_POSITION) &&
                mouseLocation.getX() <= dimensions.getProperty(Rect.RectProperties.X_POSITION) + dimensions.getProperty(Rect.RectProperties.WIDTH) &&
                mouseLocation.getY() >= dimensions.getProperty(Rect.RectProperties.Y_POSITION) &&
                mouseLocation.getY() <= dimensions.getProperty(Rect.RectProperties.Y_POSITION) + dimensions.getProperty(Rect.RectProperties.HEIGHT)) {
            isHovered = true;
            if (menuMouse.isMouseClicked()) {
                for (ActionListener action : actions) {
                    action.actionPerformed(new ActionEvent(this, 0, ""));
                }
            }
        } else {
            isHovered = false;
        }
    }
}
