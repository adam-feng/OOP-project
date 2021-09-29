package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;

import unsw.loopmania.Lance;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Slug;
import unsw.loopmania.NormalCharacter;

/**
 * Test for Lance weapon
 */
public class LanceTest {
    
    /**
     * Test Lance weapon stats 
     */
    @Test
    public void testLanceTest() {
        Lance lance = new Lance(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        assertEquals(lance.getDamage(), 10);
    }

    /**
     * Equip Lance
     */
    @Test
    public void testAddLance() {

        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        Lance lance = d.addUnequippedLance();
        d.convertItemToEquipped(lance.getX(), lance.getY(), 1, 1);

        assertEquals(d.getEquippedItems().size(), 1);

    }

    /**
     * Test equip and unequip lance
     */
    @Test
    public void testAddRemoveLance() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        Lance lance = d.addUnequippedLance();
        d.convertItemToEquipped(lance.getX(), lance.getY(), 0, 1);
        assertEquals(d.getEquippedItems().size(), 1);

        d.convertItemToUnequipped(0, 1, 2, 2);
        assertEquals(d.getEquippedItems().size(), 0);
        assertEquals(d.getUnequippedInventoryItems().size(), 1);

    }

    /**
     * Battles a slug with lance
     */
    @Test
    public void testBattleSlugWithLance() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        Lance lance = d.addUnequippedLance();
        d.convertItemToEquipped(lance.getX(), lance.getY(), 0, 1);

        PathPosition slugPosition = new PathPosition(1, orderedPath);
        Slug slug = new Slug(slugPosition);

        //manufacturing a battle
        character.dealDamage(d, slug);

        // As character has lance equipped, should have dealt 20 damage to slug
        assertEquals(slug.getHealth(), -5);
    }
}
