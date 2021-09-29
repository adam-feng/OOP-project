package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import org.junit.jupiter.api.Test;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Slug;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.NormalCharacter;
import unsw.loopmania.ElanMuske;

/**
 * Test for Elan Muske test
 */
public class ElanMuskeTest {
    /**
     * Test ElanMuske spawning on map
     */
    @Test
    public void testSpawnElan() {
        // Create a world
        ArrayList<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        //LoopManiaWorld world  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        ElanMuske elanMuske= new ElanMuske(position);
        
        assertEquals(elanMuske.getX(), 0);
        assertEquals(elanMuske.getY(), 0);
    }

    /**
     * Test moving Elan
     */
    @Test
    public void testMoveElan() {
        // Create a world
        ArrayList<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));
        orderedPath.add(new Pair<>(0, 3));
        orderedPath.add(new Pair<>(0, 4));

        //LoopManiaWorld world  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);


        ElanMuske elanMuske = new ElanMuske(position);

        elanMuske.moveDownPath();
        elanMuske.moveDownPath();
        elanMuske.moveDownPath();
        elanMuske.moveDownPath();
        
        assertEquals(elanMuske.getX(), 0);
        assertEquals(elanMuske.getY(), 4);
    }

    /**
     * Test ElanMuske's battle radius
     */
    @Test
    public void testElanMuskeBattleRadius() {
        ArrayList<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);
        
        ElanMuske elanMuske = new ElanMuske(position);
        
        PathPosition charPosition = new PathPosition(2, orderedPath); 
        NormalCharacter character = new NormalCharacter(charPosition);

        d.setCharacter(character);

        character.moveDownPath();               
        assert(elanMuske.characterInBattleRadius(character));
    }

    /**
     * Test ElanMuske's damage
     */
    @Test
    public void testElanMuskeDamage() {
        ArrayList<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);
        
        ElanMuske elanMuske = new ElanMuske(position);

        PathPosition charPosition = new PathPosition(1, orderedPath); 
        NormalCharacter character = new NormalCharacter(charPosition);

        d.setCharacter(character);

        assertEquals(elanMuske.getDamage(), 40);
        character.takeDamage(d, elanMuske);
        assertEquals(character.getHealth(), 60);
    }

    /**
     * Character battles elanMuske
     */
    @Test
    public void testBattleElanMuske() {

        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        PathPosition elanMuskePosition = new PathPosition(1, orderedPath);
        ElanMuske elanMuske = new ElanMuske(elanMuskePosition);

        //manufacturing a battle
        //character attack first
        character.dealDamage(d, elanMuske);
        assertEquals(elanMuske.getHealth(), 90);
        character.dealDamage(d, elanMuske);
        assertEquals(elanMuske.getHealth(), 80);
        character.dealDamage(d, elanMuske);
        assertEquals(elanMuske.getHealth(), 70);
        character.dealDamage(d, elanMuske);
        assertEquals(elanMuske.getHealth(), 60);
        character.dealDamage(d, elanMuske);
        assertEquals(elanMuske.getHealth(), 50);
        character.dealDamage(d, elanMuske);
        assertEquals(elanMuske.getHealth(), 40);
        character.dealDamage(d, elanMuske);
        assertEquals(elanMuske.getHealth(), 30);
        character.dealDamage(d, elanMuske);
        assertEquals(elanMuske.getHealth(), 20);
        character.dealDamage(d, elanMuske);
        assertEquals(elanMuske.getHealth(), 10);
        

        List<BasicEnemy> defeatedEnemies = new ArrayList<BasicEnemy>();
        defeatedEnemies.add(elanMuske);
        d.handleRewards(defeatedEnemies);

        assertEquals(d.getGold(), 3000);
        assertEquals(d.getXp(), 10000);
    }

    /**
     * Tests Elan Muske heals other enemies in his support radius
     */
    @Test
    public void testHealing() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        PathPosition elanMuskePosition = new PathPosition(1, orderedPath);
        ElanMuske elanMuske = new ElanMuske(elanMuskePosition);
        Slug slug = new Slug(new PathPosition(2, orderedPath));
        d.addEnemy(slug);
        d.addEnemy(elanMuske);

        character.dealDamage(d, slug);
        assertEquals(slug.getHealth(), 5);

        elanMuske.healSurroundings(d);
        assertEquals(slug.getHealth(), 10);
    }

    /**
     * Test elanMuske spawns after a certain number of cycles & XP level
     */
    @Test
    public void testCycleElanMuske() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        PathPosition elanMuskePosition = new PathPosition(1, orderedPath);
        ElanMuske elanMuske = new ElanMuske(elanMuskePosition);

        assertEquals(elanMuske.canSpawn(d), false);
        d.addXp(20000);
        for (int i = 0; i < 40; i++) {
            d.updateCyclesCompleted();
        }
        assertEquals(elanMuske.canSpawn(d), true);
    }
}
