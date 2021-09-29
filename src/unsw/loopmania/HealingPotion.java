package unsw.loopmania;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Healing potion item
 */
public class HealingPotion extends HealingItem {
    /**
     * Healing potion constructor
     * @param x
     * @param y
     */
    public HealingPotion(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, "HealingPotion", 160, 800);
    }

    /**
     * Heal character to full health, unless swift foot character class
     * @param character
     */
    public void heal(Character character) {
        character.setHealth(character.getBaseHealth());
    }
}
