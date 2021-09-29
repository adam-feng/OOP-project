package unsw.loopmania;


import javafx.beans.property.SimpleIntegerProperty;

/**
 * Anduril - a Weapon item
 */
public class Anduril extends WeaponItem {
    /**
     * Anduril constructor
     * @param x
     * @param y
     */
    public Anduril(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, 10, "Anduril", 0, 0);
    }

    /**
     * Check if enemy is a Boss type
     * @param enemy
     * @return true
     */
    public boolean checkBoss(BasicEnemy enemy) {
        if (enemy.getType().equals("Doggie") || enemy.getType().equals("ElanMuske")) {
            return true;
        }   
        return false;
    }
}
