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
import unsw.loopmania.NormalCharacter;
import unsw.loopmania.CampfireBuilding;

/**
 * Campfire building tests
 */
public class CampfireBuildingTest {
    /**
     * Test add campfire to map
     */
    @Test
    public void addCampfireTest(){
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);

        CampfireBuilding campfireBuilding = new CampfireBuilding(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));

        d.addBuildingEntity(campfireBuilding);
    
        assertEquals(d.getBuildingEntities().get(0), campfireBuilding); 
        assertEquals(campfireBuilding.getX(), 1);
        assertEquals(campfireBuilding.getY(), 1);   
        assertEquals(campfireBuilding.getAreaOfEffect(), 5);
    }

    /**
     * Test character does double damage in radius
     */
    @Test
    public void testCampfireDamage(){
        
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);

        CampfireBuilding campfireBuilding = new CampfireBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        d.addBuildingEntity(campfireBuilding);
        PathPosition position = new PathPosition(1, orderedPath);

        Slug slug = new Slug(position);

        PathPosition charPosition = new PathPosition(2, orderedPath);
        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        assertEquals(character.dealDamage(d, slug), 20);
    }
    
    /**
     * Test campfire radius
     */
    @Test
    public void testRadius(){
        
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));
        orderedPath.add(new Pair<>(0, 3));
        orderedPath.add(new Pair<>(0, 4));
        orderedPath.add(new Pair<>(0, 5));
        orderedPath.add(new Pair<>(0, 6));

        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);

        CampfireBuilding campfireBuilding = new CampfireBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(6));

        d.addBuildingEntity(campfireBuilding);

        PathPosition position = new PathPosition(0, orderedPath);
        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        assertEquals(campfireBuilding.isCharacterInAOE(character), false);
        
        // move character into radius
        character.moveDownPath();
        character.moveDownPath();
        // character in radius now
        assertEquals(campfireBuilding.isCharacterInAOE(character), true);

    }
}