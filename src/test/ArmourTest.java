package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;

import unsw.loopmania.Armour;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Slug;
import unsw.loopmania.NormalCharacter;

/**
 * Armour defence tests
 */
public class ArmourTest {
    /**
     * Tests armour stats
     */
    @Test
    public void testArmourStats() {
        Armour armour = new Armour(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        assertEquals(armour.getDefence(), 0.5);
    }

    /**
     * Test equip armour
     */
    @Test
    public void testEquipArmour() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        Armour armour = d.addUnequippedArmour();
        d.convertItemToEquipped(armour.getX(), armour.getY(), 1, 1);

        assertEquals(d.getEquippedItems().size(), 1);
    }

    /**
     * Test unequip armour
     */
    @Test
    public void testRemoveArmour(){
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        Armour armour = d.addUnequippedArmour();
        d.convertItemToEquipped(armour.getX(), armour.getY(), 1, 1);
        assertEquals(d.getEquippedItems().size(), 1);

        d.convertItemToUnequipped(1, 1, 2, 2);
        assertEquals(d.getEquippedItems().size(), 0);
        assertEquals(d.getUnequippedInventoryItems().size(), 1);
    }

    /**
     * Battle slug with armour
     */
    @Test
    public void testBattleSlugWithArmour() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        Armour armour = d.addUnequippedArmour();
        d.convertItemToEquipped(armour.getX(), armour.getY(), 1, 1);

        PathPosition slugPosition = new PathPosition(1, orderedPath);
        Slug slug = new Slug(slugPosition);

        //manufacturing a battle
        character.takeDamage(d, slug);

        // As character has armor equipped, should have took 2.5 damage from slug
        assertEquals(character.getHealth(), 97.5);
    }
}