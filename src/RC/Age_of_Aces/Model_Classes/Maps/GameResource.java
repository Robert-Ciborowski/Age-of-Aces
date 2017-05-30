package RC.Age_of_Aces.Model_Classes.Maps;

import RC.Age_of_Aces.Model_Classes.Math.Point;
import RC.Age_of_Aces.Model_Classes.Sprites.ImageSprite;

import static RC.Age_of_Aces.Model_Classes.Maps.GameResource.Type.*;

/**
 * Created by 323999433 on 25/05/2017.
 */
public class GameResource {
    public enum Type {FOOD, WOOD, STONE, GOLD};
    private int amount;
    private Type type;
    private ImageSprite image;

    public GameResource() {
        amount = 2;
        type = FOOD;
    }

    public GameResource(int amount, Type type, Point location) {
        this.amount = amount;
        this.type = type;
        image = new ImageSprite();
    }

    public void setBasedOnID(int ID) {
        switch (ID) {
            case 1:
                type = FOOD;
                break;
            case 2:
                type = WOOD;
                break;
            case 3:
                type = STONE;
                break;
            case 4:
                type = GOLD;
                break;
        }
    }

    public int deplete(int depletionAmount) {
        amount -= depletionAmount;
        if (amount < 0) {
            depletionAmount -= (0 - amount);
            amount = 0;
        }
        System.out.println(amount);
        return depletionAmount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public ImageSprite getImage() {
        return image;
    }

    public void setImage(ImageSprite image) {
        this.image = image;
    }
}
