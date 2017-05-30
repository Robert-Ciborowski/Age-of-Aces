package RC.Age_of_Aces.Model_Classes.Maps;
 /*
 * Name: Robert Ciborowski
 * Date: 2017-05-12
 * Assignment:
 * Description:
*/

import RC.Age_of_Aces.Model_Classes.Math.LineEquation;
import RC.Age_of_Aces.Model_Classes.Math.Point;
import RC.Age_of_Aces.Model_Classes.Math.Rect;
import RC.Age_of_Aces.Model_Classes.Sprites.ImageSprite;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class TileManager {
    private Point vertexA = new Point(), vertexB = new Point(), vertexC = new Point(), vertexD = new Point();
    private LineEquation lineAB = new LineEquation(), lineBC = new LineEquation(),
            lineCD = new LineEquation(), lineDA = new LineEquation();
    private ArrayList<ArrayList<Tile>> tiles = new ArrayList();
    private ArrayList<BufferedImage> tileImages = new ArrayList();
    private ArrayList<ArrayList<BufferedImage>> resourceImages = new ArrayList<>();

    public TileManager() {

    }

    public void loadImages() {
        tileImages.clear();
        resourceImages.clear();
        for (int i = 0; i < 2; i++) {
            try {
                BufferedImage image = ImageIO.read(new File("resources/images/tiles/tile" + i + ".png"));
                tileImages.add(image);
            } catch (IOException e) {

            }
        }
        for (int i = 0; i < 4; i++) {
            try {
                resourceImages.add(new ArrayList<BufferedImage>());
                BufferedImage image0 = ImageIO.read(new File("resources/images/game_resources/resource_" + i + "_0.png"));
                BufferedImage image1 = ImageIO.read(new File("resources/images/game_resources/resource_" + i + "_1.png"));
                resourceImages.get(i).add(image0);
                resourceImages.get(i).add(image1);
            } catch (IOException e) {

            }
        }
    }

    public void loadMap(String source, Map map) {
        Scanner input;
        String previousLine = "";
        int width = 0, height = 0;
        int tiles[][] = new int[0][0];
        int resources[][] = new int[0][0];

        try {
            input = new Scanner(new File(source));
        } catch (FileNotFoundException e) {
            System.out.println("Map does not exist!");
            return;
        }

        while (input.hasNextLine()) {
            String data = input.nextLine();
            if (previousLine.equals("width:")) {
                try {
                    width = Integer.parseInt(data);
                } catch (NumberFormatException e) {

                }
            } else if (previousLine.equals("height:")) {
                try {
                    height = Integer.parseInt(data);
                } catch (NumberFormatException e) {

                }
            } else if (previousLine.equals("tiles:")) {
                tiles = new int[height][width];
                for (int i = 0; i < height; i++) {
                    try {
                        data = input.nextLine();
                        String splitLine[] = data.split(",");
                        for (int j = 0; j < width; j++) {
                            try {
                                tiles[i][j] = Integer.parseInt(splitLine[j]);
                            } catch (NumberFormatException e) {
                                try {
                                    String splitLine2[] = splitLine[j].split(" ");
                                    if (splitLine2.length > 1) {
                                        tiles[i][j] = Integer.parseInt(splitLine2[1]);
                                    } else {
                                        tiles[i][j] = Integer.parseInt(splitLine2[0]);
                                    }
                                } catch (NumberFormatException e2) {
                                    // Well, I tried.
                                }
                            }
                        }
                    } catch (NoSuchElementException e) {

                    }
                }
            } else if (previousLine.equals("resources:")) {
                resources = new int[height][width];
                for (int i = 0; i < height; i++) {
                    try {
                        data = input.nextLine();
                        String splitLine[] = data.split(",");
                        for (int j = 0; j < width; j++) {
                            try {
                                resources[i][j] = Integer.parseInt(splitLine[j]);
                            } catch (NumberFormatException e) {
                                try {
                                    String splitLine2[] = splitLine[j].split(" ");
                                    if (splitLine2.length > 1) {
                                        resources[i][j] = Integer.parseInt(splitLine2[1]);
                                    } else {
                                        resources[i][j] = Integer.parseInt(splitLine2[0]);
                                    }
                                } catch (NumberFormatException e2) {
                                    // Well, I tried.
                                }
                            }
                        }
                    } catch (NoSuchElementException e) {

                    }
                }
            }
            previousLine = data;
        }

        load(map, width, height, tiles, resources);
    }

    private void load(Map map, int width, int height, int tileData[][], int resourceData[][]) {
        TileManager tileManager = map.getTileManager();
        if (tileManager.getTileImages().size() != 0) {
            map.setWidth(width);
            map.setHeight(height);
            ArrayList<ArrayList<Tile>> tiles = new ArrayList();
            for (int i = 0; i < height; i++) {
                ArrayList<Tile> row = new ArrayList();
                int lowerTile = 0;
                for (int j = 0; j < width; j++) {
                    Tile tile = new Tile();
                    tile.getImage().setImage(tileManager.getTileImages().get(tileData[i][j]));
                    tile.setWalkable(isWalkable(tileData[i][j]));
                    tile.setPositionInMap(new Point(j, i));
                    tile.getImage().setBoundingBoxToImage();
                    tile.getImage().setBoundingBox(j * tile.getImage().getBoundingBox().getWidth() / 2,
                            i * tile.getImage().getBoundingBox().getHeight() + (lowerTile * tile.getImage().getBoundingBox().getHeight() / 2),
                            tile.getImage().getBoundingBox().getWidth(), tile.getImage().getBoundingBox().getHeight());
                    row.add(tile);
                    if (lowerTile == 0) {
                        lowerTile = 1;
                    } else {
                        lowerTile = 0;
                    }
                }
                tiles.add(row);
            }
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (resourceData[i][j] != 0) {
                        Tile tile = tiles.get(i).get(j);
                        Rect tileBoundingBox = tile.getImage().getBoundingBox();
                        GameResource resource = new GameResource();
                        resource.setBasedOnID(resourceData[i][j]);
                        resource.setAmount(10);
                        resource.setImage(new ImageSprite());
                        resource.getImage().setImage(resourceImages.get(resourceData[i][j] - 1).get(0));
                        resource.getImage().setBoundingBox(tileBoundingBox.getXPosition(), 0,
                                tileBoundingBox.getWidth(), 0);
                        resource.getImage().getBoundingBox().setYPosition(tileBoundingBox.getYPosition() - (resource.getImage().getImage().getHeight() - tileBoundingBox.getHeight()));
                        resource.getImage().getBoundingBox().setHeight(resource.getImage().getImage().getHeight());
                        tile.setResource(resource);
                    }
                }
            }
            tileManager.setTiles(tiles);
            setupVertexes(map);
        } else {
            System.out.println("You forgot to load the tile images!");
        }
    }

    private void setupVertexes(Map map) {
        Rect box = map.getTileManager().getTiles().get(0).get(0).getImage().getBoundingBox();
        vertexA.setLocation(box.getWidth(), box.getHeight() / 2);
        vertexB.setLocation(box.getWidth() / 2, 0);
        vertexC.setLocation(0, box.getHeight() / 2);
        vertexD.setLocation(box.getWidth() / 2, box.getHeight());
        lineAB.calculate(vertexA, vertexB);
        lineBC.calculate(vertexB, vertexC);
        lineCD.calculate(vertexC, vertexD);
        lineDA.calculate(vertexD, vertexA);
    }

    private boolean isWalkable(int tileID) {
        if (tileID == 1) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<ArrayList<Tile>> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<ArrayList<Tile>> tiles) {
        this.tiles = tiles;
    }

    public ArrayList<BufferedImage> getTileImages() {
        return tileImages;
    }

    public void setTileImages(ArrayList<BufferedImage> tileImages) {
        this.tileImages = tileImages;
    }

    public Point getVertexA() {
        return vertexA;
    }

    public void setVertexA(Point vertexA) {
        this.vertexA = vertexA;
    }

    public Point getVertexB() {
        return vertexB;
    }

    public void setVertexB(Point vertexB) {
        this.vertexB = vertexB;
    }

    public Point getVertexC() {
        return vertexC;
    }

    public void setVertexC(Point vertexC) {
        this.vertexC = vertexC;
    }

    public Point getVertexD() {
        return vertexD;
    }

    public void setVertexD(Point vertexD) {
        this.vertexD = vertexD;
    }

    public LineEquation getLineAB() {
        return lineAB;
    }

    public void setLineAB(LineEquation lineAB) {
        this.lineAB = lineAB;
    }

    public LineEquation getLineBC() {
        return lineBC;
    }

    public void setLineBC(LineEquation lineBC) {
        this.lineBC = lineBC;
    }

    public LineEquation getLineCD() {
        return lineCD;
    }

    public void setLineCD(LineEquation lineCD) {
        this.lineCD = lineCD;
    }

    public LineEquation getLineDA() {
        return lineDA;
    }

    public void setLineDA(LineEquation lineDA) {
        this.lineDA = lineDA;
    }

    public ArrayList<ArrayList<BufferedImage>> getResourceImages() {
        return resourceImages;
    }

    public void setResourceImages(ArrayList<ArrayList<BufferedImage>> resourceImages) {
        this.resourceImages = resourceImages;
    }
}
