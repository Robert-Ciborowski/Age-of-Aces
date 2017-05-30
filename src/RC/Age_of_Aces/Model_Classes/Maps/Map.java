package RC.Age_of_Aces.Model_Classes.Maps;/*
 * Name: Robert Ciborowski
 * Date: 2017-05-03
 * Assignment:
 * Description:
*/

import RC.Age_of_Aces.Model_Classes.Camera;
import RC.Age_of_Aces.Model_Classes.GameplayManager;
import RC.Age_of_Aces.Model_Classes.Math.Point;

public class Map {
    private TileManager tileManager = new TileManager();
    private StructureManager structureManager = new StructureManager();
    private UnitManager unitManager = new UnitManager();
    private int width = 0, height = 0;

    public Map() {

    }

    public void load(int id, GameplayManager gameplayHandle) {
        // Note: the tile images must be loaded before the map.
        tileManager.loadImages();
        tileManager.loadMap("resources/maps/map" + id + ".acemap", this);
        structureManager.loadStructureImages();
        unitManager.setMapHandle(this);
        unitManager.setGameplayHandle(gameplayHandle);
        unitManager.loadUnitImages();
    }



    public void setCameraProperties(Camera camera) {
        if (tileManager.getTileImages().size() != 0) {
            camera.setViewWidthInTiles((float) (camera.getView().getWidth() / (tileManager.getTileImages().get(0).getWidth() / 2)));
            camera.setViewHeightInTiles((float) (camera.getView().getHeight() / tileManager.getTileImages().get(0).getHeight()));
        }
    }

    public int gatherResource(Point location, int gatherAmount) {
        GameResource resource = tileManager.getTiles().get((int) location.getY()).get((int) location.getX()).getResource();
        if (resource != null) {
            if (gatherAmount > resource.deplete(gatherAmount)) {
                // The resource was fully depleted!
                tileManager.getTiles().get((int) location.getY()).get((int) location.getX()).setResource(null);
            } else {
                resource.getImage().setImage(tileManager.getResourceImages().get(resource.getType().ordinal()).get(1));
            }
            return gatherAmount;
        }
        return 0;
    }

    public long getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    public StructureManager getStructureManager() {
        return structureManager;
    }

    public UnitManager getUnitManager() {
        return unitManager;
    }

    public void setTileManager(TileManager tileManager) {
        this.tileManager = tileManager;
    }

    public void setStructureManager(StructureManager structureManager) {
        this.structureManager = structureManager;
    }

    public void setUnitManager(UnitManager unitManager) {
        this.unitManager = unitManager;
    }
}
