package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Slug;
import unsw.loopmania.TrapBuilding;

/**
 * Trap building tests
 */
public class TrapBuildingTest {
    /**
     * Test add trap building to map
     */
    @Test
    public void testAddTrapBuilding(){
        
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);

        TrapBuilding trap = new TrapBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));

        d.addBuildingEntity(trap);
    
        assertEquals(d.getBuildingEntities().get(0), trap); 
        assertEquals(trap.getX(), 1);
        assertEquals(trap.getY(), 1);   
        assertEquals(trap.getSpringDamage(), 15);
    }

    /**
     * Spawn walks over trap, should be trapped
     */
    @Test
    public void testTrapSlug(){
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);

        TrapBuilding trap = new TrapBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(1));

        d.addBuildingEntity(trap);

        PathPosition slugPosition = new PathPosition(0, orderedPath);
        Slug slug = new Slug(slugPosition);
        slug.moveDownPath();
        d.handleTraps();

        // Slug should be dead / non-existent
        assertEquals(d.getEnemies().size(), 0);
        System.out.print(d.getBuildingEntities().get(0).getType());
        assertEquals(d.getBuildingEntities().size(), 0);

    }
}