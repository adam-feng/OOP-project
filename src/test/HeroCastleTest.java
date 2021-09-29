package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.HeroCastleBuilding;
import unsw.loopmania.LoopManiaWorld;

/**
 * Hero Castle building test
 */
public class HeroCastleTest {
    /**
     * Test Hero's Castle spawns
     */
    @Test
    public void testHeroCastleSpawnPosition(){
        
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);

        HeroCastleBuilding heroCastleBuilding = new HeroCastleBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(1));

        d.addBuildingEntity(heroCastleBuilding);
    
        assertEquals(d.getBuildingEntities().get(0), heroCastleBuilding); 
        assertEquals(heroCastleBuilding.getX(), 0);
        assertEquals(heroCastleBuilding.getY(), 1);   
    }
}