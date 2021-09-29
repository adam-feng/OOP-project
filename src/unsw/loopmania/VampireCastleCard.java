package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents a vampire castle card in the backend game world
 */
public class VampireCastleCard extends Card {
    /**
     * Vampire castle card constructor
     * @param x
     * @param y
     */
    public VampireCastleCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, "VampireCastleCard", new AdjacentPlacementStrategy());
    }    
}
