/*
 * Name: Robert Ciborowski
 * Date: 2017-01-23
 * Assignment: Pong Game
 * Description: A view class that controls screens.
*/

package RC.Age_of_Aces.View_Classes.Menus;

// These are the imports.
import RC.Age_of_Aces.Model_Classes.Controls.MouseTracker;
import RC.Age_of_Aces.View_Classes.*;

import java.awt.*;
import java.util.ArrayList;

// This is the MenuManager class, which uses threads.
public class MenuManager extends Thread {
    // These are the properties of the class.
    ArrayList<Screen> screens;
    int currentScreen;

    // This is the only constructor.
    public MenuManager() {
        screens = new ArrayList();
        currentScreen = 0;
    }

    // This method starts the class's thread.
    public void start () {
        Thread th = new Thread(this);
        th.start();
    }

    // This method updates the menu system.
    public void update(MouseTracker menuMouse) {
        screens.get(currentScreen).update(menuMouse);
    }

    // This method changes the screen.
    public boolean changeCurrentScreen(int newScreen) {
        if (newScreen <= screens.size()) {
            currentScreen = newScreen;
            return true;
        }
        return false;
    }

    // These are some getters, setters and adders.
    public int getCurrentScreen() {
        return currentScreen;
    }

    public void addScreen(Screen screenToAdd) {
        screens.add(screenToAdd);
    }

    public boolean currentScreenContainsGameplay() {
        return screens.get(currentScreen).getGameplayVisibility();
    }

    public ArrayList<Screen> getScreens() {
        return screens;
    }
}
