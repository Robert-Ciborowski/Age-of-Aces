package RC.Age_of_Aces.View_Classes.Menus;

/*
 * Name: Robert Ciborowski
 * Date: 2017-01-23
 * Assignment: Pong Game
 * Description:: A view type class that represents a screen with
 *             widgets and a backdrop.
*/

// These are the imports.
import RC.Age_of_Aces.Model_Classes.Menus.Backdrop;
import RC.Age_of_Aces.Model_Classes.Menus.MenuButton;
import RC.Age_of_Aces.Model_Classes.Menus.MenuLabel;
import RC.Age_of_Aces.Model_Classes.Controls.MouseTracker;
import RC.Age_of_Aces.Model_Classes.Sprites.ImageSprite;
import RC.Age_of_Aces.Model_Classes.Sprites.Sprite;
import RC.Age_of_Aces.View_Classes.Renderer2D;

import java.awt.*;
import java.util.ArrayList;

// This is the Screen class.
public class Screen {
    // These are the properties of the class.
    private ArrayList<MenuButton> buttons;
    private ArrayList<MenuLabel> labels;
    private ArrayList<Sprite> sprites;
    private Backdrop backdrop;
    boolean gameplayVisibility;

    // This is the default constructor.
    public Screen() {
        gameplayVisibility = false;
        buttons = new ArrayList();
        sprites = new ArrayList();
        labels = new ArrayList();
        backdrop = new Backdrop();
    }

    // These are some adders.
    public void addButton(MenuButton button) {
        buttons.add(button);
    }

    public void addLabel(MenuLabel label) {
        labels.add(label);
    }

    public void addSprite(Sprite sprite) {
        sprites.add(sprite);
    }

    public void addSprites(ArrayList<Sprite> sprites) {
        for (Sprite s : sprites) {
            this.sprites.add(s);
        }
    }

    // These methods set or laod the backdrop of the screen.
    public void setBackdrop(Backdrop setBackdrop) {
        backdrop = setBackdrop;
    }

    public boolean loadBackdrop(String path) {
        return backdrop.loadImage(path);
    }

    // These methods toggle the visibility of gameplay inside of the screen.
    public void setGameplayVisibility(boolean visibility) {
        gameplayVisibility = visibility;
    }

    public void toggleGameplay() {
        if (gameplayVisibility) {
            gameplayVisibility = false;
        } else {
            gameplayVisibility = true;
        }
    }

    // This method updates the screen.
    public void update(MouseTracker menuMouse) {
        for (MenuButton button : buttons) {
            button.update(menuMouse);
        }
    }

    public ArrayList<MenuButton> getButtons() {
        return buttons;
    }

    public void setButtons(ArrayList<MenuButton> buttons) {
        this.buttons = buttons;
    }

    public ArrayList<MenuLabel> getLabels() {
        return labels;
    }

    public void setLabels(ArrayList<MenuLabel> labels) {
        this.labels = labels;
    }

    public ArrayList<Sprite> getSprites() {
        return sprites;
    }

    public void setSprites(ArrayList<Sprite> sprites) {
        this.sprites = sprites;
    }

    public Backdrop getBackdrop() {
        return backdrop;
    }

    public boolean getGameplayVisibility() {
        return gameplayVisibility;
    }
}