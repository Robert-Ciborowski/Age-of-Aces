package RC.Age_of_Aces.Model_Classes.Menus;
 /*
 * Name: Robert Ciborowski
 * Date: 2017-01-27
 * Assignment: Pong Game
 * Description: A simple model class for backdrops.
*/

import RC.Age_of_Aces.Model_Classes.Math.Rect;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Backdrop {
    Rect cropping;
    Rect boundingBox;
    BufferedImage image;

    public Backdrop() {

    }

    public void setCropping(Rect setCropping) {
        cropping = setCropping;
    }

    public void setCropping(int x, int y, int w, int h) {
        cropping = new Rect(x, y, w, h);
    }

    public void setBoundingBox(Rect setBoundingBox) {
        cropping = setBoundingBox;
    }

    public void setBoundingBox(int x, int y, int w, int h) {
        boundingBox = new Rect(x, y, w, h);
    }

    public void setImage(BufferedImage setImage) {
        image = setImage;
        if (cropping == null) {
            cropping = new Rect(0, 0, image.getWidth(), image.getHeight());
            boundingBox = new Rect(0, 0, image.getWidth(), image.getHeight());
        }
    }

    public boolean loadImage(String path) {
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            return false;
        }
        if (cropping == null) {
            cropping = new Rect(0, 0, image.getWidth(), image.getHeight());
            boundingBox = new Rect(0, 0, image.getWidth(), image.getHeight());
        }
        return true;
    }

    public BufferedImage getImage() {
        return image;
    }

    public Rect getCropping() {
        return cropping;
    }

    public Rect getBoundingBox() {
        return boundingBox;
    }
}
