package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import unsw.loopmania.LoopManiaWorld;

import unsw.loopmania.PathPosition;
import unsw.loopmania.Slug;
import unsw.loopmania.Sword;
import unsw.loopmania.Lance;
import unsw.loopmania.LancerDecorator;

/**
 * Test character
 */
public class LancerTest {
    /**
     * Test spawn and set character
     */
    @Test
    public void testSpawnLancer() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        LancerDecorator character = new LancerDecorator(position);
        d.setCharacter(character);

        assertEquals(character.getX(), 0);
        assertEquals(character.getY(), 0);
    }

    /**
     * Test character's base damage with lance (1.5x)
     */
    @Test
    public void dealDamageLanceTest() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        LancerDecorator character = new LancerDecorator(position);
        d.setCharacter(character);

        PathPosition slugPosition = new PathPosition(1, orderedPath);
        Slug slug = new Slug(slugPosition);

        Lance lance = d.addUnequippedLance();
        d.convertItemToEquipped(lance.getX(), lance.getY(), 0, 1);
        
        //manufacturing a battle
        //character attack first
        character.dealDamage(d, slug);
        assertEquals(slug.getHealth(), 0.0);
    }

    /**
     * Test character's base damage with out lance
     */
    @Test
    public void dealDamageTest() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        LancerDecorator character = new LancerDecorator(position);
        d.setCharacter(character);

        PathPosition slugPosition = new PathPosition(1, orderedPath);
        Slug slug = new Slug(slugPosition);

        Sword sword = d.addUnequippedSword();
        d.convertItemToEquipped(sword.getX(), sword.getY(), 0, 1);
        
        //manufacturing a battle
        //character attack first
        character.dealDamage(d, slug);
        assertEquals(slug.getHealth(), 7.5);
    }

    /**
     * Test Charge attack
     */
    @Test
    public void testCharge() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        LancerDecorator character = new LancerDecorator(position);
        d.setCharacter(character);

        PathPosition slugPosition = new PathPosition(1, orderedPath);
        Slug slug = new Slug(slugPosition);

        Sword sword = d.addUnequippedSword();
        d.convertItemToEquipped(sword.getX(), sword.getY(), 0, 1);

        boolean charge = false;
        for (int i = 0; i < 1000; i++) {
            double damage = character.dealDamage(d, slug);
            if (damage == 150) {
                charge = true;
            }
        }
        assertEquals(charge, true);
    }
}
