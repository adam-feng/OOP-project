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
import unsw.loopmania.Helmet;
import unsw.loopmania.NormalCharacter;

/**
 * Test for helmet item
 */
public class HelmetTest {
    /**
     * Test helmet stats
     */
    @Test
    public void testHelmetStats() {
        Helmet helmet = new Helmet(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        assertEquals(helmet.getDefence(), 0.8);
        assertEquals(helmet.getAttackReduction(), 0.8);
    }
    
    /**
     * Test equip helmet
     */
    @Test
    public void testAddHelmet() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        Helmet helmet = d.addUnequippedHelmet();
        d.convertItemToEquipped(helmet.getX(), helmet.getY(), 1, 0);

        assertEquals(d.getEquippedItems().size(), 1);

    }

    /**
     * Test battling slug with helmet
     */
    @Test
    public void testBattleSlugWithHelmet() { 
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();

        orderedPath.add(new Pair<>(0, 0));
        orderedPath.add(new Pair<>(0, 1));
        orderedPath.add(new Pair<>(0, 2));


        LoopManiaWorld d  = new LoopManiaWorld(3, 3, orderedPath);
        PathPosition position = new PathPosition(0, orderedPath);

        NormalCharacter character = new NormalCharacter(position);
        d.setCharacter(character);

        Helmet helmet = d.addUnequippedHelmet();
        d.convertItemToEquipped(helmet.getX(), helmet.getY(), 1, 0);

        PathPosition slugPosition = new PathPosition(1, orderedPath);
        Slug slug = new Slug(slugPosition);

        //manufacturing a battle
        character.dealDamage(d, slug);

        // should deal 8/10 damage to character
        assertEquals(slug.getHealth(), 7.0);

        character.takeDamage(d, slug);

        // As character has helmet equipped, should have took 4/5 damage from slug
        assertEquals(character.getHealth(), 96.0);


    }
}