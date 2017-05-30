package RC.Age_of_Aces.Model_Classes.Menus;
 /*
 * Name: Robert Ciborowski
 * Date: 2017-01-31
 * Assignment: Pong Game
 * Description: A simple model class for labels.
*/

// These are the imports.
import RC.Age_of_Aces.Model_Classes.Math.Rect;

import java.awt.*;

// This is the MenuLabel class.
public class MenuLabel {
    // These are the properties of the class.
    Rect offset;
    String text;
    Color colour;
    Font font;

    // This is the default constructor.
    public MenuLabel() {
        text = "";
        offset = new Rect(0, 0, 0, 0);
        colour = Color.WHITE;
        font = new Font("Arial", Font.PLAIN, 12);
    }

    // These are other constructors.
    public MenuLabel(String setText) {
        text = setText;
        offset = new Rect(0, 0, 0, 0);
        colour = Color.WHITE;
        font = new Font("Arial", Font.PLAIN, 12);
    }

    public MenuLabel(String setText, Rect setOffset) {
        text = setText;
        offset = setOffset;
        colour = Color.WHITE;
        font = new Font("Arial", Font.PLAIN, 12);
    }

    public MenuLabel(String setText, Rect setOffset, Color setColour) {
        text = setText;
        offset = setOffset;
        colour = setColour;
        font = new Font("Arial", Font.PLAIN, 12);
    }

    public MenuLabel(String setText, Color setColour) {
        // Note!: This constructor should only be used for buttons since buttons
        //        do not need to look at the offset of the text.
        text = setText;
        offset = new Rect(0, 0, 0, 0);
        colour = setColour;
        font = new Font("Arial", Font.PLAIN, 12);
    }

    public MenuLabel(Rect setOffset) {
        text = "";
        offset = setOffset;
        colour = Color.WHITE;
        font = new Font("Arial", Font.PLAIN, 12);
    }

    // These are some basic getters and setters.
    public void setText(String setText) {
        text = setText;
    }

    public String getText() {
        return text;
    }

    public void setOffset(Rect setOffset) {
        offset = setOffset;
    }

    public Rect getOffset() {
        return offset;
    }

    public void setColour(Color setColour) {
        colour = setColour;
    }

    public Color getColour() {
        return colour;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }
}