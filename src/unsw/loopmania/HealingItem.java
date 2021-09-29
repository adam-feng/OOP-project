package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Healing type of item
 */
public class HealingItem extends Item {
    /**
     * Healing item constructor
     * @param x
     * @param y
     * @param type
     * @param sellPrice
     * @param buyPrice
     */
    public HealingItem(SimpleIntegerProperty x, SimpleIntegerProperty y, String type, int sellPrice, int buyPrice) {
        super(x, y, type, sellPrice, buyPrice);
    }

}