package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Card representation for campfire
 */
public class CampfireCard extends Card {
    /**
     * Campfire card constructor
     * @param x
     * @param y
     */
    public CampfireCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, "CampfireCard", new GrassPlacementStrategy());
    }    
}