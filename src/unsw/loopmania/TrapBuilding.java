package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Trap building    
 */
public class TrapBuilding extends Building{
    
    private double springDamage;

    /**
     * Trap building constructor
     * @param x
     * @param y
     */
    public TrapBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, "TrapBuilding");
        this.springDamage = 15; 
    }

    /**
     * Get spring damage
     * @return
     */
    public double getSpringDamage() {
        return this.springDamage;
    }
}
