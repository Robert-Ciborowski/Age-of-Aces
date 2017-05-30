/*
 * Name: Robert Ciborowski
 * Date: 28-02-2017
 * Assignment: Triangle-Bouncer
 * Description: A view class that represents the window used by the Triangle-Bouncer program.
*/

package RC.Age_of_Aces.View_Classes;

import RC.Age_of_Aces.ControllerClasses.ControlManager;
import RC.Age_of_Aces.Model_Classes.*;
import RC.Age_of_Aces.Model_Classes.Math.Rect;
import RC.Age_of_Aces.Model_Classes.Menus.Backdrop;
import RC.Age_of_Aces.Model_Classes.Menus.MenuButton;
import RC.Age_of_Aces.Model_Classes.Menus.MenuLabel;
import RC.Age_of_Aces.Model_Classes.Sprites.ImageSprite;
import RC.Age_of_Aces.View_Classes.Menus.MenuManager;
import RC.Age_of_Aces.View_Classes.Menus.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

/**
 * The <code>Window</code> class is used to create a simple Window
 * using java's <code>JFrame</code> and <code>Runnable</code>. This
 * class also uses managers such as <code>SpriteManager</code> to
 * manage controls and gameplay elements.
 *
 * @date 28-02-2017
 * @author Robert Ciborowski
 */
public class Window extends JFrame implements Runnable {
    /**
     * The <code>Window</code>'s renderer, which is able to render several
     * 2D objects.
     * @see #render
     */
    private Renderer2D renderer2D = new Renderer2D();

    /**
     * The <code>Window</code>'s buffer strategy, which is used when
     * rendering to the JFrame of the window class.
     * @see #initWindow
     * @see #render
     */
    private BufferStrategy bufferStrategy;

    /**
     * The <code>Window</code>'s Rule Manager, which is used to perform
     * actions on sprites based on rules given to it.
     * @see #setupGameplay
     */
    private RuleManager ruleManager = new RuleManager();

    /**
     * The <code>Window</code>'s Control Manager, which is used to perform
     * actions based on user input.
     * @see #addControlManagerActions
     */
    private ControlManager controlManager = new ControlManager();

    private GameplayManager gameplayManager = new GameplayManager();

    private MenuManager menuManager = new MenuManager();

    /**
     * The clear colour used when rendering to the window.
     * @see #render
     */
    private Color clearColour = new Color(200, 200, 200);

    /**
     * The width and height of the window.
     * @see #initWindow
     */
    private int width = 800, height = 600;

