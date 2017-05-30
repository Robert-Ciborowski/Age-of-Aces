package RC.Age_of_Aces.Model_Classes.Maps;
 /*
 * Name: Robert Ciborowski
 * Date: 2017-05-10
 * Assignment:
 * Description:
*/

import RC.Age_of_Aces.Model_Classes.Math.Point;
import RC.Age_of_Aces.Model_Classes.Math.Rect;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class StructureManager {
    private ArrayList<Structure> structures = new ArrayList();
    private ArrayList<BufferedImage> structureImages = new ArrayList();
    private int selectedStructure = 0;
    final int TOWN_CENTER_HEALTH = 300, MILITIA_CREATOR_HEALTH = 150, SPEARMAN_CREATOR_HEALTH = 150, MILITIA_UPGRADER_HEALTH = 150;


    public StructureManager() {

    }

    public void loadStructureImages() {
        structureImages.clear();
        for (int i = 0; i < 2; i++) {
            try {
                BufferedImage image = ImageIO.read(new File("resources/images/structures/structure" + i + ".png"));
                structureImages.add(image);
            } catch (IOException e) {

            }
        }
    }
    
    public void createStructure(Structure.StructureType type, Rect dimensionsOnMap, int tileWidth, int tileHeight) {
        Structure structure = new Structure();
        structure.setType(type);
        structure.setDimensionsOnMap(dimensionsOnMap);
        int structureImage = 0;

        switch (type) {
            case TOWN_CENTER:
                structureImage = 0;
                structure.getImage().setImage(structureImages.get(0));
                structure.setHealth(TOWN_CENTER_HEALTH);
                break;
            case MILITIA_CREATOR:
                structureImage = 1;
                structure.getImage().setImage(structureImages.get(1));
                structure.setHealth(MILITIA_CREATOR_HEALTH);
                break;
            case SPEARMAN_CREATOR:
                structureImage = 1;
                structure.getImage().setImage(structureImages.get(1));
                structure.setHealth(SPEARMAN_CREATOR_HEALTH);
                break;
            case MILITIA_UPGRADER:
                structureImage = 1;
                structure.getImage().setImage(structureImages.get(1));
                structure.setHealth(MILITIA_UPGRADER_HEALTH);
                break;
        }

        structure.setImageID(structureImage);
        structure.getImage().setBoundingBoxToImage();
        structure.getImage().setBoundingBox(new Rect((int) (dimensionsOnMap.getXPosition() * (tileWidth / 2)),
                (int) ((dimensionsOnMap.getYPosition() - (int) (dimensionsOnMap.getHeight() / 2) - 1) * tileHeight + ((dimensionsOnMap.getXPosition() % 2) * (tileHeight / 2))),
                structureImages.get(structureImage).getWidth(), structureImages.get(structureImage).getHeight()));
        structure.setTopLeftTile(new Point(dimensionsOnMap.getXPosition(), ((dimensionsOnMap.getYPosition() - (int) (dimensionsOnMap.getHeight() / 2) - 1))));
        structures.add(structure);
    }

    public ArrayList<Structure> getStructures() {
        return structures;
    }

    public void setStructures(ArrayList<Structure> structures) {
        this.structures = structures;
    }

    public ArrayList<BufferedImage> getStructureImages() {
        return structureImages;
    }

    public void setStructureImages(ArrayList<BufferedImage> structureImages) {
        this.structureImages = structureImages;
    }

    public Structure getStructureAtTile(Point position) {
        for (Structure structure : structures) {
            Point testingPosition = new Point(structure.getDimensionsOnMap().getXPosition(), structure.getDimensionsOnMap().getYPosition());
            for (int i = 0; i < structure.getDimensionsOnMap().getHeight(); i++) {
                testingPosition.setX(structure.getDimensionsOnMap().getXPosition() + i);
                testingPosition.setY(structure.getDimensionsOnMap().getYPosition() + Math.floor((i + (structure.getDimensionsOnMap().getXPosition() % 2)) / 2.0));
                if (structure.getDimensionsOnMap().getXPosition() % 2 == 1) {
                    testingPosition.setY(testingPosition.getY() - 1);
                }
                for (int j = 0; j < structure.getDimensionsOnMap().getWidth(); j++) {
                    double x = testingPosition.getX(), y = testingPosition.getY();
                    // System.out.println(x + "is x, " + y + " is y");
                    if (position.getX() == x && position.getY() == y) {
                        return structure;
                    }
                    if (x % 2 == 1) {
                        x++;
                    } else {
                        x++;
                        y--;
                    }
                    testingPosition.setX(x);
                    testingPosition.setY(y);
                }
            }
        }
        return null;
    }

    public Structure getRendererdStructureAtTile(Point point) {
        for (Structure structure : structures) {
            if (point.getX() >= structure.getTopLeftTile().getX() && point.getY() >= structure.getTopLeftTile().getY()
                    && point.getX() <= structure.getTopLeftTile().getX() + (structure.getDimensionsOnMap().getWidth())
                    && point.getY() <= structure.getTopLeftTile().getY() + structure.getDimensionsOnMap().getHeight()) {
                return structure;
            }
        }
        return null;
    }
}
