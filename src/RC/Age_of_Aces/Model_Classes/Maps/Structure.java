/*
 * Name: Robert Ciborowski
 * Date: 2017-05-03
 * Assignment:
 * Description:
*/

package RC.Age_of_Aces.Model_Classes.Maps;

import RC.Age_of_Aces.Model_Classes.Math.Point;
import RC.Age_of_Aces.Model_Classes.Math.Rect;
import RC.Age_of_Aces.Model_Classes.Sprites.ImageSprite;

public class Structure {
    public enum StructureType {TOWN_CENTER, MILITIA_CREATOR, SPEARMAN_CREATOR, MILITIA_UPGRADER};
    private StructureType type;
    private ImageSprite image = new ImageSprite();
    private int imageID;
    private Rect dimensionsOnMap;
    private Point topLeftTile;
    private int health;

    public Structure() {
        type = StructureType.TOWN_CENTER;
        imageID = 0;
        dimensionsOnMap = new Rect(0, 0, 1, 1);
        topLeftTile = new Point(0, 0);
    }

    public StructureType getType() {
        return type;
    }

    public void setType(StructureType type) {
        this.type = type;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public Rect getDimensionsOnMap() {
        return dimensionsOnMap;
    }

    public void setDimensionsOnMap(Rect dimensionsOnMap) {
        this.dimensionsOnMap = dimensionsOnMap;
    }

    public ImageSprite getImage() {
        return image;
    }

    public void setImage(ImageSprite image) {
        this.image = image;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Point getTopLeftTile() {
        return topLeftTile;
    }

    public void setTopLeftTile(Point topLeftTile) {
        this.topLeftTile = topLeftTile;
    }
}
