/*
 * Name: Robert Ciborowski
 * Date: 2017-05-03
 * Assignment:
 * Description:
*/

package RC.Age_of_Aces.Model_Classes;

import RC.Age_of_Aces.ControllerClasses.ControlManager;
import RC.Age_of_Aces.Model_Classes.Maps.GameResource;
import RC.Age_of_Aces.Model_Classes.Maps.Map;
import RC.Age_of_Aces.Model_Classes.Maps.Structure;
import RC.Age_of_Aces.Model_Classes.Maps.Unit;
import RC.Age_of_Aces.Model_Classes.Math.LineEquation;
import RC.Age_of_Aces.Model_Classes.Math.Point;
import RC.Age_of_Aces.Model_Classes.Math.Rect;
import RC.Age_of_Aces.Model_Classes.Menus.MenuButton;
import RC.Age_of_Aces.Model_Classes.Menus.MenuLabel;
import RC.Age_of_Aces.Model_Classes.Sprites.ImageSprite;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameplayManager {
	private Camera camera;
	private Map currentMap;
	private ControlManager controlManager;

	private java.awt.Point mouseLocationOnRightClick;
    private Point mouseTileLocation = new Point(0, 0);
	private boolean wasAlreadyRightClicked = false;

	private int highlightedTileX = 0, highlightedTileY = 0;

	private MenuLabel foodLabel, woodLabel, stoneLabel, goldLabel;
	private int foodAmount = 0, woodAmount = 0, stoneAmount = 0, goldAmount = 0;
	private MenuLabel optionWindowText;
	private MenuButton optionWindowButton;
	private ImageSprite optionWindowImage;

    private Unit selectedUnit;

	public GameplayManager() {
		camera = new Camera();
		currentMap = new Map();
	}

	public Camera getCamera() {
		return camera;
	}

	public Map getCurrentMap() {
		return currentMap;
	}

	public void setCurrentMap(Map currentMap) {
		this.currentMap = currentMap;
	}

	public void loadMap(int mapID) {
		currentMap.load(mapID, this);
		currentMap.getUnitManager().createUnit(Unit.UnitType.VILLAGER, new Point(6, 5),
				currentMap.getTileManager().getTileImages().get(0).getTileWidth(),
				currentMap.getTileManager().getTileImages().get(0).getTileHeight());
		currentMap.getUnitManager().createUnit(Unit.UnitType.MILITIA, new Point(7, 7),
				currentMap.getTileManager().getTileImages().get(0).getTileWidth(),
				currentMap.getTileManager().getTileImages().get(0).getTileHeight());
		currentMap.getStructureManager().createStructure(Structure.StructureType.TOWN_CENTER,
				new Rect(38, 10, 7, 7),
				currentMap.getTileManager().getTileImages().get(0).getTileWidth(),
				currentMap.getTileManager().getTileImages().get(0).getTileHeight());
	}

	public void bindControls(ControlManager controls) {
		ActionListener action2 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("KEY_PRESSED") && e.getID() == 39) {
					camera.getView().setXPosition(camera.getView().getXPosition() - 10);
				}
				if (e.getActionCommand().equals("KEY_PRESSED") && e.getID() == 37) {
					camera.getView().setXPosition(camera.getView().getXPosition() + 10);
				}
				if (e.getActionCommand().equals("KEY_PRESSED") && e.getID() == 38) {
					camera.getView().setYPosition(camera.getView().getYPosition() + 10);
				}
				if (e.getActionCommand().equals("KEY_PRESSED") && e.getID() == 40) {
					camera.getView().setYPosition(camera.getView().getYPosition() - 10);
				}
			}
		};
		controls.addActionListener(action2);
		controlManager = controls;
	}

	public void update() {
		updateMouse();
		updateTileHighlighting();
		currentMap.getUnitManager().updateUnitMovements(currentMap.getTileManager().getTileImages().get(0).getWidth(),
				currentMap.getTileManager().getTileImages().get(0).getHeight());
	}

	public void setupCamera(int startingX, int startingY, int width, int height) {
		camera.setView(new Rect(startingX, startingY, width, height));
		currentMap.setCameraProperties(camera);
	}

	private void updateMouse() {
		if (controlManager.getMouse().isRightPressed()) {
            if (!wasAlreadyRightClicked) {
				wasAlreadyRightClicked = true;
				mouseLocationOnRightClick = controlManager.getMouse().getMouseLocationAsJavaPoint();
			}
			double xDisplacement = controlManager.getMouse().getMouseLocationAsJavaPoint().getX() - mouseLocationOnRightClick.getX();
			double yDisplacement = controlManager.getMouse().getMouseLocationAsJavaPoint().getY() - mouseLocationOnRightClick.getY();
			camera.getView().setXPosition(camera.getView().getXPosition() + xDisplacement);
			camera.getView().setYPosition(camera.getView().getYPosition() + yDisplacement);
			double tileWidth = currentMap.getTileManager().getTiles().get(0).get(0).getImage().getBoundingBox().getWidth(),
				tileHeight = currentMap.getTileManager().getTiles().get(0).get(0).getImage().getBoundingBox().getHeight();
			if (camera.getView().getXPosition() > 0 - tileWidth / 2.0) {
				camera.getView().setXPosition(0 - tileWidth / 2.0);
			}
			if (camera.getView().getYPosition() > 0) {
				camera.getView().setYPosition(0);
			}
			if (-camera.getView().getXPosition() + (camera.getViewWidthInTiles() * tileWidth / 2.0) > (currentMap.getWidth() * tileWidth / 2.0)) {
				camera.getView().setXPosition(-((currentMap.getWidth() * tileWidth / 2.0) - (camera.getViewWidthInTiles() * tileWidth / 2.0)));
			}
			if (-camera.getView().getYPosition() + (camera.getViewHeightInTiles() * tileHeight) > (currentMap.getHeight() * tileHeight)) {
				camera.getView().setYPosition(-((currentMap.getHeight() * tileHeight) - (camera.getViewHeightInTiles() * tileHeight)));
			}
			mouseLocationOnRightClick = controlManager.getMouse().getMouseLocationAsJavaPoint();
		} else {
			wasAlreadyRightClicked = false;
		}

        if (controlManager.getMouse().isLeftClicked()) {
            Unit unit = currentMap.getUnitManager().getUnitAtTile(mouseTileLocation);
            if (unit != null) {
                selectUnit(unit);
            } else {
                Structure structure = currentMap.getStructureManager().getStructureAtTile(mouseTileLocation);
                if (structure != null) {
                    selectStructure(structure);
                } else {
                    selectNothing();
                }
            }
        }

        if (controlManager.getMouse().isRightClicked() && selectedUnit != null) {
            currentMap.getUnitManager().moveUnitToTile(selectedUnit, currentMap, mouseTileLocation,
					currentMap.getTileManager().getTileImages().get(0).getWidth(), currentMap.getTileManager().getTileImages().get(0).getHeight());
        }
	}

	private void updateTileHighlighting() {
		// This finds one of the tiles that is being highlighted.
		int tileWidth = (int) currentMap.getTileManager().getTiles().get(0).get(0).getImage().getBoundingBox().getWidth(),
			tileHeight = (int) currentMap.getTileManager().getTiles().get(0).get(0).getImage().getBoundingBox().getHeight();
		double mousePositionX = (-camera.getView().getXPosition() + controlManager.getMouse().getMouseLocationAsJavaPoint().getX()),
			mousePositionY = (-camera.getView().getYPosition() + controlManager.getMouse().getMouseLocationAsJavaPoint().getY());
		int mouseTileX = (int) (2 * Math.floor(mousePositionX / tileWidth)),
			mouseTileY = (int) Math.floor(mousePositionY / tileHeight);

		if (mouseTileX >= 0 && mouseTileY >= 0 && mouseTileX < currentMap.getWidth() && mouseTileY < currentMap.getHeight()) {
			currentMap.getTileManager().getTiles().get(highlightedTileY).get(highlightedTileX).setHighlighted(false);

			// This figures out which of the two tiles is actually being highlighted.
			int mouseXRelativeToTile = (int) (mousePositionX - (mouseTileX * tileWidth / 2)),
				mouseYRelativeToTile = (int) (mousePositionY - (mouseTileY * tileHeight));
			int halfTileWidth = tileWidth / 2, halfTileHeight = tileHeight / 2;

			if (mouseXRelativeToTile > halfTileWidth && mouseYRelativeToTile < halfTileHeight) {
				// If this code is run, the mouse is in quadrant 1 of the tile.
				LineEquation perpendicular = currentMap.getTileManager().getLineAB().getPerpendicular();
				perpendicular.setBUsingPoint(new Point(mouseXRelativeToTile, mouseYRelativeToTile));
				Point intersection = currentMap.getTileManager().getLineAB().getIntersection(perpendicular);
				if (mouseXRelativeToTile > intersection.getX()) {
					mouseTileY--;
					mouseTileX++;
				}
			} else if (mouseXRelativeToTile < halfTileWidth && mouseYRelativeToTile < halfTileHeight) {
				// If this code is run, the mouse is in quadrant 2 of the tile.
				LineEquation perpendicular = currentMap.getTileManager().getLineBC().getPerpendicular();
				perpendicular.setBUsingPoint(new Point(mouseXRelativeToTile, mouseYRelativeToTile));
				Point intersection = currentMap.getTileManager().getLineBC().getIntersection(perpendicular);
				if (mouseXRelativeToTile < intersection.getX()) {
					mouseTileX--;
					mouseTileY--;
				}
			} else if (mouseXRelativeToTile < halfTileWidth && mouseYRelativeToTile > halfTileHeight) {
				// If this code is run, the mouse is in quadrant 3 of the tile.
				LineEquation perpendicular = currentMap.getTileManager().getLineCD().getPerpendicular();
				perpendicular.setBUsingPoint(new Point(mouseXRelativeToTile, mouseYRelativeToTile));
				Point intersection = currentMap.getTileManager().getLineCD().getIntersection(perpendicular);
				if (mouseXRelativeToTile < intersection.getX()) {
					mouseTileX--;
				}

			} else if (mouseXRelativeToTile > halfTileWidth && mouseYRelativeToTile > halfTileHeight) {
				// If this code is run, the mouse is in quadrant 4 of the tile.
				LineEquation perpendicular = currentMap.getTileManager().getLineDA().getPerpendicular();
				perpendicular.setBUsingPoint(new Point(mouseXRelativeToTile, mouseYRelativeToTile));
				Point intersection = currentMap.getTileManager().getLineDA().getIntersection(perpendicular);
				if (mouseXRelativeToTile > intersection.getX()) {
					mouseTileX++;
				}
			}

			if (mouseTileX >= 0 && mouseTileY >= 0 && mouseTileX < currentMap.getWidth() && mouseTileY < currentMap.getHeight()) {
				currentMap.getTileManager().getTiles().get(mouseTileY).get(mouseTileX).setHighlighted(true);
				highlightedTileY = mouseTileY;
				highlightedTileX = mouseTileX;
			}

			mouseTileLocation.setX(highlightedTileX);
            mouseTileLocation.setY(highlightedTileY);
			// System.out.println(highlightedTileX + " " + highlightedTileY + " " + " are the highlighted tiles.");
            /*System.out.println("The walkable value is "
                    + currentMap.getTileManager().getTiles().get(highlightedTileY).get(highlightedTileX).isWalkable());*/
			/*if (currentMap.getStructureManager().getStructureAtTile(mouseTileLocation) != null) {
				System.out.println("YEAH");
			} else {
				System.out.println("NO");
			}*/
		}
	}

	private void selectUnit(Unit unit) {
        selectedUnit = unit;
        optionWindowText.setText(unit.getType().name());
    }

	private void selectStructure(Structure structure) {
		optionWindowText.setText(structure.getType().name());
	}

	private void selectNothing() {
        optionWindowText.setText("");
        selectedUnit = null;
    }

	public MenuLabel getFoodLabel() {
		return foodLabel;
	}

	public void setFoodLabel(MenuLabel foodLabel) {
		this.foodLabel = foodLabel;
	}

	public MenuLabel getWoodLabel() {
		return woodLabel;
	}

	public void setWoodLabel(MenuLabel woodLabel) {
		this.woodLabel = woodLabel;
	}

	public MenuLabel getStoneLabel() {
		return stoneLabel;
	}

	public void setStoneLabel(MenuLabel stoneLabel) {
		this.stoneLabel = stoneLabel;
	}

	public MenuLabel getGoldLabel() {
		return goldLabel;
	}

	public void setGoldLabel(MenuLabel goldLabel) {
		this.goldLabel = goldLabel;
	}

	public MenuLabel getOptionWindowText() {
		return optionWindowText;
	}

	public void setOptionWindowText(MenuLabel optionWindowText) {
		this.optionWindowText = optionWindowText;
	}

	public MenuButton getOptionWindowButton() {
		return optionWindowButton;
	}

	public void setOptionWindowButton(MenuButton optionWindowButton) {
		this.optionWindowButton = optionWindowButton;
	}

	public ImageSprite getOptionWindowImage() {
		return optionWindowImage;
	}

	public void setOptionWindowImage(ImageSprite optionWindowImage) {
		this.optionWindowImage = optionWindowImage;
	}

	public void incrementResource(GameResource.Type type, int amount) {
        switch (type) {
            case FOOD:
                foodAmount += amount;
                foodLabel.setText("Food: " + foodAmount);
                System.out.println("?!?!?");
                break;
            case WOOD:
                woodAmount += amount;
                woodLabel.setText("Wood: " + woodAmount);
                break;
            case STONE:
                stoneAmount += amount;
                stoneLabel.setText("Stone: " + stoneAmount);
                break;
            case GOLD:
                goldAmount += amount;
                goldLabel.setText("Gold: " + goldAmount);
                break;
        }
    }

    public int getFoodAmount() {
        return foodAmount;
    }

    public void setFoodAmount(int foodAmount) {
        this.foodAmount = foodAmount;
    }

    public int getWoodAmount() {
        return woodAmount;
    }

    public void setWoodAmount(int woodAmount) {
        this.woodAmount = woodAmount;
    }

    public int getStoneAmount() {
        return stoneAmount;
    }

    public void setStoneAmount(int stoneAmount) {
        this.stoneAmount = stoneAmount;
    }

    public int getGoldAmount() {
        return goldAmount;
    }

    public void setGoldAmount(int goldAmount) {
        this.goldAmount = goldAmount;
    }
}