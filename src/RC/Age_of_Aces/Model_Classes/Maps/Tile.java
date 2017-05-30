package RC.Age_of_Aces.Model_Classes.Maps;/*
 * Name: Robert Ciborowski
 * Date: 2017-05-03
 * Assignment:
 * Description:
*/

import RC.Age_of_Aces.Model_Classes.Math.*;
import RC.Age_of_Aces.Model_Classes.Sprites.ImageSprite;

public class Tile {
    private ImageSprite image;
    private Point positionInMap;
    private GameResource resource;
    private boolean highlighted = false, walkable = false;

    public Tile() {
        image = new ImageSprite();
    }

    public Tile(String imagePath) {
        image = new ImageSprite();
        image.loadImage(imagePath);
        positionInMap = new Point(0, 0);
    }

    public ImageSprite getImage() {
        return image;
    }

    public void setImage(ImageSprite image) {
        this.image = image;
    }

    public Point getPositionInMap() {
        return positionInMap;
    }

    public void setPositionInMap(Point positionInMap) {
        this.positionInMap = positionInMap;
    }

    public boolean isHighlighted() {
        return highlighted;
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

    public GameResource getResource() {
        return resource;
    }

    public void setResource(GameResource resource) {
        this.resource = resource;
    }
}
