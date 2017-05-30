package RC.Age_of_Aces.Model_Classes.Maps;
/*
 * Name: Robert Ciborowski
 * Date: 2017-05-20
 * Assignment:
 * Description:
*/

import RC.Age_of_Aces.Model_Classes.Math.PathSegment;
import RC.Age_of_Aces.Model_Classes.Math.Point;
import RC.Age_of_Aces.Model_Classes.Sprites.ImageSprite;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class UnitPathFinder {
    private static ArrayList<Point> path = new ArrayList<>();
    private static ArrayList<Point> bannedTiles = new ArrayList<>();

    public static final double RIGHT_DIRECTION = 0, UP_DIRECTION = Math.PI / 2.0,
            LEFT_DIRECTION = Math.PI, DOWN_DIRECTION = Math.PI * 3 / 2,
            UP_RIGHT_DIRECTION = Math.PI / 4, UP_LEFT_DIRECTION = Math.PI * 3 / 4,
            DOWN_LEFT_DIRECTION = Math.PI * 5 / 4, DOWN_RIGHT_DIRECTION = Math.PI * 7 / 4;
    private enum Direction {RIGHT, UP, LEFT, DOWN};

    public static boolean generatePath(Unit unit, Map map, Point endPoint) {
        Point startPoint = unit.getLocationOnMap();
        TileManager tileManager = map.getTileManager();

        System.out.println("Creating new path.");
        boolean pathIsFound = false;
        path.clear();
        bannedTiles.clear();
        path.add(startPoint);

        while (!pathIsFound) {
            PathSegment directionToEndPoint = new PathSegment();
            directionToEndPoint.moveToPoint(path.get(path.size() - 1), endPoint);
            directionToEndPoint.setDirection(Math.PI * 2 - betweenRadianRange(directionToEndPoint.getDirection()));
            double list[] = sortDirections(directionToEndPoint);
            ArrayList<Point> possibleTilesToMoveTo = new ArrayList<>();

            for (int i = 0; i < 4; i++) {
                Point tileLocation;
                if (list[i] == RIGHT_DIRECTION) {
                    tileLocation = getTileBasedOnMovement(path.get(path.size() - 1), Direction.RIGHT);
                } else if (list[i] == UP_DIRECTION) {
                    tileLocation = getTileBasedOnMovement(path.get(path.size() - 1), Direction.UP);
                } else if (list[i] == LEFT_DIRECTION) {
                    tileLocation = getTileBasedOnMovement(path.get(path.size() - 1), Direction.LEFT);
                } else {
                    tileLocation = getTileBasedOnMovement(path.get(path.size() - 1), Direction.DOWN);
                }

                Tile tile = tileManager.getTiles().get((int) tileLocation.getY()).get((int) tileLocation.getX());

                if (tileLocation.getX() == endPoint.getX() && tileLocation.getY() == endPoint.getY()) {
                    // This is the end tile!
                    possibleTilesToMoveTo.add(0, tileLocation);
                    pathIsFound = true;
                } else if (isPartOfPointList(tileLocation, bannedTiles)) {
                    // Disregard the tile.
                } else if (!tile.isWalkable()) {
                    // Ban the tile!
                    bannedTiles.add(tileLocation);
                } else if (tile.getResource() != null) {
                    // Ban the tile!
                    bannedTiles.add(tileLocation);
                } else if (map.getStructureManager().getStructureAtTile(tileLocation) != null) {
                    // Ban the tile!
                    bannedTiles.add(tileLocation);
                } else if (path.size() > 1 && pointsAreTheSame(tileLocation, path.get(path.size() - 2))) {
                    // Disregard the tile, it's the previous tile!
                } else {
                    // This tile is allowed to be moved to (even if it was already moved to).
                    possibleTilesToMoveTo.add(tileLocation);
                }
            }
            /*if (isPartOfPointList(new Point(29.0, 13.0), possibleTilesToMoveTo)) {
                System.out.println("{");
                System.out.println("Current position:");
                printPoint(path.get(path.size() - 1));
                System.out.println("Direction: " + directionToEndPoint.getDirection());
                System.out.println("Points:");
                printPoints(possibleTilesToMoveTo);
                System.out.println("List:");
                for (int j = 0; j < list.length; j++) {
                    System.out.println(list[j]);
                }
                System.out.println("}");
            }*/
            for (int whichPosibility = 0; whichPosibility < possibleTilesToMoveTo.size(); whichPosibility++) {
                if (isPartOfPointList(possibleTilesToMoveTo.get(whichPosibility), path)) {
                    bannedTiles.add(path.get(path.size() - 1));
                    path.remove(path.size() - 1);
                    break;
                } else {
                    path.add(possibleTilesToMoveTo.get(whichPosibility));

                    break;
                }
            }
            if (possibleTilesToMoveTo.size() == 0) {
                bannedTiles.add(path.get(path.size() - 1));
                path.remove(path.size() - 1);
            }
        }
        return pathIsFound;
    }
    
    private static double[] sortDirections(PathSegment pathSegment) {
        double direction = pathSegment.getDirection();
        direction = betweenRadianRange(direction);
        double list[] = {RIGHT_DIRECTION, UP_DIRECTION, LEFT_DIRECTION, DOWN_DIRECTION};
        // double list[] = {UP_RIGHT_DIRECTION, UP_LEFT_DIRECTION, DOWN_LEFT_DIRECTION, DOWN_RIGHT_DIRECTION};
        for (int repeats = 0; repeats < 4; repeats++) {
            for (int i = 0; i < 3; i++) {
                double possibilityOne, possibilityTwo;

                // Try "betweenRadianRange(" for list[i] and direction
                if (betweenRadianRange(list[i] - direction) < betweenRadianRange(direction - list[i])) {
                    possibilityOne = betweenRadianRange(list[i] - direction);
                } else {
                    possibilityOne = betweenRadianRange(direction - list[i]);
                }

                if (betweenRadianRange(list[i + 1] - direction) < betweenRadianRange(direction - list[i + 1])) {
                    possibilityTwo = betweenRadianRange(list[i + 1] - direction);
                } else {
                    possibilityTwo = betweenRadianRange(direction - list[i + 1]);
                }

                if (possibilityOne > possibilityTwo) {
                    double prevValue = list[i];
                    list[i] = list[i + 1];
                    list[i + 1] = prevValue;
                }
            }
        }
        return list;
    }

    private static Point getTileBasedOnMovement(Point currentLocation, Direction direction) {
        Point newLocation = new Point();
        switch (direction) {
            case RIGHT:
                if (currentLocation.getX() % 2 == 0) {
                    newLocation.setLocation(currentLocation.getX() + 1, currentLocation.getY() - 1);
                } else {
                    newLocation.setLocation(currentLocation.getX() + 1, currentLocation.getY());
                }
                break;
            case UP:
                if (currentLocation.getX() % 2 == 0) {
                    newLocation.setLocation(currentLocation.getX() - 1, currentLocation.getY() - 1);
                } else {
                    newLocation.setLocation(currentLocation.getX() - 1, currentLocation.getY());
                }
                break;
            case LEFT:
                if (currentLocation.getX() % 2 == 1) {
                    newLocation.setLocation(currentLocation.getX() - 1, currentLocation.getY() + 1);
                } else {
                    newLocation.setLocation(currentLocation.getX() - 1, currentLocation.getY());
                }
                break;
            case DOWN:
                if (currentLocation.getX() % 2 == 1) {
                    newLocation.setLocation(currentLocation.getX() + 1, currentLocation.getY() + 1);
                } else {
                    newLocation.setLocation(currentLocation.getX() + 1, currentLocation.getY());
                }
                break;
        }
        return newLocation;
    }

    public static double getDirectionBasedOnTileMovement(Point tileA, Point tileB) {
        double returnValue = UP_RIGHT_DIRECTION;
        if (tileA.getX() % 2 == 0) {
            if (tileB.getX() == tileA.getX() + 1 && tileB.getY() == tileA.getY() - 1) {
                returnValue =  DOWN_RIGHT_DIRECTION;
            }
            if (tileB.getX() == tileA.getX() - 1 && tileB.getY() == tileA.getY() - 1) {
                returnValue =  DOWN_LEFT_DIRECTION;
            }
            if (tileB.getX() == tileA.getX() - 1 && tileB.getY() == tileA.getY() ) {
                returnValue =  UP_LEFT_DIRECTION;
            }
            if (tileB.getX() == tileA.getX() + 1 && tileB.getY() == tileA.getY()) {
                returnValue =  UP_RIGHT_DIRECTION;
            }
        } else {
            if (tileB.getX() == tileA.getX() + 1 && tileB.getY() == tileA.getY()) {
                returnValue =  DOWN_RIGHT_DIRECTION;
            }
            if (tileB.getX() == tileA.getX() - 1 && tileB.getY() == tileA.getY()) {
                returnValue =  DOWN_LEFT_DIRECTION;
            }
            if (tileB.getX() == tileA.getX() - 1 && tileB.getY() == tileA.getY() + 1) {
                returnValue =  UP_LEFT_DIRECTION;
            }
            if (tileB.getX() == tileA.getX() + 1 && tileB.getY() == tileA.getY() + 1) {
                returnValue =  UP_RIGHT_DIRECTION;
            }
        }
        return returnValue;
    }

    private static double betweenRadianRange(double number) {
        while (number >= Math.PI * 2 - 0.00001) {
            number -= Math.PI * 2;
        }
        while (number < 0) {
            number += Math.PI * 2;
        }
        return number;
    }

    private static boolean isPartOfPointList(Point point, ArrayList<Point> list) {
        for (int i = 0; i < list.size(); i++) {
            if (pointsAreTheSame(point, list.get(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean pointsAreTheSame(Point a, Point b) {
        return a.getX() == b.getX() && a.getY() == b.getY();
    }

    public static void printPoint(Point point) {
        System.out.println(point.getX() + ", " + point.getY());
    }

    public static void printPoints(ArrayList<Point> points) {
        for (Point point : points) {
            printPoint(point);
        }
    }

    public static ArrayList<Point> getPath() {
        return path;
    }
}
