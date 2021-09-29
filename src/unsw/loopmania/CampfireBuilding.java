package unsw.loopmania;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Campfire - a building type
 */
public class CampfireBuilding extends Building {
    
    private int areaOfEffect;

    /**
     * Campfire building constructor
     * @param x
     * @param y
     */
    public CampfireBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, "CampfireBuilding");
        this.areaOfEffect = 5;
    }

    /**
     * Gets radius where character has double damage
     * @return
     */
    public int getAreaOfEffect() {
        return this.areaOfEffect;
    }

    /**
     * Returns true if character is in campfire radius, false if not
     * @param character
     * @return boolean
     */
    public boolean isCharacterInAOE(Character character) {
        if (Math.pow((character.getX()-this.getX()), 2) +  Math.pow((character.getY()-this.getY()), 2) <= Math.pow(areaOfEffect, 2)){
            return true;
        }
        return false;
    }

    /**
     * Retruns true if enemy is in attack radius
     * @param enemy
     * @return boolean
     */
    public boolean isEnemyInAOE(BasicEnemy enemy) {
        if (Math.pow((enemy.getX()-this.getX()), 2) +  Math.pow((enemy.getY()-this.getY()), 2) <= Math.pow(areaOfEffect, 2)){
            return true;
        }
        return false;
    }

    /**
     * returns true if vampire is in attack radius
     * @param vamp
     * @return
     */
    public boolean isInAOE(Vampire vamp) {
        if (Math.pow((vamp.getX()-this.getX()), 2) +  Math.pow((vamp.getY()-this.getY()), 2) <= Math.pow(areaOfEffect, 2)){
            return true;
        }
        return false;
    }
}


