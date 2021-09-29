package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.EnemyFactory;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.ZombiePitBuilding;

/**
 * Zombie pit building test
 */
public class ZombiePitBuildingTest {
    /**
     * Test add zombie pit
     */
    @Test
    public void testAddZombiePit(){
        
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);

        ZombiePitBuilding zombiePit = new ZombiePitBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(1));

        d.addBuildingEntity(zombiePit);
    
        assertEquals(d.getBuildingEntities().get(0), zombiePit); 
        assertEquals(zombiePit.getX(), 0);
        assertEquals(zombiePit.getY(), 1);   
    }

    /**
     * Test spawn zombies
     */
    @Test
    public void testSpawnZombies() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));
        orderedPath.add(new Pair<>(0, 3));


        LoopManiaWorld d  = new LoopManiaWorld(4, 4, orderedPath);

        ZombiePitBuilding zombiePit = new ZombiePitBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(1));

        d.addBuildingEntity(zombiePit);
        d.addZombiePit(zombiePit);
        
        for (int i = 0; i < 5; i++) {
            d.updateCyclesCompleted();
        }
        EnemyFactory factory = new EnemyFactory(d);
        factory.spawnZombies();
    
        assert(d.getEnemies().size() > 0);
    }
}