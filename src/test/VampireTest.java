package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Vampire;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.CampfireBuilding;
import unsw.loopmania.NormalCharacter;

/**
 * Test vampire enemy
 */
public class VampireTest {
    /**
     * Spawn vampire in map
     */
    @Test
    public void spawnVampires() {
        ArrayList<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        //LoopManiaWorld world  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        Vampire vampire = new Vampire(position);
        
        assertEquals(vampire.getX(), 0);
        assertEquals(vampire.getY(), 0);
    }
    /**
     * Test move vampire on path
     */
    @Test
    public void testMoveVampire(){
        // Create a world
        ArrayList<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));
        orderedPath.add(new Pair<>(0, 3));
        orderedPath.add(new Pair<>(0, 4));

        //LoopManiaWorld world  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);


        Vampire vampire = new Vampire(position);

        vampire.moveDownPath();
        vampire.moveDownPath();
        vampire.moveDownPath();
        vampire.moveDownPath();
        
        assertEquals(vampire.getX(), 0);
        assertEquals(vampire.getY(), 4);
    }

    /**
     * Test vampire battle and support radius
     */
    @Test
    public void testVampireRadius(){
        ArrayList<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));
        orderedPath.add(new Pair<>(0, 3));
        orderedPath.add(new Pair<>(0, 4));
        orderedPath.add(new Pair<>(0, 5));

        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(5, orderedPath);
        
        Vampire vampire = new Vampire(position);
        
        PathPosition charPosition = new PathPosition(0, orderedPath); 
        NormalCharacter character = new NormalCharacter(charPosition);

        d.setCharacter(character);
        assert(!vampire.characterInBattleRadius(character));
        assert(!vampire.characterInSupportRadius(character));

        character.moveDownPath();               
        assert(vampire.characterInSupportRadius(character));
        character.moveDownPath();
        character.moveDownPath();
        assert(vampire.characterInBattleRadius(character));
    }
    /**
     * Test vampire's damage
     */
    @Test
    public void testVampireDamage() {
        ArrayList<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);
        
        Vampire vampire = new Vampire(position);

        PathPosition charPosition = new PathPosition(1, orderedPath); 
        NormalCharacter character = new NormalCharacter(charPosition);

        d.setCharacter(character);

        assertEquals(vampire.getDamage(), 30);
        character.takeDamage(d, vampire);

        boolean noncritical = character.getHealth() == 70;
        boolean critical = character.getHealth() == 40;
        assert(critical || noncritical);
    }

    /**
     * Character battle vampire
     */
    @Test
    public void testBattleVampire() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        PathPosition slugPosition = new PathPosition(1, orderedPath);
        Vampire vampire = new Vampire(slugPosition);

        //manufacturing a battle
        //character attack first
        character.dealDamage(d, vampire);
        assertEquals(vampire.getHealth(), 20);
        character.dealDamage(d, vampire);
        assertEquals(vampire.getHealth(), 10);
        character.dealDamage(d, vampire);
        assertEquals(vampire.getHealth(), 0);

        // Character should have 300 gold, 50 exp and 95 health after 
        // battling the slug
        List<BasicEnemy> defeatedEnemies = new ArrayList<BasicEnemy>();
        defeatedEnemies.add(vampire);
        d.handleRewards(defeatedEnemies);

        assertEquals(d.getGold(), 500);
        assertEquals(d.getXp(), 2000);
    }

    /**
     * Test campfire behaviour
     */
    @Test
    public void testVampireCampfire() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));
        orderedPath.add(new Pair<>(0, 3));


        LoopManiaWorld d  = new LoopManiaWorld(4, 4, orderedPath);

        PathPosition slugPosition = new PathPosition(2, orderedPath);
        Vampire vampire = new Vampire(slugPosition);

        CampfireBuilding campfire = new CampfireBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(3));

        vampire.moveAwayFromCampfire(campfire);

        assertEquals(vampire.getX(), 0);
        assertEquals(vampire.getY(), 3);
    }
}
