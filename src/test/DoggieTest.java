package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import org.junit.jupiter.api.Test;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.NormalCharacter;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Doggie;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.Character;
import unsw.loopmania.DoggieCoin;

/**
 * Test for Doggie boss
 */
public class DoggieTest {
    /**
     * Test Doggie spawning on map
     */
    @Test
    public void testSpawnDoggie() {
        // Create a world
        ArrayList<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        //LoopManiaWorld world  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        Doggie doggie = new Doggie(position);
        
        assertEquals(doggie.getX(), 0);
        assertEquals(doggie.getY(), 0);
    }

    /**
     * Test moving Doggie
     */
    @Test
    public void testMoveDoggie() {
        // Create a world
        ArrayList<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));
        orderedPath.add(new Pair<>(0, 3));
        orderedPath.add(new Pair<>(0, 4));

        //LoopManiaWorld world  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);


        Doggie doggie = new Doggie(position);

        doggie.moveDownPath();
        doggie.moveDownPath();
        doggie.moveDownPath();
        doggie.moveDownPath();
        
        assertEquals(doggie.getX(), 0);
        assertEquals(doggie.getY(), 4);
    }

    /**
     * Test Doggie's battle radius
     */
    @Test
    public void testDoggieBattleRadius() {
        ArrayList<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);
        
        Doggie doggie = new Doggie(position);
        
        PathPosition charPosition = new PathPosition(2, orderedPath); 
        NormalCharacter character = new NormalCharacter(charPosition);

        d.setCharacter(character);

        character.moveDownPath();               
        assert(doggie.characterInBattleRadius(character));
    }

    /**
     * Test Doggie's damage
     */
    @Test
    public void testDoggieDamage() {
        ArrayList<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);
        
        Doggie doggie = new Doggie(position);

        PathPosition charPosition = new PathPosition(1, orderedPath); 
        NormalCharacter character = new NormalCharacter(charPosition);

        d.setCharacter(character);

        assertEquals(doggie.getDamage(), 20);
        character.takeDamage(d, doggie);
        assertEquals(character.getHealth(), 80);
    }

    /**
     * Character battles doggie
     */
    @Test
    public void testBattleDoggie() {

        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        PathPosition doggiePosition = new PathPosition(1, orderedPath);
        Doggie doggie = new Doggie(doggiePosition);

        //manufacturing a battle
        //character attack first
        character.dealDamage(d, doggie);
        assertEquals(doggie.getHealth(), 40);
        character.dealDamage(d, doggie);
        assertEquals(doggie.getHealth(), 30);
        character.dealDamage(d, doggie);
        assertEquals(doggie.getHealth(), 20);
        character.dealDamage(d, doggie);
        assertEquals(doggie.getHealth(), 10);
        character.dealDamage(d, doggie);
        assertEquals(doggie.getHealth(), 0);

        List<BasicEnemy> defeatedEnemies = new ArrayList<BasicEnemy>();
        defeatedEnemies.add(doggie);
        d.handleRewards(defeatedEnemies);

        assertEquals(d.getGold(), 3000);
        assertEquals(d.getXp(), 10000);
    }

    /**
     * Doggie drops DoggieCoin
     */
    @Test
    public void testDropDoggieCoin() {

        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        PathPosition doggiePosition = new PathPosition(1, orderedPath);
        Doggie doggie = new Doggie(doggiePosition);

        //manufacturing a battle
        //character attack first
        character.dealDamage(d, doggie);
        assertEquals(doggie.getHealth(), 40);
        character.dealDamage(d, doggie);
        assertEquals(doggie.getHealth(), 30);
        character.dealDamage(d, doggie);
        assertEquals(doggie.getHealth(), 20);
        character.dealDamage(d, doggie);
        assertEquals(doggie.getHealth(), 10);
        character.dealDamage(d, doggie);
        assertEquals(doggie.getHealth(), 0);

        List<BasicEnemy> defeatedEnemies = new ArrayList<BasicEnemy>();
        defeatedEnemies.add(doggie);
        d.handleRewards(defeatedEnemies);

        DoggieCoin doggieCoin = d.addUnequippedDoggieCoin();

        assertEquals(d.getUnequippedInventoryItems().size(), 1);
        assertEquals(d.getGold(), 3000);
        assertEquals(d.getXp(), 10000);
    }

    /**
     * Test doggie spawns after a certain number of cycles
     */
    @Test
    public void testCycleDoggie() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        PathPosition doggiePosition = new PathPosition(1, orderedPath);
        Doggie doggie = new Doggie(doggiePosition);

        assertEquals(doggie.canSpawn(d), false);
        for (int i = 0; i < 20; i++) {
            d.updateCyclesCompleted();
        }
        assertEquals(doggie.canSpawn(d), true);
    }

    /**
     * Tests Doggie's stun attack
     */
    @Test
    public void testStun() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        PathPosition doggiePosition = new PathPosition(1, orderedPath);
        Doggie doggie = new Doggie(doggiePosition);

        boolean stunned = false;
        for (int i = 0; i < 100; i++) {
            doggie.possiblyApplyStun(character);
            if (character.getStunned()) {
                stunned = true;
            }
        }
        assertEquals(stunned, true);

    }
}
