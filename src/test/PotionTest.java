package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import org.junit.jupiter.api.Test;

import unsw.loopmania.LoopManiaWorld;

import unsw.loopmania.PathPosition;
import unsw.loopmania.NormalCharacter;
import unsw.loopmania.HealingPotion;
import unsw.loopmania.Slug;

/**
 * Test potion healing item
 */
public class PotionTest {
    /**
     * Test use potion to heal
     */
    @Test
    public void testUsePotion() {

        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        HealingPotion potion = d.addUnequippedHealingPotion();
        d.convertItemToEquipped(potion.getX(), potion.getY(), 0, 0);

        assertEquals(d.getEquippedItems().size(), 1);

        potion.heal(character);
        assertEquals(character.getHealth(), 100);

        character.setHealth(20);
        potion.heal(character);
        assertEquals(character.getHealth(), 100);
    }

    @Test
    public void testUsePotionAfterBattle() {

        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        Slug slug = new Slug(position);

        PathPosition charPosition = new PathPosition(1, orderedPath);
        NormalCharacter character = new NormalCharacter(charPosition);

        d.setCharacter(character);

        character.takeDamage(d, slug);

        HealingPotion potion = d.addUnequippedHealingPotion();
        d.convertItemToEquipped(potion.getX(), potion.getY(), 0, 0);

        assertEquals(d.getEquippedItems().size(), 1);

        potion.heal(character);
        assertEquals(character.getHealth(), 100);

        character.setHealth(20);
        potion.heal(character);
        assertEquals(character.getHealth(), 100);
    }
}