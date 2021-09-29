package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;

import unsw.loopmania.PathPosition;
import unsw.loopmania.AlliedSoldier;
import unsw.loopmania.BarracksBuilding;
import unsw.loopmania.NormalCharacter;

/**
 * Barracks Building tests
 */
public class BarracksBuildingTest {
    
    /**
     * Create a new Barracks Building
     */
    @Test
    public void createBarracksTest(){
        
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);

        BarracksBuilding barracks = new BarracksBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(1));

        d.addBuildingEntity(barracks);
    
        assertEquals(d.getBuildingEntities().get(0), barracks); 
        assertEquals(barracks.getX(), 0);
        assertEquals(barracks.getY(), 1);   
    }

    /**
     * Tests character gains a soldier when passing
     */
    @Test
    public void testGainSoldier() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        BarracksBuilding barracks = new BarracksBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(1));


        d.addBuildingEntity(barracks);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        assertEquals(barracks.isCharacterHere(character), false);
        assertEquals(d.canSpawnAlliedSoldier(barracks), false);

        character.moveDownPath();
        assertEquals(barracks.isCharacterHere(character), true);
        assertEquals(d.canSpawnAlliedSoldier(barracks), true);
        AlliedSoldier a = barracks.spawnSoldier(d, new SimpleIntegerProperty(0), new SimpleIntegerProperty(2));
        assertEquals(d.getAlliedSoldiers().size(), 1);

    }
}