    /**
     * The main method, which is used in order to start the program
     * from this class.
     * @param args The arguments for the program, which are not
     *             currently being used.
     */
    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Window();
            }
        });
    }

    /**
     * The only constructor of a <code>Window</code>, which initialises it.
     */
    public Window() {
        // This sets up the window and its objects.
        super("Ace of Ages");
        initWindow();
        setupMenus();
        setupGameplay();
        bindObjects();
        // This method starts a thread for the application.
        start();
    }

    /**
     * Sets up the window's properties, buffer strategy, and control manager.
     */
    private void initWindow() {
        setSize(width, height);
        setVisible(true);
        createBufferStrategy(2);
        bufferStrategy = this.getBufferStrategy();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        controlManager.bindToFrame(this);
    }

    private void setupMenus() {
        Screen titleScreen = new Screen(), gameplayScreen = new Screen();

        // This sets up the title screen.
        MenuButton startButton = new MenuButton();
        ImageSprite startButtonImage = new ImageSprite(), startButtonImageHover = new ImageSprite();
        startButtonImage.loadImage("resources/images/menus/button0.png");
        startButtonImageHover.loadImage("resources/images/menus/button0Hover.png");
        startButton.setButtonImage(startButtonImage);
        startButton.setButtonImageHover(startButtonImageHover);
        MenuLabel startText = new MenuLabel("Start");
        startText.setColour(new Color(0, 0, 0));
        startText.setFont(new Font("Arial", Font.PLAIN, 14));
        startButton.setButtonText(startText);
        startButton.setDimensions(new Rect(300, 300, 200, 50));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuManager.changeCurrentScreen(1);
            }
        });
        titleScreen.addButton(startButton);

        MenuLabel titleText = new MenuLabel("Ace of Ages");
        titleText.setColour(new Color(200, 200, 200));
        titleText.setFont(new Font("Arial", Font.PLAIN, 44));
        titleText.setOffset(new Rect(280, 150, 0, 0));
        titleScreen.addLabel(titleText);

        Backdrop titleScreenBackdrop = new Backdrop();
        titleScreenBackdrop.loadImage("resources/images/menus/backdrop0.png");
        titleScreen.setBackdrop(titleScreenBackdrop);

        // This sets up the gameplay screen.
        gameplayScreen.setGameplayVisibility(true);

        ImageSprite topBar = new ImageSprite();
        topBar.loadImage("resources/images/menus/topBar.png");
        topBar.setBoundingBox(new Rect(0, 10, 800, 50));
        gameplayScreen.addSprite(topBar);

        ArrayList<MenuLabel> resourceLabels = new ArrayList();
        resourceLabels.add(new MenuLabel("Food: 0"));
        resourceLabels.add(new MenuLabel("Wood: 0"));
        resourceLabels.add(new MenuLabel("Stone: 0"));
        resourceLabels.add(new MenuLabel("Gold: 0"));

        int counter = 0;
        for (MenuLabel label : resourceLabels) {
            label.setColour(new Color(50, 50, 50));
            label.setFont(new Font("Arial", Font.PLAIN, 14));
            label.setOffset(new Rect(25 + counter * 100, 50, 0, 0));
            gameplayScreen.addLabel(label);
            counter++;
        }

        gameplayManager.setFoodLabel(resourceLabels.get(0));
        gameplayManager.setWoodLabel(resourceLabels.get(1));
        gameplayManager.setStoneLabel(resourceLabels.get(2));
        gameplayManager.setGoldLabel(resourceLabels.get(3));

        ImageSprite optionWindow = new ImageSprite();
        optionWindow.loadImage("resources/images/menus/selectionWindow.png");
        optionWindow.setBoundingBox(new Rect(600, 400, 200, 200));
        gameplayManager.setOptionWindowImage(optionWindow);
        gameplayScreen.addSprite(optionWindow);

        MenuButton optionWindowButton = new MenuButton();
        ImageSprite optionWindowButtonImage = new ImageSprite(), optionWindowButtonImageHover = new ImageSprite();
        optionWindowButtonImage.loadImage("resources/images/menus/button0.png");
        optionWindowButtonImageHover.loadImage("resources/images/menus/button0Hover.png");
        optionWindowButton.setButtonImage(optionWindowButtonImage);
        optionWindowButton.setButtonImageHover(optionWindowButtonImageHover);
        optionWindowButton.setDimensions(new Rect(650, 560, 100, 30));
        optionWindowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuManager.changeCurrentScreen(1);
            }
        });
        gameplayManager.setOptionWindowButton(optionWindowButton);
        gameplayScreen.addButton(optionWindowButton);

        MenuLabel optionWindowText = new MenuLabel("VILLAGER");
        optionWindowText.setColour(new Color(50, 50, 50));
        optionWindowText.setFont(new Font("Arial", Font.BOLD, 14));
        optionWindowText.setOffset(new Rect(663, 555, 0, 0));
        gameplayManager.setOptionWindowText(optionWindowText);
        gameplayScreen.addLabel(optionWindowText);

        menuManager.addScreen(titleScreen);
        menuManager.addScreen(gameplayScreen);
    }

    /**
     * Sets up the program's sprites and sprite-related elements.
     */
    private void setupGameplay() {
        gameplayManager.loadMap(0);
        gameplayManager.setupCamera(-100, -100, width, height);
    }

    public void bindObjects() {
        gameplayManager.bindControls(controlManager);
    }

    /**
     * Starts a thread for the <code>Window</code>.
     */
    public void start() {
        Thread th = new Thread(this);
        th.start();
    }

    /**
     * Renders to the window using a <code>BufferStrategy</code> and the
     * <code>Renderer2D</code>.
     */
    private void render() {
        do {
            // The following loop ensures that the contents of the drawing buffer
            // are consistent in case the underlying surface was recreated.
            do {
                // This gets a new graphics context every time through the loop
                // to make sure the strategy is validated.
                Graphics graphics = bufferStrategy.getDrawGraphics();

                // This clears the screen.
                graphics.setColor(clearColour);
                graphics.fillRect(0, 0, getWidth(), getHeight());

                if (menuManager.currentScreenContainsGameplay()) {
                    renderer2D.renderGameplay(gameplayManager, graphics);
                }
                renderer2D.renderMenuManager(menuManager, graphics);

                // This disposes the graphics that were in use.
                graphics.dispose();

            } while (bufferStrategy.contentsRestored());

            // This displays the buffer.
            bufferStrategy.show();

            // This will repeat the rendering if the drawing buffer was lost.
        } while (bufferStrategy.contentsLost());
    }

    /**
     * The method that runs inside of the <code>Window</code>'s thread. It is used
     * to update the <code>RuleManager</code> and render to the screen.
     */
    public void run() {
        boolean running = true;
        // This is the running loop.
        while (running) {
            gameplayManager.update();
            menuManager.update(controlManager.getMouse());
            controlManager.updateControlsAtEndOfFrame();
            // This renders the screen.
            render();
        }
    }
}
