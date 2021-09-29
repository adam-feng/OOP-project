package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;

import unsw.loopmania.Anduril;
import unsw.loopmania.PathPosition;
import unsw.loopmania.NormalCharacter;
import unsw.loopmania.Doggie;

/**
 * Tests for Anduril, Flame of the West
 */
public class AndurilTest {
    /**
     * Test anduril's damage stats
     */
    @Test
    public void testAndurilStats() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        Anduril anduril = d.addUnequippedAnduril();
        d.convertItemToEquipped(anduril.getX(), anduril.getY(), 0, 1);

        Doggie doggie = new Doggie(new PathPosition(1, orderedPath));

        assertEquals(character.dealDamage(d, doggie), 30);
    }

    /**
     * Test equipping anduril 
     */
    @Test
    public void testAddAnduril() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        Anduril anduril = d.addUnequippedAnduril();
        d.convertItemToEquipped(anduril.getX(), anduril.getY(), 1, 1);

        assertEquals(d.getEquippedItems().size(), 1);
    }
    
    /**
     * Test battle with Anduril. should only be effective against bosses
     * Should be triple damage of character 
     */
    @Test
    public void testBattleWithAnduril() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        Anduril anduril = d.addUnequippedAnduril();
        d.convertItemToEquipped(anduril.getX(), anduril.getY(), 0, 1);

        Doggie doggie = new Doggie(new PathPosition(1, orderedPath));
        
        assertEquals(anduril.checkBoss(doggie), true);

        character.dealDamage(d, doggie);
        // doggie health is 50 default
        assertEquals(doggie.getHealth(), 20);
    }
}

