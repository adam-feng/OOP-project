package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Slug;
import unsw.loopmania.Vampire;
import unsw.loopmania.Staff;
import unsw.loopmania.NormalCharacter;

/**
 * Staff weapon tests
 */
public class StaffTest {
    private boolean tranceCase = true; 

    /**
     * Test staff stats
     */
    @Test
    public void testStaffStats() {
        Staff staff = new Staff(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        assertEquals(staff.getDamage(), 2);
    }

    /**
     * Test equip staff
     */
    @Test
    public void testAddStaff() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        Staff staff = d.addUnequippedStaff();
        d.convertItemToEquipped(staff.getX(), staff.getY(), 1, 1);

        assertEquals(d.getEquippedItems().size(), 1);
    }

    /**
     * Test remove staff
     */
    @Test
    public void testRemoveStaff(){
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        Staff staff = d.addUnequippedStaff();
        d.convertItemToEquipped(staff.getX(), staff.getY(), 1, 1);
        assertEquals(d.getEquippedItems().size(), 1);

        d.convertItemToUnequipped(1, 1, 2, 2);
        assertEquals(d.getEquippedItems().size(), 0);
        assertEquals(d.getUnequippedInventoryItems().size(), 1);
    }

    /**
     * Test battle slug with staff
     */
    @Test
    public void testBattleSlugwithStaff() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        Staff staff = d.addUnequippedStaff();
        d.convertItemToEquipped(staff.getX(), staff.getY(), 0, 1);

        PathPosition slugPosition = new PathPosition(1, orderedPath);
        Slug slug = new Slug(slugPosition);

        //manufacturing a battle
        character.dealDamage(d, slug);

        // As character has staff equipped, should have dealt 12 damage to slug
        assertEquals(slug.getHealth(), 3);
    }

    /**
     * Test trance
     */
    @Test
    public void testStaffTrance() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));

        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        Staff staff = d.addUnequippedStaff();
        d.convertItemToEquipped(staff.getX(), staff.getY(), 0, 1);

        PathPosition vampirePosition = new PathPosition(1, orderedPath);
        Vampire vampire = new Vampire(vampirePosition);

        boolean trance = false;
        for (int i = 0; i < 100; i++) {
            staff.possiblyInflictTrance(vampire);
            if (vampire.getTrance() > 0) {
                trance = true;
            }
        }

        assertEquals(trance, true);
    }
}