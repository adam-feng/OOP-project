package unsw.loopmania;

import java.util.List;
import java.util.Random;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Doggie coin - an Item type
 */
public class DoggieCoin extends Item {
    /**
     * DoggieCoin constructor
     * @param x
     * @param y
     */
    public DoggieCoin(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, "DoggieCoin", 0, 0);
    }

    /**
     * Set price of DoggieCoin
     * @param world
     */
    public void setPrice(LoopManiaWorld world) {
        Random random = new Random();
        int price = random.nextInt(100000);
        if (isElanMuskeAlive(world)) {
            price += 70000;
        }
        super.setSellPrice(price);
    }

    /**
     * Check if Elan Muske is alive
     * @param world
     * @return
     */
    public boolean isElanMuskeAlive(LoopManiaWorld world) {
        if (world.getNumCyclesCompleted() == 40) {
            List<BasicEnemy> enemies = world.getEnemies();
            if (enemies != null) {
                for (BasicEnemy e : enemies) {
                    if (e.getType().equals("ElanMuske")) {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
    
}
