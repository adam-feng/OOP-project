package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;

import unsw.loopmania.Sword;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Slug;
import unsw.loopmania.NormalCharacter;

/**
 * Sword tests
 */
public class SwordTest {
    /**
     * Test sword stats
     */
    @Test
    public void testSwordStats() {
        Sword sword = new Sword(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        assertEquals(sword.getDamage(), 10);
    }

    /**
     * Test add sword 
     */
    @Test
    public void testAddSword() {

        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        Sword sword = d.addUnequippedSword();
        d.convertItemToEquipped(sword.getX(), sword.getY(), 1, 1);

        assertEquals(d.getEquippedItems().size(), 1);

    }

    /**
     * Test equip and unequip sword
     */
    @Test
    public void testAddRemoveSword() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        Sword sword = d.addUnequippedSword();
        d.convertItemToEquipped(sword.getX(), sword.getY(), 1, 1);
        assertEquals(d.getEquippedItems().size(), 1);

        d.convertItemToUnequipped(1, 1, 2, 2);
        assertEquals(d.getEquippedItems().size(), 0);
        assertEquals(d.getUnequippedInventoryItems().size(), 1);

    }

    /**
     * Test battle slug with sword
     */
    @Test
    public void testBattleSlugWithSword() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        Sword sword = d.addUnequippedSword();
        d.convertItemToEquipped(sword.getX(), sword.getY(), 0, 1);

        PathPosition slugPosition = new PathPosition(1, orderedPath);
        Slug slug = new Slug(slugPosition);

        //manufacturing a battle
        character.dealDamage(d, slug);

        // As character has sword equipped, should have dealt 20 damage to slug
        assertEquals(slug.getHealth(), -5);
    }


}