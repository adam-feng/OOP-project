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
import unsw.loopmania.TowerBuilding;
import unsw.loopmania.NormalCharacter;

/**
 * Tower building tests
 */
public class TowerBuildingTest {
    /**
     * Test tower spawn
     */
    @Test
    public void testTowerBuilding(){
        
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);

        TowerBuilding tower = new TowerBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(1));

        d.addBuildingEntity(tower);
    
        assertEquals(d.getBuildingEntities().get(0), tower); 
        assertEquals(tower.getX(), 0);
        assertEquals(tower.getY(), 1);   
    }
    /**
     * Test tower's damage to enemies
     */
    @Test
    public void testTowerBuildingDamage(){

        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));
        orderedPath.add(new Pair<>(0, 3));
        orderedPath.add(new Pair<>(0, 4));
        orderedPath.add(new Pair<>(0, 5));
        orderedPath.add(new Pair<>(0, 6));

        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);

        TowerBuilding towerBuilding = new TowerBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(3));

        d.addBuildingEntity(towerBuilding);

        PathPosition position = new PathPosition(0, orderedPath);
        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);
        PathPosition slugPosition = new PathPosition(1, orderedPath);
        Slug slug = new Slug(slugPosition);
        
        character.dealDamage(d, slug);
        // assuming damage is 5
        assertEquals(towerBuilding.getDamage(), 5);
        // slug health should be 15 - character damage - tower damage = 15 - 10 - 5 = 0
        towerBuilding.dealDamage(slug);
        assertEquals(slug.getHealth(), 0);
    }

    /**
     * test tower building damage radius
     */
    @Test
    public void testTowerBuildingRadius(){
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));
        orderedPath.add(new Pair<>(0, 3));
        orderedPath.add(new Pair<>(0, 4));
        orderedPath.add(new Pair<>(0, 5));
        orderedPath.add(new Pair<>(0, 6));

        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);

        TowerBuilding towerBuilding = new TowerBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(6));

        d.addBuildingEntity(towerBuilding);

        PathPosition position = new PathPosition(0, orderedPath);
        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        assertEquals(towerBuilding.isCharacterInAOE(character), false);
        
        // move character into radius
        character.moveDownPath();
        character.moveDownPath();
        // character in radius now
        assertEquals(towerBuilding.isCharacterInAOE(character), true);

    }
}