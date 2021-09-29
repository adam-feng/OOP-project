package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.javatuples.Pair;
import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.DoggieCoin;
import unsw.loopmania.ElanMuske;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;

/**
 * Test DoggieCoin
 */
public class DoggieCoinTest {
    /**
     * Test Doggie coin increases value when Elan Muske is alive
     */
    @Test
    public void testElanMuskeValue() {
        // Create a world
        ArrayList<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);

        DoggieCoin doggieCoin = new DoggieCoin(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        
        int firstPrice = doggieCoin.getSellPrice();
        assertEquals(firstPrice, 0);

        PathPosition position = new PathPosition(0, orderedPath);
        ElanMuske elanMuske = new ElanMuske(position);
        d.addEnemy(elanMuske);

        d.addXp(20000);
        for (int i = 0; i < 40; i++) {
            d.updateCyclesCompleted();
        }

        doggieCoin.setPrice(d);
        assert(doggieCoin.getSellPrice() > firstPrice);

    }
}
