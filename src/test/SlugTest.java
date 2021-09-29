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
import unsw.loopmania.EnemyFactory;
import unsw.loopmania.NormalCharacter;

/**
 * Test slug enemy
 */
public class SlugTest {
    
    /**
     * Test spawn slug
     */
    @Test
    public void testSpawnSlug() {
        // Create a world
        ArrayList<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        //LoopManiaWorld world  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        Slug slug = new Slug(position);
        
        assertEquals(slug.getX(), 0);
        assertEquals(slug.getY(), 0);
    }

    /**
     * test move slug
     */
    @Test
    public void testMoveSlug() {
        // Create a world
        ArrayList<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));
        orderedPath.add(new Pair<>(0, 3));
        orderedPath.add(new Pair<>(0, 4));

        //LoopManiaWorld world  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);


        Slug slug = new Slug(position);

        slug.moveDownPath();
        slug.moveDownPath();
        slug.moveDownPath();
        slug.moveDownPath();
        
        assertEquals(slug.getX(), 0);
        assertEquals(slug.getY(), 4);
    }


    /**
     * Battle slug in radius test
     */
    @Test
    public void testSlugBattleRadius() {
        ArrayList<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);
        
        Slug slug = new Slug(position);
        
        PathPosition charPosition = new PathPosition(2, orderedPath); 
        NormalCharacter character = new NormalCharacter(charPosition);

        d.setCharacter(character);

        character.moveDownPath();               
        assert(slug.characterInBattleRadius(character));

    }

    /**
     * Test slug's damage
     */
    @Test
    public void testSlugDamage() {
        ArrayList<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);
        
        Slug slug = new Slug(position);

        PathPosition charPosition = new PathPosition(1, orderedPath); 
        NormalCharacter character = new NormalCharacter(charPosition);

        d.setCharacter(character);

        assertEquals(slug.getDamage(), 5);
        character.takeDamage(d, slug);
        assertEquals(character.getHealth(), 95);
    }


    /**
     * Character battle slug
     */
    @Test
    public void testBattleSlug() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        PathPosition slugPosition = new PathPosition(1, orderedPath);
        Slug slug = new Slug(slugPosition);

        //manufacturing a battle
        //character attack first
        character.dealDamage(d, slug);
        assertEquals(slug.getHealth(), 5.0);
        character.dealDamage(d, slug);
        assertEquals(slug.getHealth(), -5.0);

        // Character should have 300 gold, 50 exp and 95 health after 
        // battling the slug
        List<BasicEnemy> defeatedEnemies = new ArrayList<BasicEnemy>();
        defeatedEnemies.add(slug);
        d.handleRewards(defeatedEnemies);

        assertEquals(d.getGold(), 100);
        assertEquals(d.getXp(), 100);
    }

    @Test
    public void testRandomSpawnSlug() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        EnemyFactory factory = new EnemyFactory(d);
        boolean spawned = false;
        for (int i = 0; i < 50000; i++) {
            factory.spawnSlugs();
            if (d.getEnemies().size() > 0) {
                spawned = true;
            }
        }

        assertEquals(spawned, true);

    }

    @Test
    public void testSlugLoot() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        PathPosition slugPosition = new PathPosition(1, orderedPath);
        Slug slug = new Slug(slugPosition);

        boolean dropped = false;
        for (int i = 0; i < 100; i++) {
            if (slug.dropItems() != "") {
                dropped = true;
            }
        }

        assertEquals(dropped, true);
    }
}