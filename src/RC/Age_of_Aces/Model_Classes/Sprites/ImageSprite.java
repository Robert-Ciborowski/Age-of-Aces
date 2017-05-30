package RC.Age_of_Aces.Model_Classes.Sprites;
 /*
 * Name: Robert Ciborowski
 * Date: 2017-03-27
 * Assignment:
 * Description:
*/

import RC.Age_of_Aces.Model_Classes.Math.Rect;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageSprite extends Sprite {
    BufferedImage image;

    public ImageSprite() {
        super();
    }

    public ImageSprite(Rect setBoundingBox) {
        super(setBoundingBox);
    }

    public ImageSprite(double setX, double setY, double setWidth, double setHeight) {
        super(setX, setY, setWidth, setHeight);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    // This method loads the sprite's image.
    public boolean loadImage(String path) {
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public void setBoundingBoxToImage() {
        setBoundingBox(0, 0, image.getWidth(), image.getHeight());
    }
}
