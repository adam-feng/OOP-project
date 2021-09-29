package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import unsw.loopmania.LoopManiaWorld;

import unsw.loopmania.PathPosition;
import unsw.loopmania.Slug;
import unsw.loopmania.SwiftFootDecorator;
import unsw.loopmania.Sword;
import unsw.loopmania.Vampire;
import unsw.loopmania.ShortSword;

/**
 * Test character
 */
public class SwiftFootClassTest {
    /**
     * Test spawn and set character
     */
    @Test
    public void SwiftFootStatsTest() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        SwiftFootDecorator character = new SwiftFootDecorator(position);
        d.setCharacter(character);

        assertEquals(character.getHealth(), 80);
    }

    /**
     * Test spawn swift foot
     */
    @Test
    public void testSpawnSwiftFoot() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        SwiftFootDecorator character = new SwiftFootDecorator(position);
        d.setCharacter(character);

        assertEquals(character.getX(), 0);
        assertEquals(character.getY(), 0);
    }

    /**
     * Deal damage as swift foot
     */
    @Test
    public void dealDamageTest() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        SwiftFootDecorator character = new SwiftFootDecorator(position);
        d.setCharacter(character);

        PathPosition slugPosition = new PathPosition(1, orderedPath);
        Slug slug = new Slug(slugPosition);

        //manufacturing a battle
        //character attack first
        character.dealDamage(d, slug);
        assertEquals(slug.getHealth(), 5.0);
    }

    /**
     * Swift foot evades attack
     */
    @Test
    public void testEvadeAttack() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        SwiftFootDecorator character = new SwiftFootDecorator(position);
        d.setCharacter(character);

        PathPosition slugPosition = new PathPosition(1, orderedPath);
        Slug slug = new Slug(slugPosition);

        boolean evadeAttack = false;
        for (int i = 0; i < 1000; i++) {
            character.dealDamage(d, slug);
            if (character.getHealth() == 80) {
                evadeAttack = true;
            }
        }
        assertEquals(evadeAttack, true);
    }

    /**
     * Swift foot attacks twice
     */
    @Test
    public void testDoubleAttack() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        SwiftFootDecorator character = new SwiftFootDecorator(position);
        d.setCharacter(character);

        PathPosition vampirePosition = new PathPosition(1, orderedPath);
        Vampire vampire = new Vampire(vampirePosition);

        ShortSword shortSword = d.addUnequippedShortSword();
        d.convertItemToEquipped(shortSword.getX(), shortSword.getY(), 1, 1);

        boolean doubleAttack = false;
        for (int i = 0; i < 1000; i++) {
            character.dealDamage(d, vampire);
            if (character.getHealth() == 80) {
                doubleAttack = true;
            }
        }
        assertEquals(doubleAttack, true);
    }
}
