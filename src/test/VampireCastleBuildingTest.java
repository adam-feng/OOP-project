package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.EnemyFactory;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.VampireCastleBuilding;

/**
 * Test vampire castle
 */
public class VampireCastleBuildingTest {
    /**
     * Test add vampire castle
     */
    @Test
    public void testAddVampireCastle(){
        
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);

        VampireCastleBuilding vampireCastle = new VampireCastleBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(1));

        d.addBuildingEntity(vampireCastle);
    
        assertEquals(d.getBuildingEntities().get(0), vampireCastle); 
        assertEquals(vampireCastle.getX(), 0);
        assertEquals(vampireCastle.getY(), 1);    
    }

    /**
     * Test spawn vampire
     */
    @Test
    public void testSpawnVampire() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));
        orderedPath.add(new Pair<>(0, 3));


        LoopManiaWorld d  = new LoopManiaWorld(4, 4, orderedPath);

        VampireCastleBuilding vampireCastle = new VampireCastleBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(1));

        d.addBuildingEntity(vampireCastle);
        d.addVampireCastle(vampireCastle);
        
        for (int i = 0; i < 5; i++) {
            d.updateCyclesCompleted();
        }
        EnemyFactory factory = new EnemyFactory(d);
        factory.spawnVampires();
    
        assert(d.getEnemies().size() > 0);
    }
}