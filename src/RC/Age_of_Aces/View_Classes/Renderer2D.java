 /*
 * Name: Robert Ciborowski
 * Date: 23-01-2017
 * Assignment: Triangle-Bouncer
 * Description: A view class that renders 2D elements.
*/

package RC.Age_of_Aces.View_Classes;

 import RC.Age_of_Aces.Model_Classes.Camera;
 import RC.Age_of_Aces.Model_Classes.GameplayManager;
 import RC.Age_of_Aces.Model_Classes.Maps.*;
 import RC.Age_of_Aces.Model_Classes.Math.*;
 import RC.Age_of_Aces.Model_Classes.Menus.Backdrop;
 import RC.Age_of_Aces.Model_Classes.Menus.MenuButton;
 import RC.Age_of_Aces.Model_Classes.Menus.MenuLabel;
 import RC.Age_of_Aces.Model_Classes.Sprites.*;
 import RC.Age_of_Aces.View_Classes.Menus.MenuManager;
 import RC.Age_of_Aces.View_Classes.Menus.Screen;

 import java.awt.*;
 import java.awt.image.BufferedImage;
 import java.awt.image.RasterFormatException;

 /**
  * The <code>Renderer2D</code> class is used to render 2D elements,
  * such as <code>Sprites</code>.
  *
  * @date 23-01-2017
  * @author Robert Ciborowski
  */
 public class Renderer2D {
     /**
      * This is the default constructor.
      */
     public void Renderer2D() {

     }

     /**
      * A method that renders any sprite.
      * @param g An object of Java's <code>Graphics</code> class.
      * @param sprite The sprite to render.
      */
     public void renderSprite(Graphics g, Sprite sprite) {
         if (sprite instanceof ImageSprite) {
             renderImageSprite(g, (ImageSprite) sprite, new Rect(0, 0, 0, 0));
         } else if (sprite instanceof BallSprite) {
             renderBallSprite(g, (BallSprite) sprite);
         } else if (sprite instanceof LineSprite) {
             renderLineSprite(g, (LineSprite) sprite);
         } else if (sprite instanceof TriangleSprite) {
             renderLineSprite(g, (LineSprite) ((TriangleSprite) sprite).getSides()[0]);
             renderLineSprite(g, (LineSprite) ((TriangleSprite) sprite).getSides()[1]);
             renderLineSprite(g, (LineSprite) ((TriangleSprite) sprite).getSides()[2]);
         }
     }

     /**
      * A method that renders an <code>ImageSprite</code>.
      * @param g An object of Java's <code>Graphics</code> class.
      * @param sprite The sprite to render.
      */
     public void renderImageSprite(Graphics g, ImageSprite sprite, Rect displacement) {
         Rect boundingBox = sprite.getBoundingBox();
         BufferedImage image = sprite.getImage();
         if (image != null) {
             g.drawImage(image,
                     // This specifies the cropping for the image.
                     (int) (boundingBox.getXPosition() + displacement.getXPosition()), (int) (boundingBox.getYPosition() + displacement.getYPosition()),
                     (int) (boundingBox.getXPosition() + displacement.getXPosition() + boundingBox.getProperty(Rect.RectProperties.WIDTH)), (int) (boundingBox.getYPosition() + displacement.getYPosition() + boundingBox.getProperty(Rect.RectProperties.HEIGHT)),
                     // This specifies the output box (bounding box) for the image.
                     0, 0,
                     image.getWidth(), image.getHeight(), null);
         }
     }

     /**
      * A method that renders a <code>BallSprite</code>.
      * @param g An object of Java's <code>Graphics</code> class.
      * @param sprite The sprite to render.
      */
     public void renderBallSprite(Graphics g, BallSprite sprite) {
         Rect boundingBox = sprite.getBoundingBox();
         g.setColor(sprite.getColour());
         g.drawOval(
                 (int) boundingBox.getProperty(Rect.RectProperties.X_POSITION), (int) boundingBox.getProperty(Rect.RectProperties.Y_POSITION),
                 (int) (boundingBox.getProperty(Rect.RectProperties.WIDTH)), (int) (boundingBox.getProperty(Rect.RectProperties.HEIGHT))
                 );
     }

     /**
      * A method that renders a <code>LineSprite</code>.
      * @param g An object of Java's <code>Graphics</code> class.
      * @param sprite The sprite to render.
      */
     public void renderLineSprite(Graphics g, LineSprite sprite) {
         Rect boundingBox = sprite.getBoundingBox();
         g.setColor(sprite.getColour());
         g.drawLine(
                 // This specifies the cropping for the image.
                 (int) boundingBox.getProperty(Rect.RectProperties.X_POSITION), (int) boundingBox.getProperty(Rect.RectProperties.Y_POSITION),
                 (int) ( boundingBox.getProperty(Rect.RectProperties.X_POSITION) + boundingBox.getProperty(Rect.RectProperties.WIDTH)), (int) (boundingBox.getProperty(Rect.RectProperties.Y_POSITION) + boundingBox.getProperty(Rect.RectProperties.HEIGHT))
                 );
     }

     /**
      * A method that renders an <code>Image</code>.
      * @param g An object of Java's <code>Graphics</code> class.
      * @param image The image to render.
      * @param cropper The cropping of the image.
      * @param boundingBox The rectangle that the image is rendered into.
      */
     public void renderImage(Graphics g, BufferedImage image, Rect cropper, Rect boundingBox) {
         g.drawImage(image,
                 // This specifies the cropping for the image.
                 (int) cropper.getProperty(Rect.RectProperties.X_POSITION), (int) cropper.getProperty(Rect.RectProperties.Y_POSITION),
                 (int) ( cropper.getProperty(Rect.RectProperties.X_POSITION) + cropper.getProperty(Rect.RectProperties.WIDTH)), (int) (boundingBox.getProperty(Rect.RectProperties.Y_POSITION) + boundingBox.getProperty(Rect.RectProperties.HEIGHT)),
                 // This specifies the output box (bounding box) for the image.
                 0, 0,
                 image.getWidth(), image.getHeight(), null);
     }

     // This method renders a backdrop.
     public void renderBackdrop(Graphics g, Backdrop backdrop) {
         BufferedImage image = backdrop.getImage();
         if (image != null) {
             Rect cropping = backdrop.getCropping();
             Rect boundingBox = backdrop.getBoundingBox();
             g.drawImage(image,
                     // This specifies the cropping for the image.
                     (int) cropping.getProperty(Rect.RectProperties.X_POSITION), (int) cropping.getProperty(Rect.RectProperties.Y_POSITION),
                     (int) (cropping.getProperty(Rect.RectProperties.X_POSITION) + cropping.getProperty(Rect.RectProperties.WIDTH)), (int) (boundingBox.getProperty(Rect.RectProperties.Y_POSITION) + boundingBox.getProperty(Rect.RectProperties.HEIGHT)),
                     // This specifies the output box (bounding box) for the image.
                     (int) boundingBox.getProperty(Rect.RectProperties.X_POSITION), (int) boundingBox.getProperty(Rect.RectProperties.Y_POSITION),
                     (int) boundingBox.getProperty(Rect.RectProperties.WIDTH), (int) boundingBox.getProperty(Rect.RectProperties.HEIGHT), null);
         }
     }

     // This method renders a label/text.
     public void renderLabel(Graphics g, MenuLabel label) {
         Rect offset = label.getOffset();
         String data = label.getText();
         g.setColor(label.getColour());
         g.setFont(label.getFont());
         g.drawString(data,
                 (int) (offset.getProperty(Rect.RectProperties.X_POSITION) + offset.getProperty(Rect.RectProperties.WIDTH)),
                 (int) (offset.getProperty(Rect.RectProperties.Y_POSITION) + offset.getProperty(Rect.RectProperties.HEIGHT)));
     }

     // Tbis method renders a button (and accounts for button hovering).
     public void renderButton(Graphics g, MenuButton button) {
         if (button.isHovered()) {
             renderImageSprite(g, button.getButtonImageHover(), new Rect(0, 0, 0, 0));
         } else {
             renderImageSprite(g, button.getButtonImage(), new Rect(0, 0, 0, 0));
         }
         renderButtonText(g, button);
     }

     // This private method is used to render the text of buttons.
     private void renderButtonText(Graphics g, MenuButton button) {
         MenuLabel label = button.getButtonText();
         Rect buttonDimensions = button.getDimensions();
         String data = label.getText();
         FontMetrics metrics = g.getFontMetrics();
         g.setFont(label.getFont());
         int widthOfText = metrics.stringWidth(data), heightOfText = label.getFont().getSize();
         Rect textOffset = new Rect(
                 buttonDimensions.getProperty(Rect.RectProperties.X_POSITION) + ((buttonDimensions.getProperty(Rect.RectProperties.WIDTH) - widthOfText) / 2),
                 buttonDimensions.getProperty(Rect.RectProperties.Y_POSITION) + ((buttonDimensions.getProperty(Rect.RectProperties.HEIGHT) / 2 + (heightOfText / 2))),
                 0, 0);
         g.setColor(label.getColour());
         g.drawString(data,
                 (int) (textOffset.getProperty(Rect.RectProperties.X_POSITION)),
                 (int) (textOffset.getProperty(Rect.RectProperties.Y_POSITION)));
     }

     public void renderMenuManager(MenuManager menuManager, Graphics g) {
         Screen screen = menuManager.getScreens().get(menuManager.getCurrentScreen());
         if (screen.getBackdrop() != null) {
             renderBackdrop(g, screen.getBackdrop());
         }
         for (Sprite sprite : screen.getSprites()) {
             renderSprite(g, sprite);
         }
         for (MenuButton button : screen.getButtons()) {
             renderButton(g, button);
         }
         for (MenuLabel label : screen.getLabels()) {
             renderLabel(g, label);
         }
     }

     public void renderGameplay(GameplayManager gameplayManager, Graphics g) {
         Map map = gameplayManager.getCurrentMap();
         Camera camera = gameplayManager.getCamera();
         UnitManager unitManager = map.getUnitManager();
         StructureManager structureManager = map.getStructureManager();
         int tileWidth = map.getTileManager().getTileImages().get(0).getWidth(),
                 tileHeight = map.getTileManager().getTileImages().get(0).getHeight();
         for (int j = 0; j < map.getHeight(); j++) {
             for (int i = 0; i < map.getWidth(); i++) {
                 renderImageSprite(g, map.getTileManager().getTiles().get(j).get(i).getImage(), camera.getView());
             }
         }
         for (int j = 0; j < map.getHeight(); j++) {
             for (int i = 0; i < map.getWidth(); i++) {
                 Structure structure = structureManager.getRendererdStructureAtTile(new RC.Age_of_Aces.Model_Classes.Math.Point(i, j));
                 if (structure != null) {
                     try {
                         double yPixelOffset = structure.getImage().getBoundingBox().getHeight() - structure.getImage().getBoundingBox().getHeight();
                         BufferedImage image = structure.getImage().getImage().getSubimage(
                                 (int) (((double) i - (structure.getDimensionsOnMap().getXPosition())) * tileWidth), (int) (((double) j - (structure.getTopLeftTile().getY())) * tileHeight + yPixelOffset),
                                 tileWidth, tileHeight);
                         ImageSprite sprite = new ImageSprite((i * tileWidth) / 2 + (((double) i - (structure.getDimensionsOnMap().getXPosition())) * tileWidth / 2), j * tileHeight, tileWidth, tileHeight);
                         sprite.setImage(image);
                         renderImageSprite(g, sprite, camera.getView());
                     } catch (RasterFormatException e) {

                     }
                 }
             }
             for (int i = 0; i < map.getWidth(); i++) {
                 if (map.getTileManager().getTiles().get(j).get(i).getResource() != null) {
                     renderImageSprite(g, map.getTileManager().getTiles().get(j).get(i).getResource().getImage(), camera.getView());
                 }
             }
             for (int i = 0; i < map.getWidth(); i++) {
                 for (Unit unit : unitManager.getUnits()) {
                     if (UnitPathFinder.pointsAreTheSame(new RC.Age_of_Aces.Model_Classes.Math.Point(i, j), unit.getLocationOnMap())) {
                         renderImageSprite(g, unit.getImage(), camera.getView());
                     }
                 }
             }
         }



         // renderMap(gameplayManager.getCurrentMap(), gameplayManager.getCamera(), g);
         // renderUnits(gameplayManager.getCurrentMap().getUnitManager(), gameplayManager.getCamera(), g);
         // renderStructures(gameplayManager.getCurrentMap().getStructureManager(), gameplayManager.getCamera(), g);
         // renderResources(gameplayManager.getCurrentMap(), gameplayManager.getCamera(), g);
     }

     public void renderMap(Map map, Camera camera, Graphics g) {
         for (int i = 0; i < map.getWidth(); i++) {
             for (int j = 0; j < map.getHeight(); j++) {
                 //if (!map.getTileManager().getTiles().get(j).get(i).isHighlighted()) {
                 renderImageSprite(g, map.getTileManager().getTiles().get(j).get(i).getImage(), camera.getView());
                 // }
             }
         }
     }

     public void renderUnits(UnitManager unitManager, Camera camera, Graphics g) {
         for (Unit unit : unitManager.getUnits()) {
             renderImageSprite(g, unit.getImage(), camera.getView());
         }
     }

     public void renderStructures(StructureManager structureManager, Camera camera, Graphics g) {
         for (Structure structure : structureManager.getStructures()) {
             renderImageSprite(g, structure.getImage(), camera.getView());
         }
     }

     public void renderResources(Map map, Camera camera, Graphics g) {
         for (int i = 0; i < map.getWidth(); i++) {
             for (int j = 0; j < map.getHeight(); j++) {
                 if (map.getTileManager().getTiles().get(j).get(i).getResource() != null) {
                     renderImageSprite(g, map.getTileManager().getTiles().get(j).get(i).getResource().getImage(), camera.getView());
                 }
             }
         }
     }
 }
