package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;

import unsw.loopmania.PathPosition;
import unsw.loopmania.Shield;
import unsw.loopmania.Slug;
import unsw.loopmania.NormalCharacter;

/**
 * Test shield defence item
 */
public class ShieldTest {
    /**
     * Test shield stats
     */
    @Test
    public void testShieldStats() {
        Shield shield = new Shield(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        assertEquals(shield.getDefence(), 0.8);
    }
    
    /**
     * Test add and equip shield
     */
    @Test
    public void testAddShield() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        Shield shield = d.addUnequippedShield();
        d.convertItemToEquipped(shield.getX(), shield.getY(), 1, 1);

        assertEquals(d.getEquippedItems().size(), 1);
    }

    /**
     * Test equip and unequip shield
     */
    @Test
    public void testAddRemoveShield() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        Shield shield = d.addUnequippedShield();
        d.convertItemToEquipped(shield.getX(), shield.getY(), 2, 1);

        assertEquals(d.getEquippedItems().size(), 1);
        d.convertItemToUnequipped(2, 1, 2, 2);
        assertEquals(d.getEquippedItems().size(), 0);
        assertEquals(d.getUnequippedInventoryItems().size(), 1);

    }

    /**
     * Test battle slug with shield
     */
    @Test
    public void testBattleSlugwithShield() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        Shield shield = d.addUnequippedShield();
        d.convertItemToEquipped(shield.getX(), shield.getY(), 2, 1);

        PathPosition slugPosition = new PathPosition(1, orderedPath);
        Slug slug = new Slug(slugPosition);

        //manufacturing a battle
        character.takeDamage(d, slug);

        // As character has armor equipped, should have took 2.5 damage from slug
        assertEquals(character.getHealth(), 96.0);
    }
}