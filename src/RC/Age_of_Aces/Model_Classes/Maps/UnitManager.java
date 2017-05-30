package RC.Age_of_Aces.Model_Classes.Maps;
 /*
 * Name: Robert Ciborowski
 * Date: 2017-05-12
 * Assignment:
 * Description:
*/

import RC.Age_of_Aces.Model_Classes.GameplayManager;
import RC.Age_of_Aces.Model_Classes.Math.PathSegment;
import RC.Age_of_Aces.Model_Classes.Math.Point;
import RC.Age_of_Aces.Model_Classes.Math.Rect;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class UnitManager {
    ArrayList<Unit> units = new ArrayList();
    ArrayList<ArrayList<ArrayList<BufferedImage>>> unitImages = new ArrayList();
    Map mapHandle;
    GameplayManager gameplayHandle;
    int selectedUnit = 0;
    int villagerHealthModifier = 0, villagerAttackModifier = 0, militiaHealthModifier = 0,
            militiaAttackModifier = 0, spearmanHealthModifier = 0, spearmanAttackModifier = 0;
    final int villagerBaseHealth = 10, villagerBaseAttack = 1, militiaBaseHealth = 30,
        militiaBaseAttack = 5, spearmanBaseHealth = 35, spearmanBaseAttack = 6;
    final double villagerSpeed = 0.05, militiaSpeed = 0.075, spearmanSpeed = 0.0625;

    public UnitManager() {

    }

    public void loadUnitImages() {
        unitImages.clear();
        for (int i = 0; i < 3; i++) {
            unitImages.add(new ArrayList<ArrayList<BufferedImage>>());
            try {
                for (int j = 0; j < 4; j++) {
                    unitImages.get(i).add(new ArrayList<BufferedImage>());
                    for (int k = 0; k < 3; k++) {
                        BufferedImage image = ImageIO.read(new File("resources/images/units/unit_" + i + "/" + j + "_" + k + ".png"));
                        unitImages.get(i).get(j).add(image);
                    }
                }
            } catch (IOException e) {

            }
        }
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public void setUnits(ArrayList<Unit> units) {
        this.units = units;
    }

    public ArrayList<ArrayList<ArrayList<BufferedImage>>> getUnitImages() {
        return unitImages;
    }

    public void setUnitImages(ArrayList<ArrayList<ArrayList<BufferedImage>>> unitImages) {
        this.unitImages = unitImages;
    }

    public void createUnit(Unit.UnitType type, Point locationOnMap, int tileWidth, int tileHeight) {
        Unit unit = new Unit();
        unit.setType(type);
        unit.setLocationOnMap(locationOnMap);
        int unitImage = 0;

        switch (type) {
            case VILLAGER:
                unitImage = 0;
                unit.getImage().setImage(unitImages.get(0).get(0).get(0));
                unit.setHealth(villagerBaseHealth + villagerHealthModifier);
                unit.setAttack(villagerBaseAttack + villagerAttackModifier);
                unit.setSpeed(villagerSpeed);
                break;
            case MILITIA:
                unitImage = 1;
                unit.getImage().setImage(unitImages.get(1).get(0).get(0));
                unit.setHealth(militiaBaseHealth + militiaHealthModifier);
                unit.setAttack(militiaBaseAttack + militiaAttackModifier);
                unit.setSpeed(militiaSpeed);
                break;
            case SPEARMAN:
                unitImage = 2;
                unit.getImage().setImage(unitImages.get(2).get(0).get(0));
                unit.setHealth(spearmanBaseHealth + spearmanHealthModifier);
                unit.setAttack(spearmanBaseAttack + spearmanAttackModifier);
                unit.setSpeed(spearmanSpeed);
                break;
        }

        unit.setImageID(unitImage);
        unit.getImage().setBoundingBoxToImage();
        unit.getImage().setBoundingBox(new Rect(locationOnMap.getX() * (tileWidth / 2),
                (locationOnMap.getY() - 1) * tileHeight + ((locationOnMap.getX() % 2) * (tileHeight / 2)),
                unitImages.get(unitImage).get(0).get(0).getWidth(), unitImages.get(unitImage).get(0).get(0).getHeight()));
        units.add(unit);
    }

    public void changeUnitPosition(Unit unit, Point locationOnMap, int tileWidth, int tileHeight, double xOffset, double yOffset) {
        unit.getImage().setBoundingBox(new Rect(locationOnMap.getX() * (tileWidth / 2) + xOffset,
                (locationOnMap.getY() - 1) * tileHeight + ((locationOnMap.getX() % 2) * (tileHeight / 2)) + yOffset,
                unit.getImage().getImage().getWidth(), unit.getImage().getImage().getHeight()));
    }

    public void updateUnitMovements(int tileWidth, int tileHeight) {
        for (Unit unit : units) {
            if (unit.getCurrentMovement() != null && unit.getMovements() != null) {
                long time = System.currentTimeMillis();
                if (unit.getCurrentMovement().hasReachedMagnitude(time)) {
                    long timeResidue = (long) ((time - unit.getCurrentMovement().getStartTimeOfMovement()) - (unit.getCurrentMovement().getMagnitude()) / unit.getSpeed());
                    unit.setLocationOnMap(unit.getMovements().get(1));
                    unit.getMovements().remove(0);
                    if (unit.getMovements().size() > 1) {
                        generateUnitMovement(unit, unit.getMovements().get(0), unit.getMovements().get(1), tileWidth, tileHeight);
                        unit.getCurrentMovement().setStartTimeOfMovement(time - timeResidue);
                    } else {
                        unit.setMovements(null);
                        unit.setCurrentMovement(null);
                        unit.setStep(0);
                        changeUnitPosition(unit, unit.getLocationOnMap(), tileWidth, tileHeight, 0, 0);
                        gatherResourceUsingUnit(unit);
                        continue;
                    }
                }
                changeUnitPosition(unit, unit.getLocationOnMap(), tileWidth, tileHeight, unit.getCurrentMovement().getXDisplacement(time - unit.getCurrentMovement().getStartTimeOfMovement()),
                        unit.getCurrentMovement().getYDisplacement(time - unit.getCurrentMovement().getStartTimeOfMovement()));
            }
        }
    }

    public void gatherResourceUsingUnit(Unit unit) {
        GameResource resource = mapHandle.getTileManager().getTiles().get((int) unit.getLocationOnMap().getY()).get((int) unit.getLocationOnMap().getX()).getResource();
        GameResource.Type type;
        if (resource != null && unit.getType() == Unit.UnitType.VILLAGER) {
            type = resource.getType();
            gameplayHandle.incrementResource(type, mapHandle.gatherResource(unit.getLocationOnMap(), unit.getAttack()));
        }
    }

    public boolean moveUnitToTile(Unit unit, Map map, Point endPoint, int tileWidth, int tileHeight) {
        boolean state = UnitPathFinder.generatePath(unit, map, endPoint);
        unit.setMovements(UnitPathFinder.getPath());
        if (state && unit.getMovements().size() > 1) {
            generateUnitMovement(unit, unit.getMovements().get(0), unit.getMovements().get(1), tileWidth, tileHeight);
        } else if (state && unit.getMovements().size() == 1) {
            unit.setMovements(null);
            unit.setCurrentMovement(null);
            unit.setStep(0);
            gatherResourceUsingUnit(unit);
        }
        return state;
    }

    private boolean generateUnitMovement(Unit unit, Point a, Point b, int tileWidth, int tileHeight) {
        PathSegment path = new PathSegment();
        if (unit.getMovements().size() > 1) {
            path.setDirection(UnitPathFinder.getDirectionBasedOnTileMovement(a, b));
            path.setMagnitude(Math.sqrt(Math.pow(tileWidth / 2, 2) + Math.pow(tileHeight / 2, 2)));
            path.setSpeed(unit.getSpeed());
            path.setStartTimeOfMovement(System.currentTimeMillis());
            unit.switchStep();
            if (path.getDirection() == UnitPathFinder.DOWN_RIGHT_DIRECTION) {
                unit.setImageID(0);
                unit.getImage().setImage(unitImages.get(unit.getType().ordinal()).get(0).get(unit.getStep()));
            } else if (path.getDirection() == UnitPathFinder.DOWN_LEFT_DIRECTION) {
                unit.setImageID(1);
                unit.getImage().setImage(unitImages.get(unit.getType().ordinal()).get(1).get(unit.getStep()));
            } else if (path.getDirection() == UnitPathFinder.UP_LEFT_DIRECTION) {
                unit.setImageID(2);
                unit.getImage().setImage(unitImages.get(unit.getType().ordinal()).get(2).get(unit.getStep()));
            } else if (path.getDirection() == UnitPathFinder.UP_RIGHT_DIRECTION) {
                unit.setImageID(3);
                unit.getImage().setImage(unitImages.get(unit.getType().ordinal()).get(3).get(unit.getStep()));
            }
            unit.setCurrentMovement(path);
            return true;
        }
        return false;
    }

    public int getVillagerHealthModifier() {
        return villagerHealthModifier;
    }

    public void setVillagerHealthModifier(int villagerHealthModifier) {
        int differenceInHealth = villagerHealthModifier - this.villagerHealthModifier;
        this.villagerHealthModifier = villagerHealthModifier;
        for (Unit unit : units) {
            if (unit.getType() == Unit.UnitType.VILLAGER) {
                unit.setHealth(unit.getHealth() + differenceInHealth);
            }
        }
    }

    public int getVillagerAttackModifier() {
        return villagerAttackModifier;
    }

    public void setVillagerAttackModifier(int villagerAttackModifier) {
        int differenceInAttack = villagerAttackModifier - this.villagerAttackModifier;
        this.villagerAttackModifier = villagerAttackModifier;
        for (Unit unit : units) {
            if (unit.getType() == Unit.UnitType.VILLAGER) {
                unit.setAttack(unit.getAttack() + differenceInAttack);
            }
        }
    }

    public int getMilitiaHealthModifier() {
        return militiaHealthModifier;
    }

    public void setMilitiaHealthModifier(int militiaHealthModifier) {
        int differenceInHealth = militiaHealthModifier - this.militiaHealthModifier;
        this.militiaHealthModifier = militiaHealthModifier;
        for (Unit unit : units) {
            if (unit.getType() == Unit.UnitType.MILITIA) {
                unit.setHealth(unit.getHealth() + differenceInHealth);
            }
        }
    }

    public int getMilitiaAttackModifier() {
        return militiaAttackModifier;
    }

    public void setMilitiaAttackModifier(int militiaAttackModifier) {
        int differenceInAttack = militiaAttackModifier - this.militiaAttackModifier;
        this.militiaAttackModifier = militiaAttackModifier;
        for (Unit unit : units) {
            if (unit.getType() == Unit.UnitType.MILITIA) {
                unit.setAttack(unit.getAttack() + differenceInAttack);
            }
        }
    }

    public int getSpearmanHealthModifier() {
        return spearmanHealthModifier;
    }

    public void setSpearmanHealthModifier(int spearmanHealthModifier) {
        int differenceInHealth = spearmanHealthModifier - this.spearmanHealthModifier;
        this.spearmanHealthModifier = spearmanHealthModifier;
        for (Unit unit : units) {
            if (unit.getType() == Unit.UnitType.SPEARMAN) {
                unit.setHealth(unit.getHealth() + differenceInHealth);
            }
        }
    }

    public int getSpearmanAttackModifier() {
        return spearmanAttackModifier;
    }

    public void setSpearmanAttackModifier(int spearmanAttackModifier) {
        int differenceInAttack = spearmanAttackModifier - this.spearmanAttackModifier;
        this.spearmanAttackModifier = spearmanAttackModifier;
        for (Unit unit : units) {
            if (unit.getType() == Unit.UnitType.SPEARMAN) {
                unit.setAttack(unit.getAttack() + differenceInAttack);
            }
        }
    }

    public Unit getUnitAtTile(Point position) {
        for (Unit unit : units) {
            if (unit.getLocationOnMap().getX() == position.getX() && unit.getLocationOnMap().getY() == position.getY()) {
                return unit;
            }
        }
        return null;
    }

    public Map getMapHandle() {
        return mapHandle;
    }

    public void setMapHandle(Map mapHandle) {
        this.mapHandle = mapHandle;
    }

    public GameplayManager getGameplayHandle() {
        return gameplayHandle;
    }

    public void setGameplayHandle(GameplayManager gameplayHandle) {
        this.gameplayHandle = gameplayHandle;
    }
}
