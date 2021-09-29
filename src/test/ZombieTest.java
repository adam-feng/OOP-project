package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Zombie;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.NormalCharacter;

/**
 * Test zombie enemy
 */
public class ZombieTest {
    /**
     * Spawn zombie test
     */
    @Test
    public void spawnZombies() {
        // Create a world
        ArrayList<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        //LoopManiaWorld world  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        Zombie zombie = new Zombie(position);
        
        assertEquals(zombie.getX(), 0);
        assertEquals(zombie.getY(), 0);
    }

    /**
     * Move zombie
     */
    @Test
    public void testMoveZombie() {
        ArrayList<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));
        orderedPath.add(new Pair<>(0, 3));
        orderedPath.add(new Pair<>(0, 4));

        //LoopManiaWorld world  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);


        Zombie zombie = new Zombie(position);

        zombie.moveDownPath();
        zombie.moveDownPath();
        zombie.moveDownPath();
        zombie.moveDownPath();
        
        assertEquals(zombie.getX(), 0);
        assertEquals(zombie.getY(), 4);
    }

    /**
     * test zombie battle and support radius
     */
    @Test
    public void testZombieRadius() {
        ArrayList<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));
        orderedPath.add(new Pair<>(0, 3));
        orderedPath.add(new Pair<>(0, 4));
        orderedPath.add(new Pair<>(0, 5));

        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(1, orderedPath);
        
        Zombie zombie = new Zombie(position);
        
        PathPosition charPosition = new PathPosition(5, orderedPath); 
        NormalCharacter character = new NormalCharacter(charPosition);

        d.setCharacter(character);
        assert(!zombie.characterInBattleRadius(character));
        assert(!zombie.characterInSupportRadius(character));

        character.moveDownPath();               
        assert(zombie.characterInSupportRadius(character));
        character.moveDownPath();
        assert(zombie.characterInBattleRadius(character));
    }

    /**
     * test zombie damage
     */
    @Test
    public void testZombieDamage() {
        ArrayList<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);
        
        Zombie zombie = new Zombie(position);

        PathPosition charPosition = new PathPosition(1, orderedPath); 
        NormalCharacter character = new NormalCharacter(charPosition);

        d.setCharacter(character);

        assertEquals(zombie.getDamage(), 10);
        character.takeDamage(d, zombie);
        assertEquals(character.getHealth(), 90);
    }

    /**
     * Character battle zombie
     */
    @Test
    public void testBattleZombie() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        PathPosition slugPosition = new PathPosition(1, orderedPath);
        Zombie zombie = new Zombie(slugPosition);

        //manufacturing a battle
        //character attack first
        character.dealDamage(d, zombie);
        assertEquals(zombie.getHealth(), 10);
        character.dealDamage(d, zombie);
        assertEquals(zombie.getHealth(), 0);

        // Character should have 300 gold, 50 exp and 95 health after 
        // battling the slug
        List<BasicEnemy> defeatedEnemies = new ArrayList<BasicEnemy>();
        defeatedEnemies.add(zombie);
        d.handleRewards(defeatedEnemies);

        assertEquals(d.getGold(), 200);
        assertEquals(d.getXp(), 500);
    }
}