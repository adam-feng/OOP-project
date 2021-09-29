package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.javatuples.Pair;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.Stake;
import unsw.loopmania.Vampire;
import unsw.loopmania.Slug;
import unsw.loopmania.PathPosition;
import unsw.loopmania.NormalCharacter;

/**
 * Test stake weapon
 */
public class StakeTest {
    /**
     * Test stake stats
     */
    @Test
    public void testStakeStats() {
        Stake stake = new Stake(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        assertEquals(stake.getDamage(), 5);
    }

    /**
     * Test add and equip stake
     */
    @Test
    public void testAddStake() {

        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        Stake stake = d.addUnequippedStake();
        d.convertItemToEquipped(stake.getX(), stake.getY(), 1, 1);

        assertEquals(d.getEquippedItems().size(), 1);
    }

    /**
     * Test unequip stake
     */
    @Test
    public void testAddRemoveStake() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        Stake stake = d.addUnequippedStake();
        d.convertItemToEquipped(stake.getX(), stake.getY(), 1, 1);
        assertEquals(d.getEquippedItems().size(), 1);

        d.convertItemToUnequipped(1, 1, 2, 2);
        assertEquals(d.getEquippedItems().size(), 0);
        assertEquals(d.getUnequippedInventoryItems().size(), 1);
    }

    /**
     * Battle slug with stake
     */
    @Test
    public void testBattleSlugWithStake() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        Stake stake = d.addUnequippedStake();
        d.convertItemToEquipped(stake.getX(), stake.getY(), 0, 1);

        PathPosition slugPosition = new PathPosition(1, orderedPath);
        Slug slug = new Slug(slugPosition);

        //manufacturing a battle
        character.dealDamage(d, slug);

        // As character has stale equipped, should have dealt 15 damage to slug
        assertEquals(slug.getHealth(), 0);
    }

    /**
     * Battle vampire with stake
     */
    @Test
    public void testBattleVampireWithStake() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        Stake stake = d.addUnequippedStake();
        d.convertItemToEquipped(stake.getX(), stake.getY(), 0, 1);

        PathPosition vPosition = new PathPosition(1, orderedPath);
        Vampire vampire = new Vampire(vPosition);

        //manufacturing a battle
        character.dealDamage(d, vampire);

        // As character has stake equipped, should have dealt 30 damage to slug
        assertEquals(vampire.getHealth(), 0);
    }
}